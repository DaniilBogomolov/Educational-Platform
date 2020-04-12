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


function receiveMessage(roomGeneratedName) {
    $.ajax({
        url: "/messages?roomGeneratedName=" + roomGeneratedName,
        method: "GET",
        dataType: "json",
        contentType: "application/json",

        success: function (response) {
            for (var key in response) {
                if (response.hasOwnProperty(key)) {
                    $('#messages').append('<li>' + response[key]["senderFullName"] + ': ' + response[key]["text"] + '</li>')
                }
            }
            receiveMessage();
        }
    });
}
