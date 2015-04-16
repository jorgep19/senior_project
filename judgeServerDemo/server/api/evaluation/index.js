'use strict';

var express = require('express');
var controller = require('./evaluation.controller');

var router = express.Router();

router.get('/:id', controller.show);
router.post('/evaluate', controller.requestEvaluation);
router.post('/receive', controller.receiveEvaluation);

module.exports = router;
