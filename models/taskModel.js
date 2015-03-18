var Month = require('./monthModel');

var Task = function(task) {
    this.data = task;
}

Task.prototype.isValid = function() {
    if (['todo', 'done', 'cancelled', 'moved'].indexOf(this.data.status) == -1) {
        return false
    }
    if (this.data.list_type == 'month') {
        var month = new Month(this.data.list_id);
        return month.isValid();
    } else {
        return true;
    }
}

Task.prototype.move = function(list_type, list_id) {
    this.data.status = 'moved';
    return new Task({
        name: this.data.name,
        status: 'todo',
        list_type: list_type,
        list_id: list_id
    });
}

Task.prototype.getMoveList = function() {
    var month = new Month(this.data.list_id);
    return [
        month.previous(),
        month.next(),
        month.next().next()
    ];
}

module.exports = Task;
