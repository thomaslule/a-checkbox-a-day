angular.module("item", ["ngResource"])
.directive('item', function() {
	return {
		scope: {
			model: '='
		},
		templateUrl: '/item/item.html',
		controller: 'itemCtrl'
	};
})
.directive('newItem', function() {
	return {
		scope: {
			listType: '@',
			listId: '@',
			onSave: '&'
		},
		templateUrl: '/item/newItem.html',
		controller: 'newItemCtrl'
	};
})
.controller("itemCtrl", ["$scope", function($scope) {
	var month = moment($scope.model.list_id, "YYYYMM", true);
	$scope.moveToList = [
		moment(month).add(1, "month"),
		moment(month).add(2, "month"),
		moment(month).add(3, "month")
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
}])
.filter('translateType', function() {
	return function(input) {
		var translation = { task: "Tâche", event: "Événement", note: "Note" };
		return translation[input];
	};
})
.factory("Item", ["$resource", function($resource) {
	return $resource("api/item/:id", {}, {
		byMonth: {
			method:"GET",
			url: "api/item/month/:month",
			params: { month: "@month" },
			isArray:true
		},
		save: { method: "POST", params: { id: null }},
		update: { method: "PUT", params: {id: "@id" }},
		delete: { method: "DELETE", params: { id: "@id" }},
		move: {
			method: "PUT",
			url: "api/item/:id/month/:month",
			params: { id: "@id", month: "@month" }
		}
	});
}]);
