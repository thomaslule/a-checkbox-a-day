var assert = require('assert');
var moment = require('moment');
var Calendar = require('../../../models/calendarModel');
var Day = require('../../../models/dayModel');

moment.locale('fr');

describe('Calendar', function() {

    describe('#construct', function() {

        it('should accept YYYYMM arg', function() {
            var calendar = new Calendar('201501');
            assert(calendar.isValid());
        })

        it('should accept moment() arg', function() {
            var calendar = new Calendar(moment());
            assert(calendar.isValid());
        })

        it('shouldnt accept invalid string', function() {
            var calendar = new Calendar('2015/01');
            assert.equal(false, calendar.isValid());
        })

    })

    describe('#getDays', function() {

        it('should return a list of days', function() {
            var calendar = new Calendar('201501');
            assert.equal(31, calendar.getDays().length);
            var calendar2 = new Calendar('201502');
            assert.equal(28, calendar2.getDays().length);
        })
    })

    describe('#start', function() {

        it('should return the first day of the month', function() {
            var calendar = new Calendar('201501');
            assert.equal('2015-01-01', calendar.start().dateString());
        })
    })

    describe('#end', function() {

        it('should return the last day of the month', function() {
            var calendar = new Calendar('201501');
            assert.equal('2015-01-31', calendar.end().dateString());
        })
    })

    describe('#setDays', function() {

        it('should set the given days', function() {
            var calendar = new Calendar('201501');
            calendar.setDays([
                new Day({ date: '2015-01-08', text: 'something' }),
                new Day({ date: '2015-01-15', text: 'other' })
            ]);
            assert.equal('something', calendar.getDays()[7].text());
            assert.equal('other', calendar.getDays()[14].text());
        })
    })

})
