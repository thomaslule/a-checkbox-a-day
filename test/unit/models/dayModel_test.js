var assert = require('assert');
var moment = require('moment');
var Day = require('../../../models/dayModel')

moment.locale('fr');

describe('Day', function() {

    describe('#construct', function() {

        it('should accept YYYY-MM-DD arg', function() {
            var day = new Day({ date: '2015-01-05' });
            assert(day.isValid());
        })

        it('should accept moment() arg', function() {
            var day = new Day({ date: moment() });
            assert(day.isValid());
        })

        it('shouldnt accept invalid string', function() {
            var day = new Day({ date: '2015/01/05' });
            assert.equal(false, day.isValid());
        })

    })

    describe('#datePrettyString', function() {

        it('should return a pretty string', function() {
            var day = new Day({ date: '2015-01-05' });
            assert.equal('lundi 5', day.datePrettyString());
        })

    })

    describe('#dateString', function() {

        it('should return an ISO string', function() {
            var day = new Day({ date: '2015-01-05' });
            assert.equal('2015-01-05', day.dateString());
        })
    })

    describe('#isSame', function() {

        it('should return true only if the day is the same', function() {
            var day = new Day({ date: '2015-01-05' });
            var sameDay = new Day({ date: '2015-01-05' });
            var differentDay = new Day({ date: '2015-01-06' });
            assert(day.isSame(sameDay));
            assert.equal(false, day.isSame(differentDay));
        })
    })

    describe('#text', function() {

        it('should return the text', function() {
            var day = new Day({ date: '2015-01-05', text: 'something' });
            assert.equal('something', day.text());
        })
    })

})
