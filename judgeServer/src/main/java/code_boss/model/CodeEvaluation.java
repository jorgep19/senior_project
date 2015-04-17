package code_boss.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeEvaluation {
    private static final String SERVER_ERROR_ERROR_MSG = "There was a internal error please try submitting the solution again";
    private static final String TIMEOUT_MSG = "The implementation of the solution didn't finished within the time required";
    private static final String SUCCESS_MSG = "The solution was accepted!";

    private static final String USER_ID_KEY = "userId";
    private static final String PROBLEM_ID_KEY = "problemId";
    private static final String MESSAGE_KEY = "message";
    private static final String EVAL_CODE_KEY = "evaluationCode";
    private static final String SUCCESS_KEY = "isSuccess";

    private String userId;
    private String problemId;
    private String message;
    private int evaluationCode;
    private boolean isSuccess;

    // constructor only for compilation issues
    public CodeEvaluation(UserSolution solution, String message) {
        this.userId = solution.getUserId();
        this.problemId = solution.getProblemId();
        this.message = message;
        this.evaluationCode = 5;
        this.isSuccess = false;
    }

    public CodeEvaluation(UserSolution solution, EvaluationRun run) {
        this.isSuccess = false;
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
            message =  run.output;
        // GOOD SOLUTION!!!! we got a winner!
        } else {
            evaluationCode = 0;
            message = SUCCESS_MSG;
            isSuccess = true;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "{ 'userId': %s, 'problemId': %s, 'isSuccess': %s, 'evaluateCode': %d, 'message': %s }",
                userId, problemId, String.valueOf(isSuccess), evaluationCode, message);
    }

    public JSONObject toJSON() {
        JSONObject json = null;
        try {
            json = new JSONObject();
            json.put(USER_ID_KEY, userId);
            json.put(PROBLEM_ID_KEY, problemId);
            json.put(MESSAGE_KEY, message);
            json.put(EVAL_CODE_KEY, evaluationCode);
            json.put(SUCCESS_KEY, isSuccess);
        } catch (JSONException e) {
            System.out.println(
                    String.format("Unable to create json for evaluation with userId %s and problemId %s",
                        userId,
                        problemId));
        }

        return json;
    }
}