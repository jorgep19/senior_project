'use strict';

angular.module('judgeServerDemoApp')
  .controller('RequestsCtrl', function ($scope, $http) {
    $http.get('/api/requests').
      success(function(data) {
          $scope.requests = data;
      });

  });
