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
    <title>${roomUserInfo.originalRoomName}</title>
</head>
<body>
<div class="navbar-container">
    <a style="color: white" class="files" href="/files"><@spring.message 'files'/></a>
    <div class="profile-links">
        <div class="profile-image-small">
            <a href="/profile"><img src="${roomUserInfo.profilePhotoLink}" class="profile-image-in-a"></a>
        </div>
        <a style="color: white" class="profile">${roomUserInfo.firstName}</a>
    </div>
</div>
<h1>Это комната ${roomUserInfo.originalRoomName}</h1>
<#if roomUserInfo.owner>
    <p><@spring.message 'room.identifier'/>: ${roomUserInfo.generatedRoomName}</p>
</#if>
<div class="room">
    <div class="content">
        <ul id="messages">
        </ul>
        <hr>
        <div id="input">
            <input id="text" type="text">
            <select id="files" onclick="uploadAvailableFiles('${roomUserInfo.login}')">
                <option id="default-option" selected value="none"><@spring.message 'attach.file'/></option>
                <option id="cancel" hidden value="<@spring.message 'cancel'/>"></option>
            </select>
        </div>
        <button id="send-new-message-button" onclick="sendMessage(
                $('#text').val(),
                '${roomUserInfo.login}',
                '${roomUserInfo.fullName}'
                )"><@spring.message 'send.message'/>
        </button>
    </div>
    <#if roomUserInfo.owner>
        <div class="homework">
            <h3 style="text-align: center">Создать домашнее задание</h3>
            <input id="homework-text" type="text"/>
            <select id="files" class="att" onclick="uploadAvailableFiles('${roomUserInfo.login}')">
                <option id="default-option" selected value="none"><@spring.message 'attach.file'/></option>
            </select>
            <button style="align-self: center" onclick="sendHomework('${roomUserInfo.generatedRoomName}', '${roomUserInfo.login}')">Отправить</button>
            <hr>
            <h6 style="text-align: center">Домашние задания для проверки: </h6>
            <ul id="homeworks"></ul>

        </div>
    </#if>

</div>
<script src="/resources/js/websocket_chat.js"></script>
<script src="/resources/js/room_page_logic.js"></script>
<#if roomUserInfo.owner>
    <script>loadHomeworks()</script>
</#if>
</body>
</html>