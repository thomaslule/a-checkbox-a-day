var async = require('async');
var storage = require('../storage/storage');
var Month = require('../models/monthModel');
var Item = require('../models/itemModel');
var Calendar = require('../models/calendarModel');

var monthController = {};

monthController.get = function(req, res, next) {
    var self = this;
    var month = new Month(req.params.month);
    if (!month.isValid()) return next('month invalid');
    async.parallel([
        function(callback) {
            storage.getItemsForMonth(month, function(err, items) {
                callback(err, items);
            });
        },
        function(callback) {
            var calendar = new Calendar(month.toString());
            storage.getCalendarDays(calendar, function(err, days) {
                if (err) callback(err);
                calendar.setDays(days);
                callback(null, calendar);
            })
        }
    ], function(err, results) {
        if (err) return next(err);
        res.render('month', {
            month: month,
            items: results[0],
            calendar: results[1]
        });
    });

}

monthController.migrate = function(req, res, next) {
    var month = new Month(req.params.month);
    if (!month.isValid()) return next('month invalid');
    storage.getActiveTasksForMonth(month, function(err, tasks) {
        if (err) return next(err);
        var tasksToSave = [];
        var tasksToUpdate = [];
        tasks.forEach(function(task) {
            newTask = task.move('month', month.next().toString());
            tasksToSave.push(newTask);
            tasksToUpdate.push(task);
        });
        async.parallel([
            function(callback) {
                async.map(tasksToSave, storage.storeItem, function(results) {
                    callback(null, results);
                });
            },
            function(callback) {
                async.map(tasksToUpdate, storage.editItem, function(results) {
                    callback(null, results);
                });
            }
        ], function(err, results) {
            if (err) return next(err);
            res.sendStatus(200);
        })

    });
}

module.exports = monthController;
