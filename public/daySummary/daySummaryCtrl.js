angular.module("daySummary")
.controller("daySummaryCtrl", ["$scope", "DaySummary", function($scope, DaySummary) {
	$scope.change = function() {
		// TODO
		console.log($scope.model);
	}
}]);
