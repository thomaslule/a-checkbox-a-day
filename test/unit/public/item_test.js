var assert = require("assert");
var jsdom = require("jsdom");
var fs = require("fs-extra");
var jade = require('jade');
var Item = require('../../../models/itemModel.js')

describe('item', function() {

    var window;
    var defaultItem;

    function $(arg) {
        return window.$(arg);
    }

    function render(item) {
        return $(jade.renderFile('views/item.jade', { item: item }));
    }

    beforeEach(function(done) {
        defaultItem = new Item({ id: 1, type: 'task', name: 'my item', status: 'active', list_type: 'month', list_id: '201501' });
        jsdom.env({
            html: '<div id="root" />',
            scripts: [ 'http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js' ],
            src: [
                fs.readFileSync('public/js/item.js').toString()
            ],
            done: function (errors, windowBuilt) {
                window = windowBuilt;
                done(errors);
            }
        })
    })

    describe('#construct', function() {

        it('should build a item form', function() {
            $('#root').item(render(defaultItem));
            assert($('#root').hasClass('item'));
            assert.equal(1, $('#root').item('getId'));
            assert.equal('active', $('input[name="status"]').val());
            assert($('#root').hasClass('active'));
            assert($('#root').hasClass('task'));
            assert.equal('my item', $('.item-name').text());
            assert.equal(false, $(':checkbox').is(':checked'));
        })

        it('should build a checked item form for done tasks', function() {
            defaultItem.data.status = 'done';
            $('#root').item(render(defaultItem));
            assert($(':checkbox').is(':checked'));
        })

        it('should build an event form', function() {
            defaultItem.data.type = 'event';
            $('#root').item(render(defaultItem));
            assert($('#root').hasClass('event'))
        })

        it('should build a note form', function() {
            defaultItem.data.type = 'note';
            $('#root').item(render(defaultItem));
            assert($('#root').hasClass('note'))
        })

    })

    describe('#getForm', function() {

        it('should get the form', function() {
            $('#root').item(render(defaultItem));
            assert($('#root').item('getForm').is('form'));
        })

    })

    describe('#getId', function() {

        it('should get the id', function() {
            $('#root').item(render(defaultItem));
            assert.equal(1, $('#root').item('getId'));
        })

    })

    describe('#getStatus', function() {

        it('should get the status', function() {
            $('#root').item(render(defaultItem));
            assert.equal('active', $('#root').item('getStatus'));
            $('#root').item('changeStatus', 'done');
            assert.equal('done', $('#root').item('getStatus'));
        })

    })

    describe('#onChangeCheckbox', function() {

        it('should change status when checkbox state changes', function() {
            $('#root').item(render(defaultItem));
            assert.equal('active', $('#root input[name="status"]').val());
            $(':checkbox').prop('checked', true);
            $(':checkbox').change();
            assert.equal('done', $('#root input[name="status"]').val());
        })

    })

    describe('#edit', function() {

        it('should add a .editing class', function() {
            $('#root').item(render(defaultItem));
            assert.equal(false, $('#root').hasClass('editing'));
            $('#root').item('edit');
            assert($('#root').hasClass('editing'));
        })

        it('should remove the .editing class to the other items', function() {
            $('#root').html('<div id="item1" /><div id="item2" />')
            $('#item1, #item2').item(render(defaultItem));
            $('#item1').item('edit');
            assert($('#item1').hasClass('editing'));
            $('#item2').item('edit');
            assert.equal(false, $('#item1').hasClass('editing'));
        })

    })

    describe('#cancelEdit', function() {

        it('should reset the "name" input', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('edit');
            $('input[name="name"]').val('modified');
            assert.equal('modified', $('input[name="name"]').val());
            $('#root').item('cancelEdit');
            assert.equal('my item', $('input[name="name"]').val());
        })

        it('should remove the .editing class', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('edit');
            assert($('#root').hasClass('editing'));
            $('#root').item('cancelEdit');
            assert.equal(false, $('#root').hasClass('editing'));
        })

    })

    describe('#applyEdit', function() {

        it('should update the item name', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('edit');
            $('input[name="name"]').val('modified');
            assert.equal('my item', $('.item-name').text());
            $('#root').item('applyEdit');
            assert.equal('modified', $('.item-name').text());
        })

        it('should remove the .editing class', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('edit');
            assert($('#root').hasClass('editing'));
            $('#root').item('applyEdit');
            assert.equal(false, $('#root').hasClass('editing'));
        })

        it('should trigger the update event', function(done) {
            $('#root').item(render(defaultItem));
            $('#root').on('update', function() {
                done();
            });
            $('#root').item('edit');
            $('#root').item('applyEdit');
        })

    })

    describe('#changeStatus', function() {

        it('should change the items status', function() {
            $('#root').item(render(defaultItem));
            assert.equal('active', $('input[name="status"]').val());
            assert($('#root').hasClass('active'));
            $('#root').item('changeStatus', 'something');
            assert.equal('something', $('input[name="status"]').val());
            assert($('#root').hasClass('something'));
        })

        it('should trigger the "update" event', function(done) {
            $('#root').item(render(defaultItem));
            $('#root').on('update', function() {
                done();
            });
            $('#root').item('changeStatus', 'something');
        })

        it('should enable/disable the checkbox', function() {
            $('#root').item(render(defaultItem));
            assert.equal(false, $(':checkbox').prop('disabled'));
            $('#root').item('changeStatus', 'cancelled');
            assert($(':checkbox').prop('disabled'));
            $('#root').item('changeStatus', 'done');
            assert.equal(false, $(':checkbox').prop('disabled'));
        })

    })

    describe('#delete', function() {

        it('should remove the node', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('delete');
            assert.equal(0, $('#root').length);
        })

        it('should trigger the "delete" event', function(done) {
            $('#root').item(render(defaultItem));
            $('#root').on('delete', function() {
                done();
            });
            $('#root').item('delete');
        })

    })

    describe('#move', function() {

        it('should set the items status to "moved"', function() {
            $('#root').item(render(defaultItem));
            $('#root').item('move', { type: 'month', id: '201502' });
            assert.equal('moved', $('#root').item('getStatus'));
        })

        it('should trigger the "move" event', function(done) {
            $('#root').item(render(defaultItem));
            $('#root').on('move', function(event, destination) {
                assert.equal('month', destination.type);
                assert.equal('201502', destination.id);
                done();
            });
            $('#root').item('move', { type: 'month', id: '201502' });
        })

    })

})
