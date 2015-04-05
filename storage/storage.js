var nconf = require('nconf');
var mysql = require('mysql');
var fs = require('fs-extra');
var Item = require('../models/itemModel.js');
var Day = require('../models/dayModel.js');

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

storage.getItem = function(id, callback) {
    connection.query('select * from items where id = ?', [ id ], function(err, result) {
        if (err) return callback(err);
        if (result.length == 0) return callback('item not found');
        callback(null, new Item(result[0]));
    });
}

storage.getItemsForMonth = function(month, callback) {
    connection.query('select * from items where list_type = ? and list_id = ? order by id',
        [ 'month', month.toString() ], function(err, result) {
        if (err) return callback(err);
        callback(null, result.map(function(item) {
            return new Item(item);
        }));
    });
}

storage.storeItem = function(item, callback) {
    if (!item.isValid()) {
        console.log('item invalid: ' + JSON.stringify(item.data));
        return callback('item invalid');
    }
    connection.query('insert into items (type, name, status, list_type, list_id) values (?, ?, ?, ?, ?)',
        [ item.data.type, item.data.name, item.data.status, item.data.list_type, item.data.list_id ], function(err, results) {
        if (err) return callback(err);
        item.data.id = results.insertId;
        callback(null);
    });
}

storage.editItem = function(item, callback) {
    if (!item.isValid()) {
        console.log('item invalid: ' + JSON.stringify(item.data));
        return callback('item invalid');
    }
    connection.query('update items set name = ?, status = ?, list_type = ?, list_id = ? where id = ?',
        [ item.data.name, item.data.status, item.data.list_type, item.data.list_id, item.data.id ], function(err, results) {
        if (err) return callback(err);
        if (results.affectedRows == 0) return callback('item not found');
        callback(null);
    });
}

storage.deleteItem = function(item, callback) {
    connection.query('delete from items where id = ?', [ item.data.id ], function(err, results) {
        if (err) return callback(err);
        if (results.affectedRows == 0) return callback('item not found');
        callback(null);
    });
}

storage.storeDay = function(day, callback) {
    if (!day.isValid()) {
        console.log('day invalid: ' + JSON.stringify(day.data));
        return callback('day invalid');
    }
    connection.query('insert into days (date, text) values (?, ?)',
        [ day.dateString(), day.data.text ], function(err, results) {
        if (err) return callback(err);
        callback(null);
    });
}

storage.getDaysForCalendar = function(calendar, callback) {
    connection.query('select DATE_FORMAT(`date`, \'%Y-%m-%d\') as `date`, text from days where date between ? and ?',
        [ calendar.start().dateString(), calendar.end().dateString() ], function(err, result) {
        if (err) return callback(err);
        callback(null, result.map(function(item) {
            console.log(item);
            return new Day(item);
        }));
    });
}

module.exports = storage;
