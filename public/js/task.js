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

    methods.changeStatus = function() {
        taskElt.find('input[name="status"]').val(arg);
        taskElt.attr('data-status', arg);
        taskElt.trigger('update');
    }

    if (method == undefined) {
        // called with no argument
        return taskElt;
    }

    if ($.isPlainObject(method)) {
        // it's not a method, it's the initial task
        taskElt.addClass('task');
        taskElt.attr('data-status', method.status);
        taskElt.append(jadeTaskTemplate(method));
        return taskElt;
    }

    return methods[method]();

}

$(document).on('change', '.task :checkbox', function() {
    $(this).closest('.task').task('changeStatus', $(this).is(':checked') ? 'done' : 'todo');
});

$(document).on('click', '.task .edit-item-button', function() {
    $(this).closest('.task').task('edit');
    return false;
});

$(document).on('click', '.task .cancel-edit-button', function() {
    $(this).closest('.task').task('cancelEdit');
    return false;
});

$(document).on('submit', '.task form', function() {
    var taskElt = $(this).closest('.task');
    taskElt.find('.task-name').text(taskElt.find('input[name="name"]').val());
    taskElt.removeClass('editing');
    taskElt.trigger('update');
    return false;
});

$(document).on('click', '.task .cancel-item-button', function() {
    $(this).closest('.task').task('changeStatus', 'cancelled');
    return false;
});

$(document).on('click', '.task .delete-item-button', function() {
    var taskElt = $(this).closest('.task');
    bootbox.confirm('Voulez-vous supprimer définitivement cet item ?', function(result) {
        if (result) {
            taskElt.task('changeStatus', 'deleted');
        }
    });
    return false;
});

$(document).on('click', '.task .restore-item-button', function() {
    $(this).closest('.task').task('changeStatus', 'todo');
    return false;
});
