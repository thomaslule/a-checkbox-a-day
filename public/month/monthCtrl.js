angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "daySummary", "DaySummary", "Item", function($scope, $routeParams, daySummary, DaySummary, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.items = Item.byMonth({ id: $routeParams.month });
	// TODO
	$scope.daySummaries = daySummary.allMonthDays($routeParams.month, DaySummary.byMonth({ id: $routeParams.month }));

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
