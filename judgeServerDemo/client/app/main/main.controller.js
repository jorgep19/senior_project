'use strict';

angular.module('judgeServerDemoApp')
  .controller('MainCtrl', function ($scope, $http, $sanitize) {
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

    $scope.response = undefined;

    $scope.submit = function(theLanguage, theUserId, theProblemId, theDifficulty, theTimeout, theTestInput, theExpectedOutput, theCode) {
      $scope.solution = {
        "userId": theUserId,
        "problemId": theProblemId,
        "language": theLanguage,
        "difficulty": theDifficulty,
        "code": theCode,
        "testInput": theTestInput,
        "expectedOutput": theExpectedOutput,
        "timeout": theTimeout
      };

      $http.post("/api/requests", $scope.solution)
        .success(function(data) {
          $scope.response = data;
        });
    };

    $scope.setToJavaCompFail = function() {
      $scope.theLanguage = "java";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Java Compilation Failure";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); }";
    };


    $scope.setToCppCompFail = function() {
      $scope.theLanguage = "cpp";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "C++ compilition Failure";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "#include <iostream> <br>using namespace std; int main() { cout << x; return 0; }";
    };


    $scope.setTotimeOut = function() {
      $scope.theLanguage = "java";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Hello Timeout";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 0;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }";
    };



    $scope.setToWrongAnswer = function() {
      $scope.theLanguage = "java";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Wrong Answer";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Aloha!";
      $scope.theCode = "public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }";
    };


    $scope.setRunTimeError = function() {
      $scope.theLanguage = "java";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Out of Bounds";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "public class Main { public static void main(String[] args) { int[] arr = new int[3]; System.out.println(arr[-1]); } }";
    };

    $scope.setCppSuccess = function() {
      $scope.theLanguage = "cpp";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Adding Cs";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "2 3 33 44 3 4 0";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "#include <iostream> <br>using namespace std; int main() { int a,b; cin >> a; while(a != 0) { cin >> b; cout << a + b <<endl; cin >> a; } return 0; }";
    };

    $scope.setJavaSuccess = function() {
      $scope.theLanguage = "java";
      $scope.theUserId = "jorgep";
      $scope.theProblemId = "Hello Java";
      $scope.theDifficulty = 1;
      $scope.theTimeout = 3000;
      $scope.theTestInput = "";
      $scope.theExpectedOutput= "Hello World!";
      $scope.theCode = "public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }";
    };

  });
