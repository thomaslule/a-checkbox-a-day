$.fn.item = function(method, arg) {

    var itemElt = this;
    var methods = {};

    methods.getForm = function() {
        return itemElt.find('form');
    };

    methods.getId = function() {
        return itemElt.find('form').attr('data-id');
    };

    methods.getStatus = function() {
        return itemElt.attr('data-status');
    };

    methods.edit = function() {
        $('.item.editing').removeClass(function() {
            $(this).item('cancelEdit');
        });
        itemElt.addClass('editing');
        return itemElt;
    };

    methods.applyEdit = function() {
        itemElt.find('.item-name').text(itemElt.find('input[name="name"]').val());
        itemElt.removeClass('editing');
        itemElt.trigger('update');
    };

    methods.cancelEdit = function() {
        itemElt.find('input[name="name"]').val(itemElt.find('.item-name').text());
        itemElt.removeClass('editing');
    };

    methods.changeStatus = function(status) {
        itemElt.find('input[name="status"]').val(status);
        itemElt.attr('data-status', status);
        itemElt.trigger('update');
    };

    methods.delete = function() {
        itemElt.trigger('delete');
        itemElt.remove();
    };

    methods.move = function(destination) {
        itemElt.find('input[name="status"]').val('moved');
        itemElt.attr('data-status', 'moved');
        itemElt.trigger('move', [ destination ]);
    };

    if (method == undefined) {
        // called with no argument: make it a item object without modifying the html inside
        itemElt.addClass('item');
        itemElt.attr('data-status', itemElt.find('input[name="status"]').val());
        return itemElt;
    }

    if (method instanceof jQuery) {
        // it's not a method, it's the initial item
        itemElt.addClass('item');
        itemElt.attr('data-status', method.find('input[name="status"]').val());
        itemElt.append(method);
        return itemElt;
    }

    return methods[method](arg);

}

$(document).on('change', '.item :checkbox', function() {
    $(this).closest('.item').item('changeStatus', $(this).is(':checked') ? 'done' : 'todo');
});

$(document).on('click', '.item .edit-item-button', function() {
    $(this).closest('.item').item('edit');
    return false;
});

$(document).on('click', '.item .cancel-edit-button', function() {
    $(this).closest('.item').item('cancelEdit');
    return false;
});

$(document).on('submit', '.item form', function() {
    $(this).closest('.item').item('applyEdit');
    return false;
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
    $(this).closest('.item').item('changeStatus', 'todo');
    return false;
});

$(document).on('click', '.item .move-button', function() {
    $(this).closest('.item').item('move', {
        type: $(this).attr('data-destination-type'),
        id: $(this).attr('data-destination-id')
    });
    return false;
});
