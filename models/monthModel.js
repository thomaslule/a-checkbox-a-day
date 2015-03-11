var moment = require('moment');

var Month = function(month) {
    this.month = moment.isMoment(month) ? month.startOf('month') : moment(month, 'YYYYMM', true);
}

Month.prototype.isValid = function() {
    return this.month.isValid();
}

Month.prototype.toString = function() {
    return this.month.format('YYYYMM');
}

Month.prototype.toPrettyString = function() {
    return this.month.format('MMMM YYYY');
}

Month.prototype.toISOString = function() {
    return this.month.format('YYYY-MM');
}

Month.prototype.previous = function() {
    return new Month(moment(this.month).subtract(1, 'months'));
}

Month.prototype.next = function() {
    return new Month(moment(this.month).add(1, 'months'));
}

module.exports = Month;
