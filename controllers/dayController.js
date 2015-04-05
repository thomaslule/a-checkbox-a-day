var storage = require('../storage/storage');
var Day = require('../models/dayModel');

var dayController = {};

dayController.edit = function(req, res, next) {
    var day = new Day({ date: req.params.day, text: req.body.text });
    storage.storeDay(day, function(err) {
        if (err) return next(err);
        res.json(day.data);
    });
}

module.exports = dayController;
