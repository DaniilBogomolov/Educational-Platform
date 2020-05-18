const filesSelector = $("select[id='files']")
const cancel = document.getElementById("cancel");
const cancelElement = document.createElement("option");
cancelElement.setAttribute("value", "none");


function insertFileOption(file) {
    filesSelector.append("<option value='" + file.url + "'>" + file.originalFileName + "</option>");
}

function uploadAvailableFiles(login) {
    $.ajax({
        method : "get",
        url : "/files/user/".concat(login),
        success: function (files) {
            filesSelector.empty();
            filesSelector.append(cancelElement);
            for (var index in files) {
                insertFileOption(files[index]);
            }
        }
    });
}

function sendHomework(roomId, login) {
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf_token']").attr("content");
    const text = $("input[id=homework-text]");

    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    });
    const body =  {
        "text" : text.val(),
        "roomIdentifier" : roomId,
        "senderLogin" : login,
        "attachment" : {
            "originalFileName": $(".att option:selected").text(),
            "url": $(".att").val()
        }
    };

    $.ajax({
        method: "post",
        url : "/homework/create",
        data : JSON.stringify(body),
        dataType: "json",
        contentType: "application/json"
    });
    text.val('');
    clearSelect();
}