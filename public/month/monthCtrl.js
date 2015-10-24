angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "Item", function($scope, $routeParams, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.items = Item.byMonth({ id: $routeParams.month });

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
