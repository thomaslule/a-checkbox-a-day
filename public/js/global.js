
$(document).on('keypress', '*[contenteditable="true"]', function(e) {
    if (e.keyCode == 13) {
        // enter key
        $(this).blur();
        return false;
    }
});

$(document).on('focusout', '*[contenteditable="true"]', function() {
    $(this).trigger('change', [ $(this).text() ]);
});
