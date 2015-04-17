  'use strict';

angular.module('codeBossApp')
  .controller('StatisticsCtrl', function ($scope, $http, Auth) {
    $scope.getCurrentUser = Auth.getCurrentUser;

    //console.log($scope.getCurrentUser()._id);

 $scope.getResponses="";
 $scope.getProblems="";
 $scope.problemIds = [];
 $scope.problemNames = "";

   $scope.getCurrentUser = Auth.getCurrentUser;
    $http.get('/api/responses').success(function(responses) {
      $scope.getResponses = responses;
      //console.log($scope.getResponses[0].problemId);

      for (var i = 0 ; i < $scope.getResponses.length ; i++) {
        if(($scope.getCurrentUser()._id == $scope.getResponses[i].userId) && ($scope.getResponses[i].evaluationCode == 0)){
            $scope.problemIds[i] = $scope.getResponses[i].problemId;
            console.log($scope.problemIds[i]);
            $scope.searchForProblem($scope.problemIds[i]);
            }
          };
        });

    $scope.searchForProblem = function(problemID){
    $http.get('/api/problems').success(function(problems) {
      $scope.getProblems = problems;
      for(var i = 0 ;i < $scope.getProblems.length; i++){
        if(problemID == $scope.getProblems[i]._id){
            $scope.problemNames = $scope.getProblems[i].name;
          }
      }
     
        });
    return $scope.problemNames;
  }

  });
  

  
