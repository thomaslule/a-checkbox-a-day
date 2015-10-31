angular.module("journalEntry")
.controller("journalEntryCtrl", ["$scope", function($scope) {
	$scope.change = function() {
		$scope.model.$save();
	}
}]);
