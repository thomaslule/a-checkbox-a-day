angular.module("item")
.directive('item', function() {
	return {
		restrict: 'AE',
		scope: {
			model: '='
		},
		templateUrl: '/item/item.html',
		controller: 'itemCtrl'
	};
});
