package code_boss.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSolution {
    private JSONObject data;
    private String userId;
    private String problemId;
    private String language;
    private int difficulty;
    private String code;
    private String testInput;
    private String expectedOutput;
    private int timeout;
    private String responseURL;

    public UserSolution(String userId, String problemId) {
        this.userId = userId;
        this.problemId = problemId;
        this.language = null;
        this.difficulty = 0;
        this.code = null;
        this.testInput = null;
        this.expectedOutput = null;
        this.timeout = 0;
        this.responseURL = null;
    }

    public UserSolution(JSONObject data) throws JSONException {
        this.data = data;
        this.userId = data.getString("userId");
        this.problemId = data.getString("problemId");
        this.language = data.getString("language");
        this.difficulty = data.getInt("difficulty");
        this.code = data.getString("code");
        this.testInput = data.getString("testInput");
        this.expectedOutput = data.getString("expectedOutput");
        this.timeout = data.getInt("timeout");
        this.responseURL = data.getString("responseURL");
    }

    public String getUserId() {
        return userId;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getLanguage() {
        return language;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getCode() {
        return code;
    }

    public String getTestInput() {
        return testInput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getResponseURL() {
        return responseURL;
    }

    @Override
    public boolean equals(Object other) {
        // check reference, not null and matching class. Additionally the userId and problemId.
        return this == other ||
                other != null && getClass() == other.getClass() &&
                this.userId.equals(((UserSolution) other).userId) &&
                this.problemId.equals(((UserSolution) other).problemId);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
