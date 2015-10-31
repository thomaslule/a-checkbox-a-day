angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "daySummary", "DaySummary", "Item", function($scope, $routeParams, daySummary, DaySummary, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	// load items
	$scope.items = Item.byMonth({ id: $routeParams.month });
	// load day summaries
	DaySummary.byMonth({ id: $routeParams.month }).$promise.then(function(response) {
		$scope.daySummaries = daySummary.allMonthDays($routeParams.month, response);
	});

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
