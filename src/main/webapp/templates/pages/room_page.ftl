<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <meta name="_csrf_token" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="stylesheet" href="/resources/css/room_page_styles.css">
    <script src="/resources/js/jQuery.js"></script>
    <script src="/resources/js/chat.js"></script>
    <title>${roomUserInfo.originalRoomName}</title>
</head>
<body onload="receiveMessageHistory('${roomUserInfo.generatedRoomName}')">
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
<div class="content">
    <ul id="messages">
    </ul>
    <hr>
    <input id="new-message-text" type="text">
    <button id="send-new-message-button" onclick="sendMessage(
            $('#new-message-text').val(),
            '${roomUserInfo.login}',
            '${roomUserInfo.fullName}',
            '${roomUserInfo.generatedRoomName}')">Отправить сообщение
    </button>
</div>
</body>
</html>