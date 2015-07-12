/* Items */

$(function() {

    $('#items-list > li:not(#new-item)').each(function() {
        $(this).item();
        $('#migrate').trigger('change');
    });

    /* Calendar */
    var currentMonth = moment($('meta[name="id"]').attr('content'), 'YYYYMM');
    var datepicker = $('#calendar').datepicker({
        language: 'fr',
        calendarWeeks: true,
        todayHighlight: true,
        maxViewMode: 0, // not working until datepicker 1.5.0 :(
        defaultViewDate: {
            year: currentMonth.year(),
            month: currentMonth.month()
        }
    });
    datepicker.selected_month = currentMonth;
    datepicker.on('click', '.datepicker-switch', function(e) {
        window.location.href = '/month/' + datepicker.selected_month.format('YYYYMM');
    })
    datepicker.on('changeMonth', function(e) {
        datepicker.selected_month = moment(e.date);
    });
    datepicker.on('changeDate', function() {
        $(this).datepicker('update', '');
    });

});

$(document).on('update', '.item', function() {
    util.sendForm($(this).item('getForm'), '/item/' + $(this).item('getId'), 'PUT');
    $('#migrate').trigger('change');
});

$(document).on('move', '.item', function(event, destination) {
    util.send('/item/' + $(this).item('getId') + '/list', 'PUT', {
        list_type: destination.type,
        list_id: destination.id
    });
    $('#migrate').trigger('change');
});

$(document).on('delete', '.item', function() {
    util.sendForm($(this).item('getForm'), '/item/' + $(this).item('getId'), 'DELETE');
    $('#migrate').trigger('change');
});

/* New item form */

$(document).on('submit', '#new-item form', function() {
    util.sendForm($(this), '/item', 'POST', function(item) {
        $('#new-item').before('<li />');
        var itemElt = $('#new-item').prev().item($(item));
        $('#new-item form input[name="name"]').val('');
        $('#migrate').trigger('change');
    });
    return false;
});

$(document).on('click', '#new-item-type-list a', function() {
    $('#new-item form input[name="type"]').val($(this).attr('data-type'));
    $('#new-item-type-selected').text($(this).text());
    $('#new-item-type-list > li').show();
    $(this).closest('li').hide();
});

/* Migrate */

$(document).on('click', '#migrate', function() {
    bootbox.confirm('Voulez-vous migrer vos tâches vers le mois suivant ?', function(result) {
        if (result) {
            util.send('/month/' + $('meta[name="id"]').attr('content') + '/tasks/list', 'PUT', {}, function(result) {
                $('.task.active').item('doChangeStatus', 'moved');
                $('#migrate').trigger('change');
            });
        }
    });
});

$(document).on('change', '#migrate', function() {
    $(this).prop('disabled', $('.task.active').length == 0);
});

/* Calendar */

$(document).on('change', '.day-text', function(event, newText) {
    util.send('/calendar/day/' + $(this).closest('.day').attr('data-id'), 'PUT', { text: newText });
});
