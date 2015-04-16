'use strict';

angular.module('codeBossApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('statistics', {
        url: '/statistics',
        templateUrl: 'app/statistics/statistics.html',
        controller: 'StatisticsCtrl'
      });
  });