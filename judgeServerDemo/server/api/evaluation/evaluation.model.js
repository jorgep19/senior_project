'use strict';

var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

var EvaluationSchema = new Schema({
  userId: String,
  problemId: String,
  isSuccess: Boolean,
  evaluateCode: Number,
  message: String

});

module.exports = mongoose.model('Evaluation', EvaluationSchema);
