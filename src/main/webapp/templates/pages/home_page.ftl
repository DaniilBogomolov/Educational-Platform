<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home page</title>
    <link rel="stylesheet" href="/resources/css/home_page_styles.css">
    <script src="/resources/js/jQuery.js"></script>
    <script>
        window.onload = function () {
            $('#connectToRoomButton').click(function () {
                const roomId = $('#roomId').val();
                $('#connectToRoomForm').attr('action', 'room/' + roomId);
            })
        }
    </script>
</head>

<body>
<div class="navbar-container">
    <#if user??>
        <a class="files" href="/files">Мои файлы</a>
        <div class="profile-links">
            <div class="profile-image-small">
                <a href="/profile"><img src="${user.profilePhotoLink}" class="profile-image-in-a"></a>
            </div>
            <a class="profile">${user.firstName}</a>
        </div>
    <#else>
        <div class="right-elements">
            <a class="signUp" href="/signUp"><@spring.message 'navbar.sign.up'/></a>
            <a class="signIn" href="/signIn"><@spring.message 'navbar.sign.in'/></a>
        </div>
    </#if>
</div>
<div class="content">
    <#if user?has_content>
        <h1>Мои комнаты</h1>
        <#if user.rooms??>
            <#list user.rooms>
                <ul>
                    <#items as room>
                        <li><a style="color: black" href="/room/${room.generatedName}">${room.originalName}</a></li>
                    </#items>
                </ul>
            </#list>
        </#if>
        <hr>
        <h1>Присоедениться к существующей комнате по идентификатору:</h1>
        <form id="connectToRoomForm" action="/room" method="post">
            <input id="roomId" type="text" required>
            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
            <button id="connectToRoomButton" type="submit">Присоединиться</button>
        </form>
    </#if>
</div>
</body>
</html>