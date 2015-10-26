angular.module("error")
.factory("error", function() {
	var _showErrorCallback;

	return {
		_setShowErrorCallback: function(callback) {
			_showErrorCallback = callback;
		},

		showError: function(text) {
			if (_showErrorCallback == undefined) {
				console.error("Add an 'error' element/attribute in your page")
				return;
			}
			_showErrorCallback(text);
		}
	};
});
