var assert = require('assert');
var moment = require('moment');
var Day = require('../../../models/dayModel')

moment.locale('fr');

describe('Day', function() {

    describe('#construct', function() {

        it('should accept YYYYMMDD arg', function() {
            var day = new Day('20150105');
            assert(day.isValid());
        })

        it('should accept moment() arg', function() {
            var day = new Day(moment());
            assert(day.isValid());
        })

        it('shouldnt accept invalid string', function() {
            var day = new Day('2015/01/05');
            assert.equal(false, day.isValid());
        })

    })

    describe('#toPrettyString', function() {

        it('should return a pretty string', function() {
            var day = new Day('20150105');
            assert.equal('lundi 5', day.toPrettyString());
        })

    })

    describe('#toISOString', function() {

        it('should return an ISO string', function() {
            var day = new Day('20150105');
            assert.equal('2015-01-05', day.toISOString());
        })
    })

})
