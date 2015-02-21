$(function() {

    $.get('/all')
    .done(function(tasks) {
        tasks.forEach(function(task) {
            $('#items-list').append(Tasks.taskToHTML(task));
        });
    });

    $('form').submit(function() {
        // html5 validation
        if (!$(this)[0].checkValidity()) {
            $(this).find(':submit').click();
            return false;
        }
        // ajax submit
        $.post($(this).attr('action'), $(this).serialize());
        return false;
    });

});

Tasks = {
    taskToHTML: function(task) {
        // TODO valid form
        return $('<li><input type="checkbox" /> ' + task.name + '</li>');
    }
};
