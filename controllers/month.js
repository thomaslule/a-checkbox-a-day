var storage = require('../storage/storage');
var Task = require('../models/task');

module.exports = function(app) {
    return {
        get: function(req, res) {
            res.render('month');
        },

        post: function(req, res) {
            var task = new Task(req.body.new_task);
            storage.storeTask(task, function() {
                res.json('{}');
            });
        }
    }
}
