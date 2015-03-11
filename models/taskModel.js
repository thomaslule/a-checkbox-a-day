var Month = require('./monthModel');

module.exports = function(task) {

    task.isValid = function() {
        if (this.list_type == 'month') {
            var month = new Month(this.list_id);
            return month.isValid();
        } else {
            return true;
        }
    }

    return task;
}
