'use strict';

var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    Evaluation = require('../evaluation/evaluation.model');

var RequestSchema = new Schema({
  userId: String,
  problemId: String,
  language: String,
  difficulty: Number,
  code: String,
  testInput: String,
  expectedOutput: String,
  timeout: Number,
  responseURL: String,
  evaluation: [Evaluation]
});

module.exports = mongoose.model('Request', RequestSchema);
