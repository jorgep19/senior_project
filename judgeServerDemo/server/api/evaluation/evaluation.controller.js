'use strict';

var _ = require('lodash');
var Evaluation = require('./evaluation.model');

// Get a single evaluation
exports.show = function(req, res) {
  Evaluation.findById(req.params.id, function (err, evaluation) {
    if(err) { return handleError(res, err); }
    if(!evaluation) { return res.send(404); }
    return res.json(evaluation);
  });
};

// Creates a new evaluation in the DB.
exports.requestEvaluation = function(req, res) {
  Evaluation.create(req.body, function(err, evaluation) {
    if(err) { return handleError(res, err); }
    return res.json(201, evaluation);
  });
};

// Updates an existing evaluation in the DB.
exports.receiveEvaluation = function(req, res) {
  console.log(req.body);
  return res.send("Good :)");
};


function handleError(res, err) {
  return res.send(500, err);
}
