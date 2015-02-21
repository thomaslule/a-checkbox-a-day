var taskModel = require('../models/taskModel');

module.exports = {

    get: function(req, res) {
        res.render('month');
    },

    getAll: function(req, res) {
        taskModel.getAll(function(result) {
            res.json(result);
        });
    },

    postNew: function(req, res) {
        taskModel.store(req.body, function(result) {
            res.json(result);
        });
    }

}
