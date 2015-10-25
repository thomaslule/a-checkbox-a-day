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

storage.query = function(query, params, callback) {
    connection.query(query, params, callback);
}

module.exports = storage;
