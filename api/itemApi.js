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

module.exports = itemApi;