angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "Item", "error", function($scope, $routeParams, Item, error) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.items = Item.byMonth({ id: $routeParams.month });
	// TODO
	$scope.daySummaries = [ { date: moment(), text: "test" } ];

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
