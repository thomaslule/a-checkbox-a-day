var storage = require('../storage/storage');
var Month = require('../models/monthModel');

module.exports.get = function(req, res, next) {
    var month = new Month(req.params.month);
    if (!month.isValid()) {
        next('month invalid');
        return;
    }
    storage.getTasksForMonth(req.params.month, function(err, tasks) {
        if (err) {
            return next(err);
        }
        res.render('month', {
            monthId: req.params.month,
            tasks: tasks
        });
    });
}
