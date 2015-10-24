angular.module("item")
.directive('item', function() {
	return {
		restrict: 'E',
		scope: {
			model: '='
		},
		templateUrl: '/item/item.html',
		controller: 'itemCtrl'
	};
})
.directive('newItem', function() {
	return {
		restrict: 'E',
		scope: {
			listType: '@',
			listId: '@',
			onSave: '&'
		},
		templateUrl: '/item/newItem.html',
		controller: 'newItemCtrl'
	};
});
