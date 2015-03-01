$.fn.task = function(method, arg) {

    var taskElt = this;
    var methods = {};

    methods.getForm = function() {
        return taskElt.find('form');
    };

    methods.getId = function() {
        return taskElt.find('input[name="id"]').val();
    };

    methods.edit = function() {
        $('.task.editing').removeClass(function() {
            $(this).task('cancelEdit');
        });
        taskElt.addClass('editing');
        return taskElt;
    };

    methods.cancelEdit = function() {
        taskElt.find('input[name="name"]').val(taskElt.find('.task-name').text());
        taskElt.removeClass('editing');
    };

    if (method == undefined) {
        // called with no argument
        return taskElt;
    }

    if ($.isPlainObject(method)) {
        // it's not a method, it's the initial task
        taskElt.addClass('task');
        taskElt.append(jadeTaskTemplate(method));
        return taskElt;
    }

    return methods[method]();

}

$(document).on('change', '.task :checkbox', function() {
    var taskElt = $(this).closest('.task');
    taskElt.find('input[name="status"]').val($(this).is(':checked') ? 'done' : 'todo');
    taskElt.trigger('checkUncheck');
});

$(document).on('click', '.task .edit-item-button', function() {
    $(this).closest('.task').task('edit');
    return false;
});

$(document).on('click', '.task .cancel-button', function() {
    $(this).closest('.task').task('cancelEdit');
    return false;
});

$(document).on('click', '.task .delete-item-button', function() {
    var taskElt = $(this).closest('.task');
    bootbox.confirm('Voulez-vous supprimer cet item ?', function(result) {
        if (result) {
            taskElt.trigger('delete');
            taskElt.remove();
        }
    });
    return false;
});

$(document).on('submit', '.task form', function() {
    var taskElt = $(this).closest('.task');
    taskElt.find('.task-name').text(taskElt.find('input[name="name"]').val());
    taskElt.removeClass('editing');
    taskElt.trigger('applyEdit');
    return false;
});
