var extend = require('extend');
var itemRepository = require('../repository/itemRepository');

var itemService = {};

itemService.moveItem = function(item, month, callback) {
	var newItem = extend({}, item);
	newItem.list_id = month;
	item.status = "moved";
	itemRepository.storeItem(newItem, function(err) {
		if (err) callback(err);
		itemRepository.editItem(item, callback);
	});
}

module.exports = itemService;
