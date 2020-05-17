const filesSelector = $('#files');
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