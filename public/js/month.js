$(function() {

    $.get('/all')
    .done(function(tasks) {
        tasks.forEach(function(task) {
            $('#items-list').append(Tasks.taskToHTML(task));
        });
    });
    // TODO .fail

    $('#new-item').submit(function() {
        var nameInput = $(this).find('input[name="name"]');
        return Util.post($(this), function(task) {
            $('#items-list').append(Tasks.taskToHTML(task));
            nameInput.val('');
        })
    });

});

Tasks = {
    taskToHTML: function(task) {
        // TODO valid form
        return $('<li><input type="checkbox" /> ' + task.name + '</li>');
    }
};

Util = {
    post: function(form, callback) {
        // html5 validation
        if (!form[0].checkValidity()) {
            form.find(':submit').click();
            return false;
        }
        // ajax submit
        $.post(form.attr('action'), form.serialize())
        .done(function(result) {
            callback(result);
        });
        // TODO .fail
        return false;
    }
}
