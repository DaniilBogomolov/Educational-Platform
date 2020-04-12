function sendMessage(text, login, senderFullName, roomGeneratedName) {
    const body = {
        text : text,
        senderFullName : senderFullName,
        roomGeneratedName : roomGeneratedName,
        login : login
    };

    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf_token']").attr("content");

    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: 'application/json',
        dataType: 'json'
    });
}