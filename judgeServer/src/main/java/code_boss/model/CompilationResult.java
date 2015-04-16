package code_boss.model;

public class CompilationResult {
    private static final String DEFAULT_MESSAGE = "All gud :)";

    private int languageIndex;
    private boolean isSuccess;
    private boolean isServerError;
    private String message;
    private UserSolution solution;
    private String compiledFilePath;
    private boolean isFileCreated;

    public CompilationResult(String message) {
        this.languageIndex = -1;
        this.isSuccess = false;
        this.isServerError = true;
        this.message = message;
        this.solution = null;
        this.compiledFilePath = null;
        this.isFileCreated = false;
    }

    public CompilationResult(
            int languageIndex,
            UserSolution solution,
            String compiledFilePath,
            boolean isSuccess) {
        this.languageIndex = languageIndex;
        this.isSuccess = isSuccess;
        this.isServerError = false;
        this.message = DEFAULT_MESSAGE;
        this.compiledFilePath = compiledFilePath;
        this.solution = solution;
        this.isFileCreated = true;
    }

    public CompilationResult(
            int languageIndex,
            UserSolution solution,
            String compiledFilePath,
            boolean isSuccess,
            String message) {
        this.languageIndex = languageIndex;
        this.isSuccess = isSuccess;
        this.isServerError = false;
        this.message = message;
        this.compiledFilePath = compiledFilePath;
        this.solution = solution;
        this.isFileCreated = true;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isServerError() {
        return isServerError;
    }

    public String getMessage() {
        return message;
    }

    public int getLanguageIndex() {
        return languageIndex;
    }

    public UserSolution getSolution() {
        return solution;
    }

    public String getCompiledFilePath() {
        return compiledFilePath;
    }

    public boolean isFileCreated() {
        return isFileCreated;
    }
}
