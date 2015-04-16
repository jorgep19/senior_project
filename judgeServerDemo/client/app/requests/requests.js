'use strict';

angular.module('judgeServerDemoApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('requests', {
        url: '/requests',
        templateUrl: 'app/requests/requests.html',
        controller: 'RequestsCtrl'
      });
  });