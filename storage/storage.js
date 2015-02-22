var nconf = require('nconf');
var mysql = require('mysql');

nconf.argv().env().file('local.json');

var connectionConf = {
    host: nconf.get('database:host'),
    user: nconf.get('database:user'),
    password: nconf.get('database:password'),
    database: nconf.get('database:database')
}
var connection = mysql.createConnection(connectionConf);
connectionConf.multipleStatements = true;
var multiConnection = mysql.createConnection(connectionConf);

module.exports.execute = function(script, callback) {
    multiConnection.query(script, processDbResult(callback));
},

module.exports.getTask = function(id, callback) {
    connection.query('select * from tasks where id = ?', [ id ], processDbResult(callback, true));
},

module.exports.getTasks = function(callback) {
    connection.query('select * from tasks order by id', processDbResult(callback));
},

module.exports.storeTask = function(task, callback) {
    connection.query('insert into tasks (name, done) values (?, ?)', [ task.name, task.done ], function(err, results) {
        if (err) {
            callback(err);
        } else {
            module.exports.getTask(results.insertId, callback);
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
