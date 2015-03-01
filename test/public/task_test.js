var assert = require("assert");
var jsdom = require("jsdom");
var fs = require("fs-extra");
var jade = require('jade');

describe('task', function() {

    var window;
    var defaultTask;

    beforeEach(function(done) {
        defaultTask = { id: 1, name: 'my task', done: 0 };
        jsdom.env({
            html: '<div id="root" />',
            scripts: [ "https://code.jquery.com/jquery-1.11.2.min.js" ],
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
            assert.equal(1, window.$('input[name="id"]').val());
            assert.equal('my task', window.$('.task-name').text());
            assert.equal(false, window.$(':checkbox').is(':checked'));
        })

        it('should build a checked task form', function() {
            defaultTask.done = 1;
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

        it('should make the "name" input visible', function() {
            window.$('#root').task(defaultTask);
            assert(isHidden(window.$('input[name="name"]')));
            window.$('#root').task('edit');
            assert(isVisible(window.$('input[name="name"]')));
            assert.equal('my task', window.$('input[name="name"]').val());
        })

        it('should hide the other tasks inputs', function() {
            window.$('#root').html('<div id="task1" /><div id="task2" />')
            window.$('#task1, #task2').task(defaultTask);
            assert(isHidden(window.$('#task1 input[name="name"]')));
            assert(isHidden(window.$('#task2 input[name="name"]')));
            window.$('#task1').task('edit');
            assert(isVisible(window.$('#task1 input[name="name"]')));
            window.$('#task2').task('edit');
            assert(isVisible(window.$('#task2 input[name="name"]')));
            assert(isHidden(window.$('#task1 input[name="name"]')));
        })

    })

    describe('#cancelEdit', function() {

        it('should hide the "name" input', function() {
            window.$('#root').task(defaultTask);
            window.$('#root').task('edit');
            assert(isVisible(window.$('input[name="name"]')));
            window.$('#root').task('cancelEdit');
            assert(isHidden(window.$('input[name="name"]')));
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

    // cannot directly use :hidden/:visible https://github.com/tmpvar/jsdom/issues/1048
    function isHidden(jQueryElt) {
        return jQueryElt.is(':hidden') || jQueryElt.parents().is(':hidden');
    }
    function isVisible(jQueryElt) {
        return !isHidden(jQueryElt);
    }

})
