angular.module("month", [ "journalEntry", "item", "ngRoute" ])
.config(["$routeProvider", function($routeProvider) {
  
  $routeProvider.when("/month/:month", {
    templateUrl: "month/month.html",
    controller: "monthCtrl"
  });

}])
.controller("monthCtrl", ["$scope", "$routeParams", "journalEntry", "JournalEntry", "Item", function($scope, $routeParams, journalEntry, JournalEntry, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.prevMonth = moment($scope.month).subtract(1, "months").format("YYYYMM");
	$scope.nextMonth = moment($scope.month).add(1, "months").format("YYYYMM");
	// load items
	$scope.items = Item.byMonth({ month: $routeParams.month });
	// load day entries
	JournalEntry.byMonth({ month: $routeParams.month }).$promise.then(function(response) {
		$scope.journalEntries = journalEntry.allMonthDays($routeParams.month, response);
	});

	$scope.onNewItem = function(item) {
		$scope.items.push(item);
	}
}]);
