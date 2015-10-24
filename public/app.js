angular.module('acad', [
	'angularMoment',
	'month',
	'ngRoute',
	'ui.bootstrap'
	])
.config(['$routeProvider', function($routeProvider) {

	$routeProvider.otherwise({
		redirectTo: '/month/201510'
	});

}])
.run(['amMoment', function(amMoment) {
	amMoment.changeLocale('fr');
}])
// from http://fdietz.github.io/recipes-with-angular-js/common-user-interface-patterns/editing-text-in-place-using-html5-content-editable.html
.directive("contenteditable", function() {
	return {
		restrict: "A",
		require: "ngModel",
		link: function(scope, element, attrs, ngModel) {

			function read() {
				ngModel.$setViewValue(element.html());
			}

			ngModel.$render = function() {
				element.html(ngModel.$viewValue || "");
			};

			element.bind("blur keyup change", function() {
				scope.$apply(read);
			});
		}
	};
});
