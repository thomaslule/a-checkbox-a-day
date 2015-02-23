$(function() {

    $.get('/all')
    .done(function(tasks) {
        tasks.forEach(function(task) {
            $('#new-item').before(jadeTaskTemplate(task));
        });
    })
    .fail(Util.displayError);

    $(document).on('submit', '#new-item form', function() {
        var nameInput = $(this).find('input[name="name"]');
        Util.post($(this), function(task) {
            $('#new-item').before(jadeTaskTemplate(task));
            nameInput.val('');
        })
        return false;
    });

    $(document).on('change', '.task-form :checkbox', function() {
        Util.post($(this).closest('form'));
        return false;
    });

    $(document).on('click', '.edit-item-button', function() {
        Item.enterEdit($(this).closest('li'));
        return false;
    });

    $(document).on('click', '.delete-item-button', function() {
        li = $(this).closest('li');
        bootbox.confirm('Voulez-vous supprimer cet item ?', function(result) {
            if (result) {
                Item.delete(li);
            }
        });
    });

    $(document).on('submit', '.task-form', function() {
        li = $(this).closest('li');
        Util.post($(this), function(task) {
            Item.applyEdit(li, task);
        });
        return false;
    });

});

Item = {};
Item.enterEdit = function(li) {
    li.find('.checkbox').hide();
    li.find('.edit-item-input').show();
}

Item.applyEdit = function(li, item) {
    li.find('.task-name').text(item.name);
    li.find('.edit-item-input').hide();
    li.find('.checkbox').show();
}
Item.delete = function(li) {
    $.post('/delete', { id: li.find('input[name="id"]').val() })
    .done(function() {
        li.remove();
    })
    .fail(Util.displayError);
}

Util = {};
Util.post = function(form, callback) {
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
    .fail(Util.displayError);
}

Util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
}
