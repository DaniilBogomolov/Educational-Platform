const socket = new SockJS("/websocket");
const stomp = Stomp.over(socket);
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
const csrfToken = $("meta[name='_csrf_token']").attr("content");
const generatedName = $("meta[name='_room_generated_name']").attr("content");
const headers = {};
headers[csrfHeader] = csrfToken;
const defaultOption = document.getElementById("default-option");
const select = $("#files");

stomp.connect(headers, function () {
    stomp.subscribe("/topic/room/".concat(generatedName), function (data) {
        insertMessage(JSON.parse(data.body));
    });
});

$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(csrfHeader, csrfToken);
});

window.onload = (ev => {
    loadMessageHistory();
});

function loadMessageHistory() {
    $.ajax({
        method: "get",
        url: "/messages/".concat(generatedName),
        success: function (messages) {
            for (var index in messages) {
                insertMessage(messages[index]);
            }
        }
    });
}

function insertMessage(message) {
    if (message.attachment === null) {
        $('#messages').append('<li>' + message.senderFullName + ': ' + message.text + '</li>');
    } else {
        $('#messages').append('<li>' + message.senderFullName + ': ' + message.text +
            '<br>' + Array(message.senderFullName.length + 2).fill('\xa0').join('')
            + '<a href="' + message.attachment.url + '">' + message.attachment.originalFileName + '</a>' + '</li>');
    }
}


function sendMessage(text, login, senderFullName) {
    const body = {
        "text": text,
        "senderFullName": senderFullName,
        "roomGeneratedName": generatedName,
        "login": login,
        "attachment": {
            "url": $("#files").val(),
            "originalFileName": $("#files option:selected").text()
        }
    };
    $('#text').val("");
    stomp.send("/app/room/".concat(generatedName), headers, JSON.stringify(body));
    select.empty();
    select.append(defaultOption);
}