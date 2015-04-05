var assert = require('assert');
var moment = require('moment');
var Month = require('../../../models/monthModel');

moment.locale('fr');

describe('Month', function() {

    describe('#construct', function() {

        it('should accept YYYYMM arg', function() {
            var month = new Month('201501');
            assert(month.isValid());
        })

        it('should accept moment() arg', function() {
            var month = new Month(moment());
            assert(month.isValid());
        })

        it('shouldnt accept invalid string', function() {
            var month = new Month('2015/01');
            assert.equal(false, month.isValid());
        })

    })

    describe('#toString', function() {

        it('should return a YYYYMM string', function() {
            var month = new Month('201501');
            assert.equal('201501', month.toString());
        })
    })

    describe('#toPrettyString', function() {

        it('should return a pretty string', function() {
            var month = new Month('201501');
            assert.equal('janvier 2015', month.toPrettyString());
        })
    })

    describe('#toISOString', function() {

        it('should return an ISO string', function() {
            var month = new Month('201501');
            assert.equal('2015-01', month.toISOString());
        })
    })

    describe('#previous', function() {

        it('should return the previous month', function() {
            var month = new Month('201501');
            assert.equal('201412', month.previous().toString());
        })
    })

    describe('#next', function() {

        it('should return the next month', function() {
            var month = new Month('201501');
            assert.equal('201502', month.next().toString());
        })
    })

})
