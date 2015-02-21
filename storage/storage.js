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

module.exports = {

    execute: function(script, callback) {
        multiConnection.query(script, processDbResult(callback));
    },

    getTasks: function(callback) {
        connection.query('select * from tasks order by id', processDbResult(callback));
    },

    storeTask: function(task, callback) {
        connection.query('insert into tasks (name, done) values (?, ?)', [ task.name, task.done ], processDbResult(callback));
    }

}

function processDbResult(callback) {
    return function(err, results) {
        if (err) throw err;
        if (callback) callback(results);
    }
}
