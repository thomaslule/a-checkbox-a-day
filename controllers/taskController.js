var storage = require('../storage/storage');
var moment = require('moment');
var Task = require('../models/taskModel.js');

var taskController = {};

taskController.new = function(req, res, next) {
    task = new Task(req.body);
    storage.storeTask(task, function(err) {
        if (err) return next(err);
        res.render('task', {
            task: task
        });
    });
}

taskController.edit = function(req, res, next) {
    var task = new Task(req.body);
    task.data.id = req.params.id;
    storage.editTask(task, function(err) {
        if (err) return next(err);
        res.json(task.data);
    });
}

taskController.delete = function(req, res, next) {
    var task = new Task(req.body);
    task.data.id = req.params.id;
    storage.deleteTask(task, function(err) {
        if (err) return next(err);
        res.sendStatus(200);
    });
}

taskController.move = function(req, res, next) {
    storage.getTask(req.params.id, function(err, result) {
        if (err) return next(err);
        task = new Task(result);
        newTask = task.move(req.body.list_id);
        storage.storeTask(newTask, function(err) {
            if (err) return next(err);
            storage.editTask(task, function(err) {
                if (err) return next(err);
                res.sendStatus(200);
            });
        });
    });
}

module.exports = taskController;
