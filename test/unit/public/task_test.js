var assert = require("assert");
var jsdom = require("jsdom");
var fs = require("fs-extra");
var jade = require('jade');
var Task = require('../../../models/taskModel.js')

describe('task', function() {

    var window;
    var defaultTask;

    function $(arg) {
        return window.$(arg);
    }

    function render(task) {
        return $(jade.renderFile('views/task.jade', { task: task }));
    }

    beforeEach(function(done) {
        defaultTask = new Task({ id: 1, name: 'my task', status: 'todo' });
        jsdom.env({
            html: '<div id="root" />',
            scripts: [ "https://code.jquery.com/jquery-2.1.3.min.js" ],
            src: [
                fs.readFileSync("public/js/task.js").toString(),
                fs.readFileSync('node_modules/jade/runtime.js').toString(),
                jade.compileFileClient('views/task.jade', {name: 'jadeTaskTemplate'})
            ],
            done: function (errors, windowBuilt) {
                window = windowBuilt;
                done(errors);
            }
        })
    })

    describe('#construct', function() {

        it('should build a task form', function() {
            $('#root').task(render(defaultTask));
            assert($('#root').hasClass('task'));
            assert.equal(1, $('#root').task('getId'));
            assert.equal('todo', $('input[name="status"]').val());
            assert.equal('todo', $('#root').attr('data-status'));
            assert.equal('my task', $('.task-name').text());
            assert.equal(false, $(':checkbox').is(':checked'));
        })

        it('should build a checked task form', function() {
            defaultTask.data.status = 'done';
            $('#root').task(render(defaultTask));
            assert($(':checkbox').is(':checked'));
        })

    })

    describe('#getForm', function() {

        it('should get the form', function() {
            $('#root').task(render(defaultTask));
            assert($('#root').task('getForm').is('form'));
        })

    })

    describe('#getId', function() {

        it('should get the id', function() {
            $('#root').task(render(defaultTask));
            assert.equal(1, $('#root').task('getId'));
        })

    })
    
    describe('#onChangeCheckbox', function() {

        it('should change status when checkbox state changes', function() {
            $('#root').task(render(defaultTask));
            assert.equal('todo', $('#root input[name="status"]').val());
            $(':checkbox').prop('checked', true);
            $(':checkbox').change();
            assert.equal('done', $('#root input[name="status"]').val());
        })

    })

    describe('#edit', function() {

        it('should add a .editing class', function() {
            $('#root').task(render(defaultTask));
            assert.equal(false, $('#root').hasClass('editing'));
            $('#root').task('edit');
            assert($('#root').hasClass('editing'));
        })

        it('should remove the .editing class to the other tasks', function() {
            $('#root').html('<div id="task1" /><div id="task2" />')
            $('#task1, #task2').task(render(defaultTask));
            $('#task1').task('edit');
            assert($('#task1').hasClass('editing'));
            $('#task2').task('edit');
            assert.equal(false, $('#task1').hasClass('editing'));
        })

    })

    describe('#cancelEdit', function() {

        it('should reset the "name" input', function() {
            $('#root').task(render(defaultTask));
            $('#root').task('edit');
            $('input[name="name"]').val('modified');
            assert.equal('modified', $('input[name="name"]').val());
            $('#root').task('cancelEdit');
            assert.equal('my task', $('input[name="name"]').val());
        })

        it('should remove the .editing class', function() {
            $('#root').task(render(defaultTask));
            $('#root').task('edit');
            assert($('#root').hasClass('editing'));
            $('#root').task('cancelEdit');
            assert.equal(false, $('#root').hasClass('editing'));
        })

    })

    describe('#onSubmitEdit', function() {

        it('should update the task name', function() {
            $('#root').task(render(defaultTask));
            $('#root').task('edit');
            $('input[name="name"]').val('modified');
            assert.equal('my task', $('.task-name').text());
            $('#root form').submit();
            assert.equal('modified', $('.task-name').text());
        })

        it('should remove the .editing class', function() {
            $('#root').task(render(defaultTask));
            $('#root').task('edit');
            assert($('#root').hasClass('editing'));
            $('#root form').submit();
            assert.equal(false, $('#root').hasClass('editing'));
        })

        it('should trigger the update event', function(done) {
            $('#root').task(render(defaultTask));
            $('#root').on('update', function() {
                done();
            });
            $('#root').task('edit');
            $('#root form').submit();
        })

    })

    describe('#changeStatus', function() {

        it('should change the tasks status', function() {
            $('#root').task(render(defaultTask));
            assert.equal('todo', $('input[name="status"]').val());
            assert.equal('todo', $('#root').attr('data-status'));
            $('#root').task('changeStatus', 'something');
            assert.equal('something', $('input[name="status"]').val());
            assert.equal('something', $('#root').attr('data-status'));
        })

        it('should trigger the "update" event', function(done) {
            $('#root').task(render(defaultTask));
            $('#root').on('update', function() {
                done();
            });
            $('#root').task('changeStatus', 'something');
        })

    })

})
