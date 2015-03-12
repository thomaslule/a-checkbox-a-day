var storage = require('../storage/storage');
var moment = require('moment');
var Task = require('../models/taskModel.js')

var taskController = {};

taskController.new = function(req, res, next) {
    task = new Task(req.body);
    if (!task.isValid()) return next('task invalid');
    storage.storeTask(task, getStorageCallback(res, next));
}

taskController.edit = function(req, res, next) {
    var task = new Task(req.body);
    if (!task.isValid()) return next('task invalid');
    task.data.id = req.params.id;
    storage.editTask(task, getStorageCallback(res, next));
}

taskController.delete = function(req, res, next) {
    var task = new Task(req.body);
    if (!task.isValid()) return next('task invalid');
    task.data.id = req.params.id;
    storage.deleteTask(task, getStorageCallback(res, next));
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

module.exports = taskController;
