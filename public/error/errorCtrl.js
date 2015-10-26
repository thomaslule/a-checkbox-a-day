angular.module("error")
.controller("errorCtrl", [ "$scope", "error", function($scope, $error) {
	$scope.errorText = "";
	$scope.displayed = false;

	$scope.$on("error", function (event, data) {
		$scope.errorText = data.text;
		$scope.displayed = true;
	});

	$scope.close = function() {
		$scope.displayed = false;
	}
}]);
