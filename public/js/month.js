/* Items */

$(function() {

    $('#items-list > li:not(#new-item)').each(function() {
        $(this).item();
    });

});

$(document).on('update', '.item', function() {
    util.sendForm($(this).item('getForm'), '/item/' + $(this).item('getId'), 'PUT');
});

$(document).on('move', '.item', function(event, destination) {
    util.send('/item/' + $(this).item('getId') + '/list', 'PUT', {
        list_type: destination.type,
        list_id: destination.id
    });
});

$(document).on('delete', '.item', function() {
    util.sendForm($(this).item('getForm'), '/item/' + $(this).item('getId'), 'DELETE');
});

/* Calendar */

$(document).on('change', '.day-text', function(event, newText) {
    util.send('/calendar/day/' + $(this).closest('.day').attr('data-id'), 'PUT', { text: newText });
});

/* New item form */

$(document).on('submit', '#new-item form', function() {
    util.sendForm($(this), '/item', 'POST', function(item) {
        $('#new-item').before('<li />');
        var itemElt = $('#new-item').prev().item($(item));
        $('#new-item form input[name="name"]').val('');
    });
    return false;
});

$(document).on('click', '#new-item-type-list a', function() {
    $('#new-item form input[name="type"]').val($(this).attr('data-type'));
    $('#new-item-type-selected').text($(this).text());
    $('#new-item-type-list > li').show();
    $(this).closest('li').hide();
});
