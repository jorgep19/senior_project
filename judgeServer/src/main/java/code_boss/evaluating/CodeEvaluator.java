package code_boss.evaluating;

import code_boss.model.CodeEvaluation;
import code_boss.model.CompilationResult;
import code_boss.model.EvaluationRun;
import code_boss.model.UserSolution;

import java.io.*;

public abstract class CodeEvaluator {

    private ICodeEvaluationListener listener;

    public CodeEvaluator(ICodeEvaluationListener listener) {
        this.listener = listener;
    }

    public void runCode(final CompilationResult compilationResult) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder(getExecutionCommand(), getExecutionArgs());
                pb.directory(getExecutionDirectory(compilationResult.getCompiledFilePath()));
                pb.redirectErrorStream(true);

                EvaluationRun run = runWithinTimeout(pb, compilationResult.getSolution());

                listener.notifyEvaluation(
                        new CodeEvaluation(compilationResult.getSolution(), run),
                        compilationResult);
            }
        }).start();
    }

    protected abstract String getExecutionCommand();

    protected abstract String getExecutionArgs();

    protected abstract File getExecutionDirectory(String compiledFilePath);

    private EvaluationRun runWithinTimeout(ProcessBuilder pb, UserSolution solution) {
        try {
            EvaluationRun run = new EvaluationRun();
            System.out.println(String.format("Starting execution of %s", solution));
            long end = System.currentTimeMillis() + solution.getTimeout();
            Process solutionProcess = pb.start();

            // give input to the solution
            OutputStream os = solutionProcess.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            printStream.print(solution.getTestInput());
            printStream.close();

            // run solution while tracking timeout
            while (!run.finished && !run.timedOut) {
                try {
                    if (System.currentTimeMillis() >= end) {
                        run.timedOut = true;
                    }

                    run.exitCode = solutionProcess.exitValue();
                    run.finished = true;
                } catch (IllegalThreadStateException e) {
                    run.finished = false;
                }
            }

            System.out.println(String.format("Finished execution of %s with exitCode %d", solution, run.exitCode));
            if(run.timedOut) {
                solutionProcess.destroy();
                run.output = "timed out";
            } else {
                run.output = getProgramOutput(solutionProcess.getInputStream());
            }

            return run;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getProgramOutput(InputStream programOutput) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(programOutput, "UTF-8"));

            String line;
            do {
                line = bufferedReader.readLine();
                sb.append(line != null ? line : "");
                sb.append("\n");
            } while (line != null);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
