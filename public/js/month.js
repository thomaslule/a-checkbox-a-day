$(function() {

    $.get('/all')
    .done(function(tasks) {
        tasks.forEach(function(task) {
            $('#new-item').before('<li />');
            var taskElt = $('#new-item').prev().task(task);
            taskElt.task('onCheck', function() {
                util.post(taskElt.task('getForm'));
            });
            taskElt.task('onApplyEdit', function() {
                util.post(taskElt.task('getForm'));
            });
            taskElt.task('onDelete', function() {
                $.post('/delete', { id: taskElt.task('getId') })
                .fail(util.displayError);
            });
        });
    })
    .fail(util.displayError);

});

util = {};
util.post = function(form, callback) {
    form.removeAttr('novalidate');
    // html5 validation
    if (!form[0].checkValidity()) {
        form.find(':submit').click();
        return;
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
    .fail(util.displayError);
}

util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
