<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мои файлы</title>
    <link rel="stylesheet" href="/resources/css/upload_files_styles.css">
</head>

<body>
<div class="navbar-container">
    <a class="files" href="/home"><@spring.message 'main.page'/></a>
    <div class="profile-links">
        <div class="profile-image-small">
            <a href="/profile"><img src="${profilePhotoLink}" class="profile-image-in-a"></a>
        </div>
        <a class="profile">${firstName}</a>
    </div>
</div>
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
                <li><a style="color: black; text-decoration: none" href="${file.url}">${file.originalFileName}</a></li>
            </#items>
        </ul>
    </#list>
</#if>
</body>
</html>