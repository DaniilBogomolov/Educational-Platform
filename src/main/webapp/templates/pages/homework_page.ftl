<html>
<head>
    <meta name="_csrf_token" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script src="/resources/js/jQuery.js"></script>
</head>
<body>
<input type="text" name="text" placeholder="Ответ">
<select id="select">
    <#if info.files?has_content>
        <option value="none" hidden disabled selected></option>
        <#list info.files>
            <#items as file>
                <option value="${file.url}">${file.originalFileName}</option>
            </#items>
        </#list>
    <#else>
        <option value="none" selected></option>
    </#if>
</select><br>
<input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<button type="submit" onclick="sendHW()">Отправить
</button>

<script>
    function sendHW() {
        const body = {
            "text": $("input[name=text]").val(),
            "roomIdentifier": '${info.roomIdentifier}',
            "senderLogin": '${info.senderLogin}',
            "attachment": {
                "originalFileName": $("#select option:selected").text(),
                "url": $("#select option:selected").val()
            }
        };


        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf_token']").attr("content");

        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        });

        $.ajax({
            method: "post",
            url: "/homework",
            data: JSON.stringify(body),
            dataType: "json",
            contentType: "application/json"
        });

        window.location.replace("/profile");
    }
</script>
</body>
</html>
