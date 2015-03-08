var storage = require('../storage/storage');

module.exports.get = function(req, res, next) {
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
