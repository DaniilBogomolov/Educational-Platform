<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#if userCookie??>
<form action="/files" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="Upload">
</form>
<hr>
<#if files??>
<#list files>
    <ul>
        <#items as file>
            <li><a href="${file.url}">${file.originalFileName}</a> </li>
        </#items>
    </ul>
</#list>
</#if>
    <#else>
    Sorry, but to upload and get your uploaded files you should be logged in!<br>
    <a href="/signIn">Sign In</a>
</#if>
</body>
</html>