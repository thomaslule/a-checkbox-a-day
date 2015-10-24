angular.module("item")
.directive('item', function() {
	return {
		scope: {
			model: '='
		},
		templateUrl: '/item/item.html',
		controller: 'itemCtrl'
	};
})
.directive('newItem', function() {
	return {
		scope: {
			listType: '@',
			listId: '@',
			onSave: '&'
		},
		templateUrl: '/item/newItem.html',
		controller: 'newItemCtrl'
	};
});
