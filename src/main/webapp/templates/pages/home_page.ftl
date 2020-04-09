<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home page</title>
    <link rel="stylesheet" href="/resources/css/home_page_styles.css">
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
            <a class="signUp" href="/signUp">Зарегистрироваться</a>
            <a class="signIn" href="/signIn">Войти</a>
        </div>
    </#if>
</div>
</body>
</html>