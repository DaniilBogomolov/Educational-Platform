const filesSelector = $('#files');
const uploadedFiles = $('#uploaded');


filesSelector.click(function () {
    filesSelector.empty();
    filesSelector.append("<option id='back'>Back</option>");
    filesSelector.append("<option id='uploaded'>Выбрать мой файл</option>");
    filesSelector.append("<option id='uploadNew'>Выбрать файл с компьютера</option>");
}).trigger("change");
