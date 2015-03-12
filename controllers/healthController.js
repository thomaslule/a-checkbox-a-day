/**
 * @author Thomas Bracher "thomas.bracher@cpe.fr"
 */
var storage = require('../storage/storage');

var healthController = {}

healthController.get = function(req, res) {
    storage.testConnection(function(err) {
        if (err) {
            console.log("error connecting to mysql: %s", err.stack);
            res.sendStatus(500);
        } else {
            res.sendStatus(200);
        }
    });
};

module.exports = healthController;
