angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "journalEntry", "JournalEntry", "Item", function($scope, $routeParams, journalEntry, JournalEntry, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	// load items
	$scope.items = Item.byMonth({ id: $routeParams.month });
	// load day entries
	JournalEntry.byMonth({ id: $routeParams.month }).$promise.then(function(response) {
		$scope.journalEntries = journalEntry.allMonthDays($routeParams.month, response);
	});

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
