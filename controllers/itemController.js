var storage = require('../storage/storage');
var moment = require('moment');
var Item = require('../models/itemModel.js');

var itemController = {};

itemController.new = function(req, res, next) {
    item = new Item(req.body);
    storage.storeItem(item, function(err) {
        if (err) return next(err);
        res.render('item', {
            item: item
        });
    });
}

itemController.edit = function(req, res, next) {
    var item = new Item(req.body);
    item.data.id = req.params.id;
    storage.editItem(item, function(err) {
        if (err) return next(err);
        res.json(item.data);
    });
}

itemController.delete = function(req, res, next) {
    var item = new Item(req.body);
    item.data.id = req.params.id;
    storage.deleteItem(item, function(err) {
        if (err) return next(err);
        res.sendStatus(200);
    });
}

itemController.move = function(req, res, next) {
    storage.getItem(req.params.id, function(err, item) {
        if (err) return next(err);
        newItem = item.move(req.body.list_type, req.body.list_id);
        storage.storeItem(newItem, function(err) {
            if (err) return next(err);
            storage.editItem(item, function(err) {
                if (err) return next(err);
                res.sendStatus(200);
            });
        });
    });
}

module.exports = itemController;
