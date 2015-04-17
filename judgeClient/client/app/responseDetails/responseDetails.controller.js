'use strict';

angular.module('codeBossApp')
  .controller('ResponseDetailsCtrl', function ($scope, $stateParams, $http) {
    $scope.message = 'Hello';
    var responseId = $stateParams.id;
    $http.get('/api/responses/' + responseId).
      success(function(data) {
        $scope.response = data;

       console.log(data.problemId);
       $scope.searchForProblem(data.problemId);
      });

        $scope.searchForProblem = function(problemID){
        $http.get('/api/problems').success(function(problems) {
        $scope.getProblems = problems;
        for(var i = 0 ;i < $scope.getProblems.length; i++){
        if(problemID == $scope.getProblems[i]._id){
        $scope.problemNames = $scope.getProblems[i].name;
        console.log($scope.problemNames);
        	return $scope.problemNames;
        	
          		}
      		}
     });
  }

      $scope.getEvaluationLabel = function(evaluation) {
      if(evaluation == 0){
      	return "Accepted";
      } else {
      	if(evaluation == 1){
      		return "Wrong Answer";
      	} else {
      		if(evaluation == 2){
      			return "Run Time Error";
      		} else {
      			if(evaluation == 3){
      				return "Timeout";
      			}else {
      				if(evaluation == 4){
      					return "Server Error";
      				} else {
      					return "Compilation Error";
      					}		
      				}
      			}
      		}
      	}
      }
      
    


  });
