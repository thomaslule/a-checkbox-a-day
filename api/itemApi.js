var moment = require('moment');
var storage = require('../storage/storage');

var itemApi = {};

itemApi.getForMonth = function(req, res, next) {
    var month = moment(req.params.month, 'YYYYMM', true);
    if (!month.isValid()) return next('month invalid');
    storage.getItemsForMonth(month, function(err, result) {
    	if (err) return next(err);
    	res.json(result);
    });
};

itemApi.post = function(req, res, next) {
	var item = req.body;
	item.status = "active";
	storage.storeItem(item, function(err) {
		if (err) return next(err);
		res.json(item);
	});
};

itemApi.put = function(req, res, next) {
	var item = req.body;
	if (item.id != req.params.id) return next("invalid id");
	storage.editItem(item, function(err) {
		if (err) return next(err);
		res.json(item);
	});
};

itemApi.delete = function(req, res, next) {
	storage.deleteItem(req.params.id, function(err) {
		if (err) return next(err);
		res.sendStatus(200);
	});
};

module.exports = itemApi;
