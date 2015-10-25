var storage = require('../storage/storage');

var itemRepository = {};

itemRepository.getItemsForMonth = function(month, callback) {
	storage.query('select * from items where list_type = ? and list_id = ? order by id',
		[ 'month', month.format('YYYYMM') ],
		callback);
}

itemRepository.storeItem = function(item, callback) {
	storage.query('insert into items (type, name, status, list_type, list_id) values (?, ?, ?, ?, ?)',
		[ item.type, item.name, item.status, item.list_type, item.list_id ],
		function(err, results) {
	        if (err) return callback(err);
	        item.id = results.insertId;
	        callback(null);
	    });
}

itemRepository.editItem = function(item, callback) {
	storage.query('update items set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
		[ item.name, item.status, item.list_type, item.list_id, item.id ],
			function(err, results) {
	        if (err) return callback(err);
	        if (results.affectedRows == 0) return callback('item not found');
	        callback(null);
	    });
}

itemRepository.deleteItem = function(id, callback) {
	storage.query('delete from items where id = ?',
		[ id ],
		function(err, results) {
	        if (err) return callback(err);
	        if (results.affectedRows == 0) return callback('item not found');
	        callback(null);
	    });
}

module.exports = itemRepository;