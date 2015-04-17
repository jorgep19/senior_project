package code_boss;

import code_boss.compiling.CodeCompiler;
import code_boss.compiling.CppCompiler;
import code_boss.compiling.ICompilationResultListener;
import code_boss.compiling.JavaCompiler;
import code_boss.evaluating.CodeEvaluator;
import code_boss.evaluating.CppEvaluator;
import code_boss.evaluating.ICodeEvaluationListener;
import code_boss.evaluating.JavaEvaluator;
import code_boss.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class EvaluationDirector implements ICompilationResultListener, ICodeEvaluationListener {
    private static final String[] LANGUALES = new String[] { "java", "cpp"};
    private static final String ADDITIONALS_KEY_FORMAT = "%s_%s";

    private CodeCompiler[] compilers;
    private EvaluationSender evaluationSender;
    private CodeEvaluator[] evaluators;
    // this two are used so that if a user submits +two answers for the same problem then
    // the later arriving anwser will be only evaluated after the first one has been evaluated
    private List<UserSolution> beingEvaluated;
    private ConcurrentHashMap<String, UserSolution> additionalSolutions;
    private AbstractQueue<QueuedUserSolution> evalQueue;

    public EvaluationDirector() {
        compilers = new CodeCompiler[] { new JavaCompiler(this), new CppCompiler(this)};
        evaluators = new CodeEvaluator[] { new JavaEvaluator(this), new CppEvaluator(this) };

        evaluationSender = new EvaluationSender();
        beingEvaluated = Collections.synchronizedList(new ArrayList<UserSolution>());
        additionalSolutions = new ConcurrentHashMap<String, UserSolution>();
        evalQueue = new PriorityBlockingQueue<QueuedUserSolution>(500, new Comparator<QueuedUserSolution>() {
            @Override
            public int compare(QueuedUserSolution sol1, QueuedUserSolution sol2) {
                int comparisonVal;
                int val1 = sol1.getTimeWaited() + (sol1.getSolution().getDifficulty() * 3);
                int val2 = sol2.getTimeWaited() + (sol2.getSolution().getDifficulty() * 3);

                if (val1 == val2) {
                    comparisonVal = 0;
                } else if (val1 < val2) {
                    comparisonVal = 1;
                } else {
                    comparisonVal = -1;
                }

                return comparisonVal;
            }
        });

        startQueueCheckThread();
    }

    public EvaluationRequestResponse evaluateSolution(UserSolution solution) {
        EvaluationRequestResponse response;
        int languageIndex = getLanguageIndex(solution);

        // bad request in valid language for the solution
        if (languageIndex == -1) {
            response = new EvaluationRequestResponse(400, "The solution submitted is in a language that is not supported");
        } else {
            // successfully added solution to evaluation queue
            if(addSolutionToQueue(solution)) {
                response = new EvaluationRequestResponse(200, "The solution was successfully queued for evaluation");
            //
            } else {
                response = new EvaluationRequestResponse(500, "Unable to queue the solution for evaluation");
            }
        }

        return response;
    }

    public EvaluationCheckResponse evaluationCheck(UserSolution solution) {
        EvaluationCheckResponse response;

        // check if currently being evaluated
        if(beingEvaluated.contains(solution)) {
            response = new EvaluationCheckResponse(EvaluationCheckResults.EVALUATING, solution);
        // check if queued for evaluation
        } else if (evalQueue.contains(solution)) {
            response = new EvaluationCheckResponse(EvaluationCheckResults.IN_QUEUE, solution);
            // no match found
        } else {
            response = new EvaluationCheckResponse(EvaluationCheckResults.NOT_FOUND, solution);
        }
        // NOTE: additional answers are ignore because that implies that a matching solution is currently being evaluated

        return response;
    }

    @Override
    public void notifyResult(CompilationResult result) {
        System.out.println(
            String.format("Compilation of %s succeeded: %s",
                result.getCompiledFilePath(),
                String.valueOf(result.isSuccess())));

        if(result.isSuccess()) {
            evaluators[result.getLanguageIndex()].runCode(result);
        } else {
            // server error while compiling
            if (result.isServerError()) {
                sendEvaluation(
                        new CodeEvaluation(result.getSolution(), (EvaluationRun) null),
                        result);
            // start actual evaluation
            } else {
                sendEvaluation(
                        new CodeEvaluation(result.getSolution(), result.getMessage()),
                        result);
            }
        }
    }

    @Override
    public void notifyEvaluation(CodeEvaluation evaluation, CompilationResult compilation) {
        sendEvaluation(evaluation, compilation);
    }

    private void sendEvaluation(CodeEvaluation evaluation, CompilationResult compilation) {
        // delete files created
        if (compilation.isFileCreated()) {
            compilers[compilation.getLanguageIndex()].cleanUp(compilation.getCompiledFilePath());
        }

        beingEvaluated.remove(compilation.getSolution());

        // if needed add additional solution to the queue
        UserSolution currentSolution = compilation.getSolution();
        UserSolution additionalSolution = additionalSolutions.get(
                String.format(ADDITIONALS_KEY_FORMAT, currentSolution.getUserId(), currentSolution.getProblemId()));
        if (additionalSolution != null) {
            additionalSolutions.remove(String.format(ADDITIONALS_KEY_FORMAT, currentSolution.getUserId(), currentSolution.getProblemId()));
            addSolutionToQueue(additionalSolution);
        }


        // send the result
        evaluationSender.send(evaluation, compilation.getSolution().getResponseURL());
    }

    private synchronized boolean addSolutionToQueue(UserSolution solution) {
        boolean result = true;

        // increased time waited by solution on the queue
        QueuedUserSolution[] queuedSolutions = evalQueue.toArray(new QueuedUserSolution[evalQueue.size()]);
        for (QueuedUserSolution s : queuedSolutions) {
            s.incWaitedTime();
        }

        // UserSolution equal was overridden
        // if the user already submitted a solution for this problem
        // put the new solution in a external collection so that the new solution will only be added tp the evaluation queue
        // once the current solution is done being evaluated
        if(beingEvaluated.contains(solution)) {
            additionalSolutions.put(String.format(ADDITIONALS_KEY_FORMAT, solution.getUserId(), solution.getProblemId()), solution);
        // otherwise add the solution to the evaluation queue;
        } else {
            result = evalQueue.offer(new QueuedUserSolution(solution));
        }

        return result;
    }

    private void startQueueCheckThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // this thread retrieves solution as they come in therefore it should stay alive for the life of the application
                while(true) {
                    if(!evalQueue.isEmpty()) {
                        QueuedUserSolution next = evalQueue.poll();
                        final UserSolution solution = next.getSolution();

                        int languageIndex = -1;

                        for (int i = 0; i < LANGUALES.length; ++i) {
                            if (LANGUALES[i].equals(solution.getLanguage())) {
                                languageIndex = i;
                            }
                        }

                        // not language then something when wrong
                        if (languageIndex == -1) {
                            evaluationSender.send(new CodeEvaluation(solution, (EvaluationRun) null), solution.getResponseURL());
                            // start the evaluation process
                        } else {
                            final int i = languageIndex;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    beingEvaluated.add(solution);
                                    compilers[i].CompileSolution(solution);
                                }
                            }).start();
                        }
                    }
                }
            }
        }).start();
    }

    private int getLanguageIndex(UserSolution solution) {
        int languageIndex = -1;

        for (int i = 0; i < LANGUALES.length; ++i) {
            if (LANGUALES[i].equals(solution.getLanguage())) {
                languageIndex = i;
            }
        }

        return languageIndex;
    }
}
