<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мои файлы</title>
    <link rel="stylesheet" href="/resources/css/upload_files_styles.css">
</head>

<body>
<form class="form"
      action="/files?${_csrf.parameterName}=${_csrf.token}"
      method="post"
      enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="Upload">
</form>
<hr>
<#if files??>
    <#list files>
        <ul>
            <#items as file>
                <li><a href="${file.url}">${file.originalFileName}</a></li>
            </#items>
        </ul>
    </#list>
</#if>
</body>
</html>