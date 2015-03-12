var storage = require('../storage/storage');
var Month = require('../models/monthModel');

var monthController = {};

monthController.get = function(req, res, next) {
    var month = new Month(req.params.month);
    if (!month.isValid()) return next('month invalid');
    storage.getTasksForMonth(month, function(err, tasks) {
        if (err) {
            return next(err);
        }
        res.render('month', {
            month: month,
            tasks: tasks
        });
    });
}

module.exports = monthController;
