'use strict';

angular.module('codeBossApp')
  .controller('SuggestCtrl', function ($scope, $http, $location) {

        $scope.clearFields = function(){
          $scope.name="";
           $scope.difficulty="";
           $scope.description="";
           $scope.example="";
           $scope.input="";
           $scope.output="";
        }

  		  $scope.submitButton = function(name,difficulty,description,example,input,output) {

           $scope.problemName=name;
           $scope.problemDifficulty=difficulty;
           $scope.problemDescription=description;
           $scope.problemExample=example;
           $scope.problemInput=input;
           $scope.problemOutput=output;

           if($scope.problemDifficulty=="Easy"){
              $scope.problemDifficulty=1;
            } else { 
              if($scope.problemDifficulty=="Medium"){
                $scope.problemDifficulty=2;
              } else {
                  $scope.problemDifficulty=3;
              }
            }
              console.log($scope.problemDifficulty);
        

           // Simple POST request example (passing data) :
				$http.post('/api/problems', {name:$scope.problemName, active:true, difficulty:$scope.problemDifficulty,
					description:$scope.problemDescription, example:$scope.problemExample, input:$scope.problemInput,
					output:$scope.problemOutput}).
				
  				success(function(data, status, headers, config) {
    			// this callback will be called asynchronously
    			// when the response is available
  				}).
  				error(function(data, status, headers, config) {
    				// called asynchronously if an error occurs
    			// or server returns response with an error status.
  				});

          console.log($scope.problemName);
          console.log($scope.problemDifficulty);
          console.log($scope.problemDescription);
          console.log($scope.problemExample);
          console.log($scope.problemInput);
          console.log($scope.problemOutput);
          $scope.clearFields();

          }  


          
  });
