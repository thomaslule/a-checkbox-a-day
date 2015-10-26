angular.module("error")
.factory("error", [ "$rootScope", function($rootScope) {
	var _showErrorCallback;

	return {
		showError: function(text) {
			$rootScope.$broadcast("error", { text: text });
		},

		ajaxError: function(error) {
			console.log(error);
			return "bluh";
		}
	};
}]);
