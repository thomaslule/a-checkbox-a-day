var Month = require('./monthModel');

var Task = function(task) {
    this.data = task;
}

Task.prototype.isValid = function() {
    if (this.data.list_type == 'month') {
        var month = new Month(this.data.list_id);
        return month.isValid();
    } else {
        return true;
    }
}

module.exports = Task;
