angular.module("daySummary")
.controller("daySummaryCtrl", ["$scope", function($scope) {
	$scope.change = function() {
		$scope.model.$save();
	}
}]);
