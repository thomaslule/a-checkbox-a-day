var assert = require("assert");
var jsdom = require("jsdom");
var fs = require("fs-extra");
var jade = require('jade');

describe('task', function() {

    var window;
    var defaultTask;

    beforeEach(function(done) {
        defaultTask = { id: 1, name: 'my task', status: 'todo' };
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
            window.$('#root').task(defaultTask);
            assert(window.$('#root').hasClass('task'));
            assert.equal(1, window.$('input[name="id"]').val());
            assert.equal('todo', window.$('input[name="status"]').val());
            assert.equal('todo', window.$('#root').attr('data-status'));
            assert.equal('my task', window.$('.task-name').text());
            assert.equal(false, window.$(':checkbox').is(':checked'));
        })

        it('should build a checked task form', function() {
            defaultTask.status = 'done';
            window.$('#root').task(defaultTask);
            assert.equal(true, window.$(':checkbox').is(':checked'));
        })

    })

    describe('#getForm', function() {

        it('should get the form', function() {
            window.$('#root').task(defaultTask);
            assert(window.$('#root').task('getForm').is('form'));
        })

    })

    describe('#getId', function() {

        it('should get the id', function() {
            window.$('#root').task(defaultTask);
            assert.equal(1, window.$('#root').task('getId'));
        })

    })
    
    describe('#onCheckUncheck', function() {

        it('should trigger checkUncheck event when checkbox state changes', function(done) {
            window.$('#root').task(defaultTask);
            window.$('#root').on('checkUncheck', function() {
                done();
            });
            window.$(':checkbox').change();
        })

    })

    describe('#edit', function() {

        it('should add a .editing class', function() {
            window.$('#root').task(defaultTask);
            assert.equal(false, window.$('#root').hasClass('editing'));
            window.$('#root').task('edit');
            assert(window.$('#root').hasClass('editing'));
        })

        it('should remove the .editing class to the other tasks', function() {
            window.$('#root').html('<div id="task1" /><div id="task2" />')
            window.$('#task1, #task2').task(defaultTask);
            window.$('#task1').task('edit');
            assert(window.$('#task1').hasClass('editing'));
            window.$('#task2').task('edit');
            assert.equal(false, window.$('#task1').hasClass('editing'));
        })

    })

    describe('#cancelEdit', function() {

        it('should reset the "name" input', function() {
            window.$('#root').task(defaultTask);
            window.$('#root').task('edit');
            window.$('input[name="name"]').val('modified');
            assert.equal('modified', window.$('input[name="name"]').val());
            window.$('#root').task('cancelEdit');
            assert.equal('my task', window.$('input[name="name"]').val());
        })

        it('should remove the .editing class', function() {
            window.$('#root').task(defaultTask);
            window.$('#root').task('edit');
            assert(window.$('#root').hasClass('editing'));
            window.$('#root').task('cancelEdit');
            assert.equal(false, window.$('#root').hasClass('editing'));
        })

    })

    describe('#onApplyEdit', function() {

        it('should trigger applyEdit event when edit form is submitted', function(done) {
            window.$('#root').task(defaultTask);
            window.$('#root').on('applyEdit', function() {
                done();
            });
            window.$('#root').task('edit');
            window.$('#root form').submit();
        })

    })

    describe('#cancel', function() {

        it('should set the task cancelled', function() {
            window.$('#root').task(defaultTask);
            assert.equal('todo', window.$('input[name="status"]').val());
            window.$('#root').task('cancel');
            assert.equal('cancelled', window.$('input[name="status"]').val());
            assert.equal('cancelled', window.$('#root').attr('data-status'));
        })

        it('should trigger the "cancel" event', function(done) {
            window.$('#root').task(defaultTask);
            window.$('#root').on('cancel', function() {
                done();
            });
            window.$('#root').task('cancel');
        })

    })

    describe('#delete', function() {

        it('should remove the task', function() {
            defaultTask.status = 'cancelled';
            window.$('#root').task(defaultTask);
            window.$('#root').task('delete');
            assert.equal(0, window.$('#root').length);
        })

        it('should trigger the "delete" event', function(done) {
            defaultTask.status = 'cancelled';
            window.$('#root').task(defaultTask);
            window.$('#root').on('delete', function() {
                done();
            });
            window.$('#root').task('delete');
        })

    })

    describe('#restore', function() {

        it('should put back a cancelled task to status: todo', function() {
            defaultTask.status = 'cancelled';
            window.$('#root').task(defaultTask);
            assert.equal('cancelled', window.$('input[name="status"]').val());
            window.$('#root').task('restore');
            assert.equal('todo', window.$('input[name="status"]').val());
            assert.equal('todo', window.$('#root').attr('data-status'));
        })

        it('should trigger the "restore" event', function(done) {
            window.$('#root').task(defaultTask);
            window.$('#root').on('restore', function() {
                done();
            });
            window.$('#root').task('restore');
        })

    })

})
