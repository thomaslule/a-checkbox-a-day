var moment = require('moment');

var CalendarDay = function(data) {
    this.data = {
        date: moment.isMoment(data.date) ? data.date.startOf('day') : moment(data.date, 'YYYY-MM-DD', true),
        text: data.text
    }
}

CalendarDay.prototype.isValid = function() {
    return this.data.date.isValid();
}

CalendarDay.prototype.datePrettyString = function() {
    return this.data.date.format('dddd D');
}

CalendarDay.prototype.dateString = function() {
    return this.data.date.format('YYYY-MM-DD');
}

CalendarDay.prototype.isSame = function(day) {
    return this.data.date.isSame(day.data.date);
}

CalendarDay.prototype.text = function(day) {
    return this.data.text;
}

module.exports = CalendarDay;
