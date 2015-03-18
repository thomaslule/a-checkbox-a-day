var nconf = require('nconf');
var mysql = require('mysql');
var fs = require('fs-extra');
var Task = require('../models/taskModel.js');

nconf.argv().env().file('local.json');

var connectionConf = {
    host: nconf.get('database:host'),
    user: nconf.get('database:user'),
    password: nconf.get('database:password'),
    database: nconf.get('database:database')
};

var connection = mysql.createConnection(connectionConf);
connectionConf.multipleStatements = true;
var multiConnection = mysql.createConnection(connectionConf);

var storage = {}

storage.initDb = function(callback) {
    fs.readFile('./storage/init_db.sql', 'utf8', function(err, script) {
        if (err) return callback(err);
        storage.execute(script, function(err) {
            if (err) return callback(err);
            callback();
        });
    });
}

storage.clearDb = function(callback) {
    fs.readFile('./storage/drop_db.sql', 'utf8', function(err, script) {
        if (err) return callback(err);
        storage.execute(script, function(err) {
            if (err) return callback(err);
            storage.initDb(callback);
        });
    });
}

storage.testConnection = function(callback) {
    connection.connect(callback);
}

storage.execute = function(script, callback) {
    multiConnection.query(script, function (err) {
        if (err) {
            // the connection needs to be re-initialized if an error occurs
            multiConnection.destroy();
            multiConnection = mysql.createConnection(connectionConf);
        }
        callback(err);
    });
}

storage.getTask = function(id, callback) {
    connection.query('select * from tasks where id = ?', [ id ], function(err, result) {
        if (err) return callback(err);
        callback(null, new Task(result[0]));
    });
}

storage.getTasksForMonth = function(month, callback) {
    connection.query('select * from tasks where list_type = ? and list_id = ? order by id',
        [ 'month', month.toString() ], function(err, result) {
        if (err) return callback(err);
        callback(null, result.map(function(task) {
            return new Task(task);
        }));
    });
}

storage.storeTask = function(task, callback) {
    if (!task.isValid()) return callback('task invalid');
    connection.query('insert into tasks (name, status, list_type, list_id) values (?, ?, ?, ?)',
        [ task.data.name, task.data.status, task.data.list_type, task.data.list_id ], function(err, results) {
        if (err) return callback(err);
        task.data.id = results.insertId;
        callback(null);
    });
}

storage.editTask = function(task, callback) {
    if (!task.isValid()) return callback('task invalid');
    connection.query('update tasks set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
        [ task.data.name, task.data.status, task.data.list_type, task.data.list_id, task.data.id ], function(err, results) {
        if (err) return callback(err);
        callback(null);
    });
}

storage.deleteTask = function(task, callback) {
    connection.query('delete from tasks where id = ?', [ task.data.id ], function(err, results) {
        if (err) return callback(err);
        callback(null);
    });
}

module.exports = storage;
