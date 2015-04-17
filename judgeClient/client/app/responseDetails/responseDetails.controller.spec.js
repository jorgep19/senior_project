'use strict';

describe('Controller: ResponseDetailsCtrl', function () {

  // load the controller's module
  beforeEach(module('codeBossApp'));

  var ResponseDetailsCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ResponseDetailsCtrl = $controller('ResponseDetailsCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
