'use strict';

var _ = require('lodash');
var Request = require('../request/request.model');

exports.receiveEvaluation = function(req, res) {
  var evaluation = req.body;
  console.log("evaluation received " + JSON.stringify(evaluation));

    Request.find(
      { userId: evaluation.userId, problemId: evaluation.problemId },
      function(err, requests) {
        if (err) {
          console.error(err);
          return res.json(err);
        }


        console.log("found this request matching" + JSON.stringify(requests));
        // should only be one
        if(requests.length > 0) {
          var withEval = requests[0];
          withEval.evaluation = evaluation;
          var updated = _.merge(requests[0], withEval);

          updated.save(function (err, up) {
            if (err) { return handleError(res, err); }
            return res.json(200, up);
          });
        } else {
          return res.send("Didn't find request matching evaluation ids");
        }
    });
};

function handleError(res, err) {
  return res.send(500, err);
}
