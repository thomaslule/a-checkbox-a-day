angular.module("error")
.controller("errorCtrl", [ "$scope", "error", function($scope, $error) {
	$scope.errorText = "";
	$scope.displayed = false;

	$error._setShowErrorCallback(function(text) {
		$scope.errorText = text;
		$scope.displayed = true;
	});

	$scope.close = function() {
		$scope.displayed = false;
	}
}]);
