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
            storage.getDaysForCalendar(calendar, function(err, days) {
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

module.exports = monthController;
