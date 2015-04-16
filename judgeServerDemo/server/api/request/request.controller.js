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

  console.log("Sending: " + JSON.stringify(evalutionrequest) + " to " + judgeAPIUrl);
  requestify.post(judgeAPIUrl, evalutionrequest).then(function(response) {
      var result = response.getBody();
      console.log(result);

      if(result.statusCode == 200) {
        Request.create(evalutionrequest, function(err, request) {
            if(err) { return handleError(res, err); }

            console.log("Saved to database: " + JSON.stringify(request));
            return res.json(201, result);
        });
      } else {
        return res.json(result);
      }
  });

  //
};

function handleError(res, err) {
  return res.send(500, err);
}