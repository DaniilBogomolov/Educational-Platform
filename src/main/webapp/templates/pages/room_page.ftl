<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/resources/css/profile_page_styles.css">
    <title>Document</title>
</head>
<body>
<div class="navbar-container">
    <a class="files" href="/files">Мои файлы</a>
    <div class="profile-links">
        <div class="profile-image-small">
            <a href="/profile"><img src="${roomUserInfo.profilePhotoLink}" class="profile-image-in-a"></a>
        </div>
        <a class="profile">${roomUserInfo.firstName}</a>
    </div>
</div>
<h1>Это комната ${roomUserInfo.originalRoomName}</h1>
<#if roomUserInfo.owner>
    <p>Идентификатор для добавления студентов: ${roomUserInfo.generatedRoomName}</p>
</#if>
</body>
</html>