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
        multiConnection.query(script, function(err, results) {
            if (err) throw err;
            if (callback) callback(results);
        });
    },

    getAll: function() {

    },

    storeTask: function(task) {

    }

}
