angular.module("acad", [
	"angularMoment",
	"contenteditable",
	"daySummary",
	"error",
	"month",
	"ngRoute",
	"ui.bootstrap"
])
.config(["$routeProvider", function($routeProvider) {

	$routeProvider.otherwise({
		redirectTo: "/month/201510"
	});

}])
.config([ "$httpProvider", function($httpProvider) {
    $httpProvider.interceptors.push("errorHttpInterceptor");
}])
.run(["amMoment", function(amMoment) {
	amMoment.changeLocale("fr");
}]);
