'use strict';

angular.module('judgeServerDemoApp')
  .controller('NavbarCtrl', function ($scope, $location) {
    $scope.menu = [
      {
        'title': 'Home',
        'link': '/'
      },
      {
        'title': 'Requests',
        'link': '/requests'
      }
    ];

    $scope.isCollapsed = true;

    $scope.isActive = function(route) {
      return route === $location.path();
    };
  });
