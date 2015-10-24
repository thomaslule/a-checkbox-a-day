angular.module('acad', [
  'ngRoute',
  'month',
  'angularMoment'
])
.config(['$routeProvider', function($routeProvider) {
  
  $routeProvider.otherwise({
    redirectTo: '/month/2015-10'
  });

}])
.run(['amMoment', function(amMoment) {
    amMoment.changeLocale('fr');
}]);
