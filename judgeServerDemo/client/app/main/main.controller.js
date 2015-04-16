'use strict';

angular.module('judgeServerDemoApp')
  .controller('MainCtrl', function ($scope, $http) {
    $http.defaults.useXDomain = true;
    $scope.judgeAPIUrl = "http://localhost\:8080/evaluate";

    $scope.languages = [ "java", "cpp"];

    $scope.difficulties = [
      {
        id: 0,
        name: "easy"
      },
      {
        id: 1,
        name: "medium"
      },
      {
        id: 2,
        name: "hard"
      },
      {
        id: 3,
        name: "dave hw"
      }
    ];

    $scope.solution = {
      "userId": "jorgep",
      "problemId": "19",
      "language": "java",
      "difficulty": 1,
      "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }",
      "testInput": "",
      "expectedOutput": "Hello World!",
      "timeout": 5000,
      "responseURL": "http://www.google.com"
    };

    $scope.response = undefined;

    $scope.submit = function() {
      $http.post($scope.judgeAPIUrl, $scope.solution)
        .success(function(data) {
          $scope.response = data;
        });
    };
  });
