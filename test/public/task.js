var assert = require("assert");
var jsdom = require("jsdom");
var fs = require("fs-extra");

describe('task', function() {
    describe('#construct', function() {
        it('should build a task form', function() {
            assert.equal(-1, [1,2,3].indexOf(5));
            assert.equal(-1, [1,2,3].indexOf(0));
            jsdom.env(
                '<div />',
                ["http://code.jquery.com/jquery-2.1.3.min.js", fs.readFileSync("public/js/task.js")],
                function (errors, window) {
                    window.$('li').task({ id: 1, name: 'my task', done: 0 });
                    assert.equal(1, window.$('input[name="id"]').val());
                    assert.equal('my task', window.$('.task-name').text());
                    assert.equal(false, window.$(':checkbox').is(':checked'));
                }
            )
        })
    })
})
