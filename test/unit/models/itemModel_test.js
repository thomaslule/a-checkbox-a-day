var assert = require('assert');
var Item = require('../../../models/itemModel');

describe('Item', function() {

    var simpleItem;

    beforeEach(function() {
        simpleItem = new Item({
            id: '42',
            type: 'task',
            name: 'some item',
            status: 'todo',
            list_type: 'month',
            list_id: '201501'
        });
    })

    describe('#isValid', function() {

        it('should only accept certain status', function() {
            assert(simpleItem.isValid());
            simpleItem.data.status = 'done';
            assert(simpleItem.isValid());
            simpleItem.data.status = 'cancelled';
            assert(simpleItem.isValid());
            simpleItem.data.status = 'moved';
            assert(simpleItem.isValid());
            simpleItem.data.status = 'other';
            assert.equal(false, simpleItem.isValid());
        })

        it('should check month validity', function() {
            simpleItem.data.list_id = 'nope';
            assert.equal(false, simpleItem.isValid());
        })

    })

    describe('#move', function() {

        it('should set the status to moved', function() {
            assert.equal('todo', simpleItem.data.status);
            simpleItem.move('month', '201502');
            assert.equal('moved', simpleItem.data.status);
        })

        it('should return a new item', function() {
            var newItem = simpleItem.move('month', '201502');
            assert.equal('task', newItem.data.type);
            assert.equal('todo', newItem.data.status);
            assert.equal('some item', newItem.data.name);
            assert.equal('month', newItem.data.list_type);
            assert.equal('201502', newItem.data.list_id);
        })

    })

    describe('#getMoveList', function() {

        it('should return a list of months', function() {
            var list = simpleItem.getMoveList();
            assert.equal('201412', list[0].toString());
            assert.equal('201502', list[1].toString());
            assert.equal('201503', list[2].toString());
        })

    })

})
