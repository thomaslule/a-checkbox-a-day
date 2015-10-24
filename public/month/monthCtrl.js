angular.module("month")
.controller("monthCtrl", ["$scope", "$routeParams", "Item", function($scope, $routeParams, Item) {
	$scope.month = moment($routeParams.month, "YYYYMM", true);
	$scope.items = Item.byMonth({ id: $routeParams.month });
	$scope.newItem = new Item();
	$scope.newItem.type = "task";
	$scope.newItem.list_type = "month";
	$scope.newItem.list_id = $routeParams.month;

	$scope.add = function() {
		$scope.newItem.$save(function(response) {
			$scope.items.push(response);
		});
		$scope.newItem = new Item();
		$scope.newItem.type = "task";
		$scope.newItem.list_type = "month";
		$scope.newItem.list_id = $routeParams.month;
	}
}]);
