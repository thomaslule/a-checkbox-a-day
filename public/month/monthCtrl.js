angular.module('month')
.controller('monthCtrl', ['$scope', '$routeParams', function($scope, $routeParams) {
	$scope.month = moment($routeParams.month, 'YYYY-MM', true);
}]);
