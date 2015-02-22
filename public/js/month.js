$(function() {

    $.get('/all')
    .done(function(tasks) {
        tasks.forEach(function(task) {
            $('#items-list').append(jadeTaskTemplate(task));
        });
    })
    .fail(Util.displayError);

    $('#new-item').submit(function() {
        var nameInput = $(this).find('input[name="name"]');
        return Util.post($(this), function(task) {
            $('#items-list').append(jadeTaskTemplate(task));
            nameInput.val('');
        })
    });

});

Util = {}
Util.post = function(form, callback) {
    form.removeAttr('novalidate');
    // html5 validation
    if (!form[0].checkValidity()) {
        form.find(':submit').click();
        return false;
    }
    // set novalidate attr in order to avoid html5 validation after the submit event
    form.attr('novalidate', 'novalidate');
    // ajax submit
    $.post(form.attr('action'), form.serialize())
    .done(function(result) {
        callback(result);
    })
    .fail(Util.displayError);
    return false;
}

Util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
