'use strict';

angular.module('codeBossApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('responseDetails', {
        url: '/responseDetails/:id',
        templateUrl: 'app/responseDetails/responseDetails.html',
        controller: 'ResponseDetailsCtrl'
      });
  });