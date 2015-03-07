var storage = require('../storage/storage');

module.exports.get = function(req, res, next) {
    storage.getTasks(function(err, tasks) {
        if (err) {
            return next(err);
        }
        res.render('month', {
            tasks: tasks
        });
    });
}

module.exports.newTask = function(req, res, next) {
    storage.storeTask(req.body, getStorageCallback(res, next));
}

module.exports.editTask = function(req, res, next) {
    var task = req.body;
    task.id = req.params.id;
    storage.editTask(task, getStorageCallback(res, next));
}

function getStorageCallback(res, next) {
    return function(err, result) {
        if (err) {
            next(err);
        } else {
            res.json(result);
        }
    }
}
