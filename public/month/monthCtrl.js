angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "journalEntry", "JournalEntry", "Item", function($scope, $routeParams, journalEntry, JournalEntry, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.prevMonth = moment($scope.month).subtract(1, "months").format("YYYYMM");
	$scope.nextMonth = moment($scope.month).add(1, "months").format("YYYYMM");
	// load items
	$scope.items = Item.byMonth({ month: $routeParams.month });
	// load day entries
	JournalEntry.byMonth({ id: $routeParams.month }).$promise.then(function(response) {
		$scope.journalEntries = journalEntry.allMonthDays($routeParams.month, response);
	});

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
