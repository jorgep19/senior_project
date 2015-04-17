'use strict';

var mongoose = require('mongoose'),
    Schema = mongoose.Schema;

var ResponseSchema = new Schema({
  userId: String,
  problemId: String,
  isSuccess: Boolean,
  evaluationCode: Number,
  message: String,
});

module.exports = mongoose.model('Response', ResponseSchema);