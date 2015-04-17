'use strict';

var _ = require('lodash');
var Evaluation = require('./evaluation.model');
var Request = require('../request/request.model');

exports.receiveEvaluation = function(req, res) {
  var evaluation = req.body;
  console.log(evaluation);

  //{ message: 'The solution was accepted!',
  //  evaluationCode: 0,
  //  problemId: '19',
  //  userId: 'jorgep',
  //  isSuccess: true }

    Request.find(
      {userId: evaluation.userId, problemId: evaluation.problemId},
      function(err, requests) {
        if (err) {
          console.error(err);
          return res.json(err);
        }

        // should only be one
        if(requests.length > 0) {
          requests[0].evaluation = [evaluation];
          requests[0].save(function (err, evaluatedRequest) {
            if (err) {
              console.error(err);
              return res.json(err);
            }

            return res.json(201, evaluatedRequest);
          });
        } else {
          return res.send("Didn't find request matching evaluation ids");
        }
    });
};

function handleError(res, err) {
  return res.send(500, err);
}
