var nconf = require('nconf');
var mysql = require('mysql');

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

module.exports.testConnection = function(callback) {
    connection.connect(callback);
};

module.exports.execute = function(script, callback) {
    multiConnection.query(script, function (err) {
        if (err) {
            // the connection needs to be re-initialized if an error occurs
            multiConnection.destroy();
            multiConnection = mysql.createConnection(connectionConf);
        }
        callback(err);
    });
},

module.exports.getTask = function(id, callback) {
    connection.query('select * from tasks where id = ?', [ id ], processDbResult(callback, true));
},

module.exports.getTasks = function(callback) {
    connection.query('select * from tasks order by id', processDbResult(callback));
},

module.exports.storeTask = function(task, callback) {
    connection.query('insert into tasks (name, status) values (?, ?)', [ task.name, task.status ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            module.exports.getTask(results.insertId, callback);
        }
    });
}

module.exports.editTask = function(task, callback) {
    connection.query('update tasks set name = ?, status = ? where id = ?', [ task.name, task.status, task.id ], function(err, results) {
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
    // TODO this code may be useless (when array is of length 1 it behaves as its first element) it needs tests
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
