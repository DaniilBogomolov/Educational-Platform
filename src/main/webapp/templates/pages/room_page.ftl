<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <meta name="_csrf_token" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_room_generated_name" content="${roomUserInfo.generatedRoomName}">

    <link rel="stylesheet" href="/resources/css/room_page_styles.css">
    <script src="/resources/js/jQuery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/resources/js/websocket_chat.js"></script>
    <title>${roomUserInfo.originalRoomName}</title>
</head>
<body>
<div class="navbar-container">
    <a class="files" href="/files"><@spring.message 'files'></a>
    <div class="profile-links">
        <div class="profile-image-small">
            <a href="/profile"><img src="${roomUserInfo.profilePhotoLink}" class="profile-image-in-a"></a>
        </div>
        <a class="profile">${roomUserInfo.firstName}</a>
    </div>
</div>
<h1>Это комната ${roomUserInfo.originalRoomName}</h1>
<#if roomUserInfo.owner>
    <p><@spring.message 'room.identifier'/>: ${roomUserInfo.generatedRoomName}</p>
</#if>
<div class="content">
    <ul id="messages">
    </ul>
    <hr>
    <div id="input">
        <input id="text" type="text">
        <select id="files">
        </select>
    </div>
    <button id="send-new-message-button" onclick="sendMessage(
            $('#text').val(),
            '${roomUserInfo.login}',
            '${roomUserInfo.fullName}'
            )"><@spring.message 'send.message'/>
    </button>
</div>
<script src="/resources/js/room_page_logic.js"></script>
</body>
</html>