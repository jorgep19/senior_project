'use strict';

var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

var EvaluationSchema = new Schema({
  name: String,
  info: String,
  active: Boolean
});

module.exports = mongoose.model('Evaluation', EvaluationSchema);