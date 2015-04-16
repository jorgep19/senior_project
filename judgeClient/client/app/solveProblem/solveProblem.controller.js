angular.module('codeBossApp')
  .controller('SolveProblemCtrl', function ($scope, $http, $location, $stateParams, Auth, $sanitize) {

  		console.log($stateParams.problemId);
       $scope.getCurrentUser = Auth.getCurrentUser;

  	 
  	  $scope.clickCount = 0;	

      $http.get('/api/problems/' + $stateParams.problemId).success(function(solveProblem) {
        $scope.problem = solveProblem;
        $scope.problemDifficulty = $scope.problem.difficulty;
        $scope.problemID = $scope.problem._id;
        $scope.problemName = $scope.problem.name;
        $scope.problemDescription = $scope.problem.description;
        $scope.example = $scope.problem.example;
        $scope.theInput = $scope.problem.input;
        $scope.output = $scope.problem.output;
        $scope.timeoutNumber = 5000;
        $scope.theLanguage = "java";
        $scope.responseURL = "";
      });	

      $scope.clearField = function() {
        $scope.code="";
        //$scope.outputAfter = "Hello";
      };

       $scope.sendSubmission = function(theCode,userID,problemID,problemDiff,input,output) {


        $scope.convertedInput = input.replace(/<br\s*\/?>/mg,"\n");
        $scope.convertedOutput = output.replace(/<br\s*\/?>/mg,"\n");

        console.log(theCode+"\n"+userID+"\n"+problemID+"\n"+problemDiff+"\n"+$scope.convertedInput+"\n"+$scope.convertedOutput+"\n"+$scope.theLanguage+"\n"+
          $scope.timeoutNumber+"\n"+$scope.responseURL+"\n");

        $http.post('/api/solutions', {code:theCode, userId:userID, problemId:problemID,
          problemDifficulty:problemDiff, testInput:$scope.convertedInput, expectedOutput:$scope.convertedOutput,
          language:$scope.theLanguage,timeout:$scope.timeoutNumber,responseURL:$scope.responseURL}).
        
        
          success(function(data, status, headers, config) {
          // this callback will be called asynchronously
          // when the response is available
          $scope.outputAfter = status;
          if(status==201){
            $scope.outputAfter = "Entered Response Succesfully";
          }
          }).
          error(function(data, status, headers, config) {
            // called asynchronously if an error occurs
          // or server returns response with an error status.
          }); 
          $scope.clearField();
      };
	
  });
