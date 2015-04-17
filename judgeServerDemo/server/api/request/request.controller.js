'use strict';

var requestify = require('requestify');
var _ = require('lodash');
var Request = require('./request.model');
var judgeAPIUrl = "http://localhost\:8080/evaluate";
var responseURL = "http://localhost\:9000/api/evaluations/receive";

// Get list of requests
exports.index = function(req, res) {
  Request.find(function (err, requests) {
    if(err) { return handleError(res, err); }
    return res.json(200, requests);
  });
};

// Creates a new request in the DB.
exports.create = function(req, res) {
  var evalutionrequest = req.body;
  evalutionrequest.responseURL = responseURL;
  evalutionrequest.evaluation = null;
  console.log("Sending: " + JSON.stringify(evalutionrequest) + " to " + judgeAPIUrl);

  // send to judge
  requestify.post(judgeAPIUrl, evalutionrequest).then(function(response) {
    var result = response.getBody();
    console.log(result);

    if(result.statusCode == 200) {
      // only save if there no request for this problem on the database already
      Request.find(
        {userId: evalutionrequest.userId, problemId: evalutionrequest.problemId},
        function(err, requests) {
          if(err) { return handleError(res, err); }

          // save only if there are aren't entries already
          if(requests.length > 0) {
            return res.json(result);
          } else {
            Request.create(evalutionrequest, function(err, request) {
              if(err) { return handleError(res, err); }

              console.log("Saved to database: " + JSON.stringify(request));
              return res.json(result);
            });
          }
      });
    } else {
      return res.json(result);
    }
  });
};

exports.show = function(req, res) {
  Request.findById(req.params.id, function (err, request) {
    if(err) { return handleError(res, err); }
    if(!request) { return res.send(404); }
    return res.json(request);
  });
};

function handleError(res, err) {
  return res.send(500, err);
}
