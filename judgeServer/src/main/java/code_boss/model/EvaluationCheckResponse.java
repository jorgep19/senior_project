package code_boss.model;

public class  EvaluationCheckResponse {
    public static final String EVALUATING_MSG = "A solution with userId=%s and problemId=%s is currently being evaluated";
    public static final String IN_QUEUE_MSG =  "A solution with userId=%s and problemId=%s is queued for evaluation";
    public static final String NOT_FOUND_MSG = "Didn't found a solution submitted with userId=%s and problemId=%s";

    private int status;
    private String message;

    public EvaluationCheckResponse(EvaluationCheckResults result, UserSolution solution) {
        this.status = result.ordinal();
        switch (result){
            case EVALUATING:
                this.message = String.format(EVALUATING_MSG, solution.getUserId(), solution.getProblemId());
                break;
            case IN_QUEUE:
                this.message = String.format(IN_QUEUE_MSG, solution.getUserId(), solution.getProblemId());
                break;
            case NOT_FOUND:
                this.message = String.format(NOT_FOUND_MSG, solution.getUserId(), solution.getProblemId());
                break;
        }
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{'status': %d, 'message': '%s'}", status, message);
    }
}
