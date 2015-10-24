angular.module('month', [])
.config(['$routeProvider', function($routeProvider) {
  
  $routeProvider.when('/month/:month', {
    templateUrl: 'month/month.html',
    controller: 'monthCtrl'
  });

}]);
