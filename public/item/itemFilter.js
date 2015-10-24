angular.module('item')
.filter('translateType', function() {
	return function(input) {
		var translation = { task: "Tâche", event: "Événement", note: "Note" };
		return translation[input];
	};
});
