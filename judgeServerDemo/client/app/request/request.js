'use strict';

angular.module('judgeServerDemoApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('request', {
        url: '/request/:id',
        templateUrl: 'app/request/request.html',
        controller: 'RequestCtrl'
      });
  });
