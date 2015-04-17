'use strict';

angular.module('judgeServerDemoApp')
  .controller('RequestsCtrl', function ($scope, $http) {
    $http.get('/api/requests').
      success(function(data) {
          $scope.requests = data;
          console.log(data[0]);
      });

    $scope.getEvaluationDescription = function(evaluation) {
      var description;
      switch(evaluation.evaluationCode) {
        case 0:
          description = "Good Answer!";
          break;
        case 1:
          description = "Code Produce a Wrong Output";
          break;
        case 2:
          description = "Run Time Error";
          break;
        case 3:
          description = "Implementation timed out";
          break;
        case 4:
          description = "Server Error... please try Again";
          break;
        case 5:
          description = "Compilation Error";
          break;
      }
      return description;
    }
  });
