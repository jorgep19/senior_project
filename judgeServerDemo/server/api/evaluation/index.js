'use strict';

var express = require('express');
var controller = require('./evaluation.controller');

var router = express.Router();

router.post('/receive', controller.receiveEvaluation);

module.exports = router;
