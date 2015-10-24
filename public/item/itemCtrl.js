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

	$scope.save = function() {
		$scope.newItem.$save(function(response) {
			$scope.onSave({ item: response });
		});
		$scope.newItem = initNewItem();
	}
}]);
