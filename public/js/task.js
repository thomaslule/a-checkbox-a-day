$.fn.task = function(method, arg) {

    var taskElt = this;
    var methods = {};

    var doApplyEdit = function(callback) {
        taskElt.find('.task-name').text(taskElt.find('input[name="name"]').val());
        taskElt.find('.edit-item-input').hide();
        taskElt.find('.checkbox').show();
        if (callback) callback();
    }

    var doDelete = function(callback) {
        bootbox.confirm('Voulez-vous supprimer cet item ?', function(result) {
            if (result) {
                taskElt.remove();
                if (callback) callback();
            }
        });
    }

    methods.getForm = function() {
        return taskElt.find('form');
    };

    methods.getId = function() {
        return taskElt.find('input[name="id"]').val();
    };

    // arg must be a callback function
    methods.onCheck = function() {
        taskElt.find(':checkbox').change(arg);
        return taskElt;
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

    // arg must be a callback function
    methods.onApplyEdit = function() {
        taskElt.find('form').submit(function() {
            doApplyEdit(arg);
            return false;
        });
        return taskElt;
    };

    // arg must be a callback function
    methods.onDelete = function() {
        taskElt.find('.delete-item-button').click(function() {
            doDelete(arg);
            return false;
        });
        return taskElt;
    };

    if (method == undefined) {
        // called with no argument
        return taskElt;
    }

    if ($.isPlainObject(method)) {
        // it's not a method, it's the initial task
        taskElt.append(jadeTaskTemplate({ task: method }));

        taskElt.find('.edit-item-button').click(function() {
            taskElt.task('edit');
            return false;
        });
        taskElt.find('.cancel-button').click(function() {
            taskElt.task('cancelEdit');
            return false;
        });
        taskElt.find('form').submit(function() {
            doApplyEdit();
            return false;
        })
        return taskElt;
    }

    return methods[method]();

}
