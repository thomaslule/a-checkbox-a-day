angular.module("item")
.directive('item', function() {
	return {
		restrict: 'AE',
		scope: {
			model: '=model'
		},
		templateUrl: '/item/item.html'
	};
});
