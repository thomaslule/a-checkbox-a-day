$(function() {

    $('#items-list li:not(#new-item)').each(function() {
        $(this).task();
    });

});

$(document).on('submit', '#new-item form', function() {
    util.send($(this), '/task', 'POST', function(task) {
        $('#new-item').before('<li />');
        var taskElt = $('#new-item').prev().task(task);
        $('#new-item form input[name="name"]').val('');
    });
    return false;
});

$(document).on('update', '.task', function() {
    util.send($(this).task('getForm'), '/task/' + $(this).task('getId'), 'PUT');
});

$(document).on('delete', '.task', function() {
    util.send($(this).task('getForm'), '/task/' + $(this).task('getId'), 'DELETE');
});

util = {};
util.send = function(form, url, method, callback) {
    form.removeAttr('novalidate');
    // html5 validation
    if (!form[0].checkValidity()) {
        form.find(':submit').click();
        return;
    }
    // set novalidate attr in order to avoid html5 validation after the submit event
    form.attr('novalidate', 'novalidate');
    var inputs = form.serializeArray();
    // ajax submit
    $.ajax({
        url: url,
        type: method,
        data: inputs
    })
    .done(function(result) {
        if (callback) callback(result);
    })
    .fail(util.displayError);
}

util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
