var Month = require('./monthModel');

var Item = function(item) {
    this.data = item;
}

Item.prototype.isValid = function() {
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

Item.prototype.move = function(list_type, list_id) {
    var oldStatus = this.data.status;
    this.data.status = 'moved';
    return new Item({
        type: this.data.type,
        name: this.data.name,
        status: oldStatus,
        list_type: list_type,
        list_id: list_id
    });
}

Item.prototype.getMoveList = function() {
    var month = new Month(this.data.list_id);
    return [
        month.previous(),
        month.next(),
        month.next().next()
    ];
}

module.exports = Item;
