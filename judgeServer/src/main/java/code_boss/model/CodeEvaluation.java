package code_boss.model;

public class CodeEvaluation {
    private static final String SERVER_ERROR_ERROR_MSG = "There was a internal error please try submitting the solution again";
    private static final String TIMEOUT_MSG = "The implementation of the solution didn't finished within the time required";
    private static final String SUCCESS_MSG = "The solution was accepted!";
    private static final String WRONG_ANSWER_MESSAGE = "The solution produce a wrong answer.";

    private String userId;
    private String problemId;
    private String message;
    private int evaluationCode;
    private boolean isSuccessful;

    // constructor only for compulation issues
    public CodeEvaluation(UserSolution solution, String message) {
        this.userId = solution.getUserId();
        this.problemId = solution.getProblemId();
        this.message = message;
        this.evaluationCode = 5;
        this.isSuccessful = false;
    }

    public CodeEvaluation(UserSolution solution, EvaluationRun run) {
        this.isSuccessful = false;
        this.userId = solution.getUserId();
        this.problemId = solution.getProblemId();

        // there was a server error trying to run
        if (run == null) {
            evaluationCode = 4;
            message = SERVER_ERROR_ERROR_MSG;
        // solution got got timeout
        } else if(run.timedOut) {
            evaluationCode = 3;
            message = TIMEOUT_MSG;
        // solution had a runtime error
        } else if(run.exitCode != 0) {
            evaluationCode = 2;
            // show runtime error if the exit code wasn't 0
            message = run.output;
        // solution didn't produce the expected output
        } else if(!solution.getExpectedOutput().trim().equals(run.output.trim())) {
            evaluationCode = 1;
            message = WRONG_ANSWER_MESSAGE;
        // GOOD SOLUTION!!!! we got a winner!
        } else {
            evaluationCode = 0;
            message = SUCCESS_MSG;
            isSuccessful = true;
        }
    }

    public String getMessage() {
        return message;
    }

    public int getEvaluationCode() {
        return evaluationCode;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Override
    public String toString() {
        return String.format(
                "{ 'userId': %s, 'problemId': %s, 'isSuccess': %s, 'evaluateCode': %d, 'message': %s }",
                userId, problemId, String.valueOf(isSuccessful), evaluationCode, message);
    }
}