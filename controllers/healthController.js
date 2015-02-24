var connection = require('../storage/storage').connection;

module.exports.get = function(req, res) {
    connection.connect(function(err) {
        if (err) {
            console.log("error connecting to mysql: %s", err.stack);
            res.sendStatus(500);
        } else {
            res.sendStatus(200);
        }
    });
};
