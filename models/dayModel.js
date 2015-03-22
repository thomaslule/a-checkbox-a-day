var moment = require('moment');

var Day = function(data) {
    this.data = moment.isMoment(data) ? data.startOf('day') : moment(data, 'YYYYMMDD', true);
    this.text = ''; // TODO
}

Day.prototype.isValid = function() {
    return this.data.isValid();
}

Day.prototype.toPrettyString = function() {
    return this.data.format('dddd D');
}

Day.prototype.toISOString = function() {
    return this.data.format('YYYY-MM-DD');
}

module.exports = Day;
