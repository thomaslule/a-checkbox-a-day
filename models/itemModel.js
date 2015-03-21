var Month = require('./monthModel');

var Item = function(item) {
    this.data = item;
}

Item.prototype.isValid = function() {
    var validTypeStatus = {
        'task': [ 'active', 'done', 'cancelled', 'moved' ],
        'event': [ 'active', 'cancelled', 'moved' ],
        'note': [ 'active', 'cancelled', 'moved' ]
    };
    if (!validTypeStatus.hasOwnProperty(this.data.type)) {
        return false;
    }
    if (validTypeStatus[this.data.type].indexOf(this.data.status) == -1) {
        return false;
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
