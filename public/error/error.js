angular.module("error", [])
.directive("error", function() {
	return {
		scope: {},
		templateUrl: "/error/error.html",
		controller: "errorCtrl"
	};
})
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
}])
.factory("error", [ "$rootScope", function($rootScope) {

	var codeToError = {
		invalid_month: "Mois invalide"
	};
	var statusToError = {
		400: "Requête invalide",
		403: "Accès interdit",
		404: "Ressource introuvable",
		500: "Erreur interne"
	};
	var defaultError = "Une erreur est survenue";

	var errorService = {};
	errorService.showError = function(text) {
		$rootScope.$broadcast("error", { text: text });
	};

	errorService.ajaxError = function(response) {
		var responseError = response.data ? response.data.error : null;
		var error = codeToError[responseError];
		if (!error) {
			error = statusToError[response.status];
		}
		if (!error) {
			error = defaultError;
		}
		errorService.showError(error);
	};

	return errorService;
}])
.factory("errorHttpInterceptor", [ "$q", "error", function($q, error) {
	return {
		responseError: function(response) {
			error.ajaxError(response);
			return $q.reject(response);
		}
	};
}]);
