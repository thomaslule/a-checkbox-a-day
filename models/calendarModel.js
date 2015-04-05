var moment = require('moment');
var Day = require('./dayModel');

var Calendar = function(start) {
    var start = moment.isMoment(start) ? start.startOf('month') : moment(start, 'YYYYMM', true);
    this.valid = start.isValid();
    if (this.valid) {
        var dayRunner = moment(start);
        this.days = [];
        while (dayRunner.month() == start.month()) {
            this.days.push(new Day({ date: moment(dayRunner), text: '' }));
            dayRunner.add(1, 'days');
        }
    }
}

Calendar.prototype.isValid = function() {
    return this.valid;
}

Calendar.prototype.start = function() {
    return this.days[0];
}

Calendar.prototype.end = function() {
    return this.days[this.days.length - 1];
}

Calendar.prototype.setDays = function(days) {
    var self = this;
    days.forEach(function(dayToSet) {
        self.days.forEach(function(dayToReplace, index) {
            if (dayToSet.isSame(dayToReplace)) {
                self.days[index] = dayToSet;
            }
        })
    })
}

Calendar.prototype.getDays = function() {
    return this.days;
}

module.exports = Calendar;
