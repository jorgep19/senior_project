package code_boss.model;

public class EvaluationRequestResponse {
    private int statusCode;
    private String message;

    public EvaluationRequestResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{ 'statusCode': %d, 'message': %s}", statusCode, message);
    }
}
