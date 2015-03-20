var assert = require('assert');
var Task = require('../../../models/taskModel');

describe('Task', function() {

    var simpleTask;

    beforeEach(function() {
        simpleTask = new Task({
            name: 'some task',
            status: 'todo',
            list_type: 'month',
            list_id: '201501'
        });
    })

    describe('#isValid', function() {

        it('should only accept certain status', function() {
            assert(simpleTask.isValid());
            simpleTask.data.status = 'done';
            assert(simpleTask.isValid());
            simpleTask.data.status = 'cancelled';
            assert(simpleTask.isValid());
            simpleTask.data.status = 'moved';
            assert(simpleTask.isValid());
            simpleTask.data.status = 'other';
            assert.equal(false, simpleTask.isValid());
        })

        it('should check month validity', function() {
            simpleTask.data.list_id = 'nope';
            assert.equal(false, simpleTask.isValid());
        })

    })

    describe('#move', function() {

        it('should set the status to moved', function() {
            assert.equal('todo', simpleTask.data.status);
            simpleTask.move('month', '201502');
            assert.equal('moved', simpleTask.data.status);
        })

        it('should return a new task', function() {
            var newTask = simpleTask.move('month', '201502');
            assert.equal('todo', newTask.data.status);
            assert.equal('some task', newTask.data.name);
            assert.equal('month', newTask.data.list_type);
            assert.equal('201502', newTask.data.list_id);
        })

    })

    describe('#getMoveList', function() {

        it('should return a list of months', function() {
            var list = simpleTask.getMoveList();
            assert.equal('201412', list[0].toString());
            assert.equal('201502', list[1].toString());
            assert.equal('201503', list[2].toString());
        })

    })

})
