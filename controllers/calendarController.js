var storage = require('../storage/storage');
var CalendarDay = require('../models/calendarDayModel');

var calendarController = {};

calendarController.editDay = function(req, res, next) {
    var day = new CalendarDay({ date: req.params.day, text: req.body.text });
    storage.storeCalendarDay(day, function(err) {
        if (err) return next(err);
        res.json(day.data);
    });
}

module.exports = calendarController;
