var moment = require('moment');

module.exports = function(month) {

    month = new String(month);

    month.isValid = function() {
        return moment(this, 'YYYYMM', true).isValid();
    }

    return month;
}
