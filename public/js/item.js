$.fn.item = function(method, arg) {

    var itemElt = this;
    var methods = {};
    var statuses = [ 'active', 'done', 'cancelled', 'moved' ];

    function doChangeStatus(newStatus) {
        itemElt.find('input[name="status"]').val(newStatus);
        itemElt.removeClass(statuses.join(' '));
        itemElt.addClass(newStatus);
        itemElt.find(':checkbox').prop('disabled', newStatus != 'active' && newStatus != 'done');
        itemElt.find('.item-name').attr('contenteditable', newStatus == 'active' ? 'true' : 'false');
    }

    methods.getForm = function() {
        return itemElt.find('form');
    };

    methods.getId = function() {
        return itemElt.find('form').attr('data-id');
    };

    methods.getStatus = function() {
        return statuses.filter(function(status) {
            return itemElt.hasClass(status);
        })[0];
    };

    methods.changeName = function(newName) {
        itemElt.find('input[name="name"]').val(newName);
        itemElt.trigger('update');
    };

    methods.changeStatus = function(status) {
        doChangeStatus(status);
        itemElt.trigger('update');
    };

    methods.delete = function() {
        itemElt.trigger('delete');
        itemElt.remove();
    };

    methods.move = function(destination) {
        doChangeStatus('moved');
        itemElt.trigger('move', [ destination ]);
    };

    if (method == undefined) {
        // called with no argument: make it a item object without modifying the html inside
        itemElt.addClass('item');
        itemElt.addClass(itemElt.find('input[name="type"]').val());
        itemElt.addClass(itemElt.find('input[name="status"]').val());
        return itemElt;
    }

    if (method instanceof jQuery) {
        // it's not a method, it's the initial item
        itemElt.addClass('item');
        itemElt.append(method);
        itemElt.addClass(itemElt.find('input[name="type"]').val());
        itemElt.addClass(itemElt.find('input[name="status"]').val());
        return itemElt;
    }

    return methods[method](arg);

}

$(document).on('change', '.task :checkbox', function() {
    $(this).closest('.task').item('changeStatus', $(this).is(':checked') ? 'done' : 'active');
});

$(document).on('focusout', '.item .item-name', function() {
    $(this).closest('.item').item('changeName', $(this).text());
});

$(document).on('keypress', '.item .item-name', function(e) {
    if (e.keyCode == 13) {
        // enter key
        $(this).blur();
        return false;
    }
});

$(document).on('click', '.item .cancel-item-button', function() {
    $(this).closest('.item').item('changeStatus', 'cancelled');
    return false;
});

$(document).on('click', '.item .delete-item-button', function() {
    var itemElt = $(this).closest('.item');
    bootbox.confirm('Voulez-vous supprimer définitivement cet item ?', function(result) {
        if (result) {
            itemElt.item('delete');
        }
    });
    return false;
});

$(document).on('click', '.item .restore-item-button', function() {
    $(this).closest('.item').item('changeStatus', 'active');
    return false;
});

$(document).on('click', '.item .move-button', function() {
    $(this).closest('.item').item('move', {
        type: $(this).attr('data-destination-type'),
        id: $(this).attr('data-destination-id')
    });
    return false;
});
