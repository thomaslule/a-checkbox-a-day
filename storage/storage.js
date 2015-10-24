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

storage.getItemsForMonth = function(month, callback) {
    connection.query('select * from items where list_type = ? and list_id = ? order by id',
        [ 'month', month.format('YYYYMM') ], function(err, result) {
        if (err) return callback(err);
        callback(null, result);
    });
}

storage.storeItem = function(item, callback) {
    connection.query('insert into items (type, name, status, list_type, list_id) values (?, ?, ?, ?, ?)',
        [ item.type, item.name, item.status, item.list_type, item.list_id ], function(err, results) {
        if (err) return callback(err);
        item.id = results.insertId;
        callback(null);
    });
}

storage.editItem = function(item, callback) {
    connection.query('update items set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
        [ item.name, item.status, item.list_type, item.list_id, item.id ], function(err, results) {
        if (err) return callback(err);
        if (results.affectedRows == 0) return callback('item not found');
        callback(null);
    });
}

storage.deleteItem = function(id, callback) {
    connection.query('delete from items where id = ?', [ id ], function(err, results) {
        if (err) return callback(err);
        if (results.affectedRows == 0) return callback('item not found');
        callback(null);
    });
}

module.exports = storage;
