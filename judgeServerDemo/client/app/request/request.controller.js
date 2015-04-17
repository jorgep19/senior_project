'use strict';

angular.module('judgeServerDemoApp')
  .controller('RequestCtrl', function ($scope, $stateParams, $http) {

    var requestId = $stateParams.id;

    $http.get('/api/requests/' + requestId).
      success(function(data) {
        $scope.request = data;
        console.log(data);
      });
      $scope.description = "";
    $scope.getEvaluationLabel = function(evaluation) {
    	console.log(evaluation);
      }

      var description;
      switch(evaluation.evaluationCode) {
        case 0:
          description = "Accepted";
          break;
        case 1:
          description = "Wrong Answer";
          break;
        case 2:
          description = "Run Time Error";
          break;
        case 3:
          description = "Timeout";
          break;
        case 4:
          description = "Server Error";
          break;
        case 5:
          description = "Compilation Error";
          break;
      }
      return description;
    }
  });
