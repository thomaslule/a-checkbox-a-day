angular.module("item")
.controller("itemCtrl", ["$scope", "Item", function($scope, Item) {
	$scope.change = function() {
		$scope.model.$update({ id: $scope.model.id });
	}
	$scope.cancel = function() {
		$scope.model.status = 'cancelled';
		$scope.change();
	}
	$scope.restore = function() {
		$scope.model.status = 'active';
		$scope.change();
	}
	$scope.delete = function() {
		$scope.model.$delete({ id: $scope.model.id });
	}
}]);
