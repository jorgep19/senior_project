'use strict';

angular.module('judgeServerDemoApp')
  .controller('RequestCtrl', function ($scope, $stateParams) {
    $scope.message = $stateParams.id;
  });
