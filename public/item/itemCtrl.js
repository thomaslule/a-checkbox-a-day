angular.module("item")
.controller("itemCtrl", ["$scope", function($scope) {
	$scope.moveToList = [
		moment($scope.model.month).add(1, "month"),
		moment($scope.model.month).add(2, "month"),
		moment($scope.model.month).add(3, "month")
	];
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
	$scope.moveTo = function(month) {
		$scope.model.$move({ id: $scope.model.id, month: month.format("YYYYMM") });
	}
}])
.controller("newItemCtrl", ["$scope", "Item", function($scope, Item) {
	var initNewItem = function() {
		var item = new Item();
		item.type = "task";
		item.list_type = $scope.listType;
		item.list_id = $scope.listId;
		return item;
	};
	$scope.newItem = initNewItem();

	$scope.changeType = function(newType) {
		$scope.newItem.type = newType;
	}

	$scope.save = function() {
		$scope.newItem.$save(function(response) {
			$scope.onSave({ item: response });
		});
		$scope.newItem = initNewItem();
	}
}]);
