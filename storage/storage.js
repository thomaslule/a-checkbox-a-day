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

module.exports.clearDb = function(callback) {
    module.exports.execute(fs.readFileSync('./storage/drop_db.sql', 'utf8'), function(err) {
        if (err) {
            callback(err);
            return;
        }
        module.exports.execute(fs.readFileSync('./storage/init_db.sql', 'utf8'), function(err) {
            if (err) {
                callback(err);
                return;
            }
            callback();
        })
    })
}

module.exports.testConnection = function(callback) {
    connection.connect(callback);
}

module.exports.execute = function(script, callback) {
    multiConnection.query(script, function (err) {
        if (err) {
            // the connection needs to be re-initialized if an error occurs
            multiConnection.destroy();
            multiConnection = mysql.createConnection(connectionConf);
        }
        callback(err);
    });
}

module.exports.getTask = function(id, callback) {
    connection.query('select * from tasks where id = ?', [ id ], processDbResult(callback, true));
}

module.exports.getTasksForMonth = function(month, callback) {
    connection.query('select * from tasks where list_type = ? and list_id = ? and status != "deleted" order by id',
        [ 'month', month.toString() ], processDbResult(callback));
}

module.exports.storeTask = function(task, callback) {
    connection.query('insert into tasks (name, status, list_type, list_id) values (?, ?, ?, ?)',
        [ task.name, task.status, task.list_type, task.list_id ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            module.exports.getTask(results.insertId, callback);
        }
    });
}

module.exports.editTask = function(task, callback) {
    connection.query('update tasks set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
        [ task.name, task.status, task.list_type, task.list_id, task.id ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            module.exports.getTask(task.id, callback);
        }
    });
}

module.exports.deleteTask = function(task, callback) {
    connection.query('delete from tasks where id = ?', [ task.id ], function(err, results) {
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
