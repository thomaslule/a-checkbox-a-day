angular.module("acad", [
	"angularMoment",
	"contenteditable",
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
.run(["amMoment", function(amMoment) {
	amMoment.changeLocale("fr");
}]);
