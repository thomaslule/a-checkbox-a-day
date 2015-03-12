var nconf = require('nconf');
var mysql = require('mysql');
var fs = require('fs-extra');

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

storage.clearDb = function(callback) {
    storage.execute(fs.readFileSync('./storage/drop_db.sql', 'utf8'), function(err) {
        if (err) {
            callback(err);
            return;
        }
        storage.execute(fs.readFileSync('./storage/init_db.sql', 'utf8'), function(err) {
            if (err) {
                callback(err);
                return;
            }
            callback();
        })
    })
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
    connection.query('select * from tasks where id = ?', [ id ], processDbResult(callback, true));
}

storage.getTasksForMonth = function(month, callback) {
    connection.query('select * from tasks where list_type = ? and list_id = ? order by id',
        [ 'month', month.toString() ], processDbResult(callback));
}

storage.storeTask = function(task, callback) {
    connection.query('insert into tasks (name, status, list_type, list_id) values (?, ?, ?, ?)',
        [ task.data.name, task.data.status, task.data.list_type, task.data.list_id ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            storage.getTask(results.insertId, callback);
        }
    });
}

storage.editTask = function(task, callback) {
    connection.query('update tasks set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
        [ task.data.name, task.data.status, task.data.list_type, task.data.list_id, task.data.id ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            storage.getTask(task.data.id, callback);
        }
    });
}

storage.deleteTask = function(task, callback) {
    connection.query('delete from tasks where id = ?', [ task.data.id ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            callback(null, {});
        }
    });
}

function processDbResult(callback, unique) {
    unique = (typeof unique === 'undefined' ? false : unique);
    return function(err, results) {
        if (callback) {
            if (err) {
                callback(err);
            } else {
                unique ? callback(null, results[0]) : callback(null, results);
            }
        }
    }
}

module.exports = storage;
