'use strict';

var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

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
  evaluation: {
    message: String,
    evaluationCode: Number,
    isSuccess: Boolean
  }
});

module.exports = mongoose.model('Request', RequestSchema);
