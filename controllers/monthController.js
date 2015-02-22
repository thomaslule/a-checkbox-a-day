var taskModel = require('../models/taskModel');

module.exports.get = function(req, res) {
    res.render('month');
},

module.exports.getAll = function(req, res, next) {
    taskModel.getAll(getStorageCallback(res, next));
},

module.exports.postNew = function(req, res, next) {
    taskModel.store(req.body, getStorageCallback(res, next));
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
