'use strict';

angular.module('judgeServerDemoApp')
  .controller('RequestCtrl', function ($scope, $stateParams, $http) {

    var requestId = $stateParams.id;

    $http.get('/api/requests/' + requestId).
      success(function(data) {
        $scope.request = data;
        console.log(data);
      });
  });
