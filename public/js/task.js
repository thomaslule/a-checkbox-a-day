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
        $('.task').each(function() {
            $(this).task('cancelEdit');
        });
        taskElt.find('.checkbox').hide();
        taskElt.find('.edit-item-input').show();
        return taskElt;
    };

    methods.cancelEdit = function() {
        if (taskElt.find('.edit-item-input').is(':visible')) {
            taskElt.find('input[name="name"]').val(taskElt.find('.task-name').text());
            taskElt.find('.edit-item-input').hide();
            taskElt.find('.checkbox').show();
        }
    };

    if (method == undefined) {
        // called with no argument
        return taskElt;
    }

    if ($.isPlainObject(method)) {
        // it's not a method, it's the initial task
        taskElt.addClass('task');
        taskElt.append(jadeTaskTemplate(method));

        taskElt.find(':checkbox').change(function() {
            taskElt.trigger('checkUncheck');
        });
        taskElt.find('.edit-item-button').click(function() {
            taskElt.task('edit');
            return false;
        });
        taskElt.find('.cancel-button').click(function() {
            taskElt.task('cancelEdit');
            return false;
        });
        taskElt.find('.delete-item-button').click(function() {
            bootbox.confirm('Voulez-vous supprimer cet item ?', function(result) {
                if (result) {
                    taskElt.remove();
                    taskElt.trigger('delete');
                }
            });
            return false;
        });
        taskElt.find('form').submit(function() {
            taskElt.find('.task-name').text(taskElt.find('input[name="name"]').val());
            taskElt.find('.edit-item-input').hide();
            taskElt.find('.checkbox').show();
            taskElt.trigger('applyEdit');
            return false;
        })
        return taskElt;
    }

    return methods[method]();

}
