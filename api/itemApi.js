var itemRepository = require('../repository/itemRepository');
var itemService = require('../service/itemService');
var moment = require('moment');

var itemApi = {};

itemApi.getForMonth = function(req, res, next) {
    var month = moment(req.params.month, 'YYYYMM', true);
    if (!month.isValid()) return next({ statusCode: 400, text: "invalid_month" });
    itemRepository.getItemsForMonth(month, function(err, result) {
    	if (err) return next(err);
    	res.json(result);
    });
};

itemApi.post = function(req, res, next) {
	var item = req.body;
	item.status = "active";
	itemRepository.storeItem(item, function(err) {
		if (err) return next(err);
		res.json(item);
	});
};

itemApi.put = function(req, res, next) {
	var item = req.body;
	if (item.id != req.params.id) return next("invalid id");
	itemRepository.editItem(item, function(err) {
		if (err) return next(err);
		res.json(item);
	});
};

itemApi.move = function(req, res, next) {
	var item = req.body;
	if (item.id != req.params.id) return next("invalid id");
	itemService.moveItem(item, req.params.month, function(err) {
		if (err) return next(err);
		res.json(item);
	});
};

itemApi.delete = function(req, res, next) {
	itemRepository.deleteItem(req.params.id, function(err) {
		if (err) return next(err);
		res.sendStatus(200);
	});
};

module.exports = itemApi;
