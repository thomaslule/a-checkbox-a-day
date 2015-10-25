angular.module('contenteditable')
.directive("contenteditable", function() {
	return {
		restrict: "A",
		require: "ngModel",
		link: function(scope, element, attrs, ngModel) {

			function read() {
				ngModel.$setViewValue(element.text());
			}

			ngModel.$render = function() {
				element.text(ngModel.$viewValue || "");
			};

			element.bind("blur keyup change", function() {
				scope.$apply(read);
			});
			element.bind("keydown keypress", function(e) {
				// leave editor on enter
				if (e.keyCode === 13) {
					element[0].blur();
					e.preventDefault();
				}
			});
		}
	};
});
