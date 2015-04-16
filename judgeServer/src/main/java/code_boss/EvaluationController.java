package code_boss;

import code_boss.model.EvaluationCheckResponse;
import code_boss.model.EvaluationRequestResponse;
import code_boss.model.UserSolution;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class EvaluationController {
    private static final EvaluationDirector director = new EvaluationDirector();

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public @ResponseBody EvaluationCheckResponse eval(
            @RequestParam(value="userId") String userId,
            @RequestParam(value="problemId") String problemId) {
        System.out.println(String.format("Received check request for userId=%s and problemId=%s", userId, problemId));

        UserSolution solutionToCheck = new UserSolution(userId, problemId);
        EvaluationCheckResponse response = director.evaluationCheck(solutionToCheck);

        System.out.println(String.format("Response for request to check solution with userId=%s and problemId=%s is %s", userId, problemId, response));

        return response;
    }

    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    // solution data is received as a string to manually validate the data
    public @ResponseBody EvaluationRequestResponse checkCodeSubmission(@RequestBody String solutionData) {
        UserSolution solution = null;
        try {
            JSONObject data = new JSONObject(solutionData);
            solution = new UserSolution(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Received request to evaluate %s", solution));

        EvaluationRequestResponse response = solution != null ?
                director.evaluateSolution(solution) :
                new EvaluationRequestResponse(400, "Unable to parse the receive solution data please double check the json format");

        System.out.println(String.format("Response for request to evaluate %s is %s", solution, response));

        return response;
    }
}