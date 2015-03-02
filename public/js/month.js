$(function() {

    $('#items-list li:not(#new-item)').each(function() {
        $(this).task();
    });

});

$(document).on('submit', '#new-item form', function() {
    $('#new-item').before('<li />');
    util.post($(this), '/task', function(task) {
        var taskElt = $('#new-item').prev().task(task);
        $('#new-item form input[name="name"]').val('');
    });
    return false;
});

$(document).on('update', '.task', function() {
    util.post($(this).task('getForm'), '/task/' + $(this).task('getId'));
});

util = {};
util.post = function(form, url, callback) {
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
    $.post(url, inputs)
    .done(function(result) {
        if (callback) callback(result);
    })
    .fail(util.displayError);
}

util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
