var storage = require('../storage/storage');
var Month = require('../models/monthModel');
var Item = require('../models/itemModel');

var monthController = {};

monthController.get = function(req, res, next) {
    var month = new Month(req.params.month);
    if (!month.isValid()) return next('month invalid');
    storage.getItemsForMonth(month, function(err, items) {
        if (err) return next(err);
        res.render('month', {
            month: month,
            items: items
        });
    });
}

module.exports = monthController;
