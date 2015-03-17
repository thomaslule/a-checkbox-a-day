var storage = require('../storage/storage');
var Month = require('../models/monthModel');
var Task = require('../models/taskModel');

var monthController = {};

monthController.get = function(req, res, next) {
    var month = new Month(req.params.month);
    if (!month.isValid()) return next('month invalid');
    storage.getTasksForMonth(month, function(err, tasks) {
        if (err) return next(err);
        res.render('month', {
            month: month,
            tasks: tasks.map(function(task) {
                return new Task(task);
            })
        });
    });
}

module.exports = monthController;
