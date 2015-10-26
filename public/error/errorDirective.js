angular.module("error")
.directive("error", function() {
	return {
		scope: {},
		templateUrl: "/error/error.html",
		controller: "errorCtrl"
	};
})
