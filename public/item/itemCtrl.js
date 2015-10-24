angular.module("item")
.controller("itemCtrl", ["$scope", "Item", function($scope, Item) {
	$scope.change = function() {
		$scope.model.$update({ id: $scope.model.id });
	}
}]);
