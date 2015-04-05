var moment = require('moment');

var Day = function(data) {
    this.data = {
        date: moment.isMoment(data.date) ? data.date.startOf('day') : moment(data.date, 'YYYY-MM-DD', true),
        text: data.text
    }
}

Day.prototype.isValid = function() {
    return this.data.date.isValid();
}

Day.prototype.datePrettyString = function() {
    return this.data.date.format('dddd D');
}

Day.prototype.dateString = function() {
    return this.data.date.format('YYYY-MM-DD');
}

Day.prototype.isSame = function(day) {
    return this.data.date.isSame(day.data.date);
}

Day.prototype.text = function(day) {
    return this.data.text;
}

module.exports = Day;
