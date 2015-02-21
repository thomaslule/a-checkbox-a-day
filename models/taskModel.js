var storage = require('../storage/storage');

// controls will be done here
module.exports = {

    getAll: function(callback) {
        storage.getTasks(callback);
    },

    store: function(task, callback) {
        storage.storeTask(task, callback);
    }

}
