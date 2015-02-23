var storage = require('../storage/storage');

// controls will be done here
module.exports = {

    getAll: function(callback) {
        storage.getTasks(callback);
    },

    store: function(task, callback) {
        storage.storeTask(task, callback);
    },

    edit: function(task, callback) {
        storage.editTask(task, callback);
    },

    delete: function(task, callback) {
        storage.deleteTask(task, callback);
    }

}
