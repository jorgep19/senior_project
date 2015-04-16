package code_boss;

import code_boss.model.CodeEvaluation;

public class ResultSender {

    public void send(CodeEvaluation evaluation, String url) {
        System.out.println(String.format("Sending result %s to %s", evaluation, url));
    }
}
