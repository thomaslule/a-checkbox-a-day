$(function() {
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
