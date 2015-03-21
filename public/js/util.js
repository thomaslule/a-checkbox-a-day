util = {};

util.sendForm = function(form, url, method, callback) {
    form.removeAttr('novalidate');
    // html5 validation
    if (!form[0].checkValidity()) {
        form.find(':submit').click();
        return;
    }
    // set novalidate attr in order to avoid html5 validation after the submit event
    form.attr('novalidate', 'novalidate');
    // ajax submit
    util.send(url, method, form.serializeArray(), callback);
};

util.send = function(url, method, data, callback) {
    $.ajax({
        url: url,
        type: method,
        data: data
    })
    .done(function(result) {
        if (callback) callback(result);
    })
    .fail(util.displayError);
};

util.displayError = function() {
    $('#error').show().delay(2000).hide(0);
};
