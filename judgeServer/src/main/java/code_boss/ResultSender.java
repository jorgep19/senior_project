package code_boss;

import code_boss.model.CodeEvaluation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ResultSender {

    public void send(CodeEvaluation evaluation, String url) throws UnsupportedEncodingException {
        System.out.println(String.format("Sending result %s to %s", evaluation, url));

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        // Request parameters and other properties.
        StringEntity entityForPost = new StringEntity(evaluation.toJSON().toString());
        post.setHeader("content-type", "application/json");
        post.setEntity(entityForPost);

        HttpResponse response;
        InputStream is = null;
        try {
            //Execute and get the response.
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
