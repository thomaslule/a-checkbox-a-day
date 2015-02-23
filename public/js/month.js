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

    $(document).on('change', '.task-form input', function() {
        return Util.post($(this).closest('form'));
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
    var inputs = form.serializeArray();
    // for each unchecked box, submit a 0 value
    form.find('input:checkbox:not(:checked)').each(function() {
        inputs.push({ name: $(this).attr('name') , value: 0 });
    });
    // ajax submit
    $.post(form.attr('action'), inputs)
    .done(function(result) {
        if (callback) callback(result);
    })
    .fail(Util.displayError);
    return false;
}

Util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
