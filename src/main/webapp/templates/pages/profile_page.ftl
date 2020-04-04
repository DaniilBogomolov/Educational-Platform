<!doctype html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home page</title>
    <link rel="stylesheet" href="/resources/css/profile_page_styles.css">
    <script src="/resources/js/jQuery.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            $("img[class='profile-image']").click(function () {
                $("input[type='file']").click();
            });

            $("input[type='file']").on('input', function () {
                $("button[class='submit-new-profile-photo']").click();
            });

        }
    </script>
</head>
<#if profile_image??>
<#else>
    <#assign profile_image = "/resources/img/anonymous.png">
</#if>
<body>
<div class="navbar-container">
    <a class="files" href="/files">Мои файлы</a>
    <div class="profile-links">
        <div class="profile-image-small">
            <img src = "${profile_image}" class="profile-image">
        </div>
        <a class="profile">Имя</a>
    </div>
</div>


<div class="big-profile-image">
    <form action="/profile?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
        <input type="file" name="file" class="input-file" accept="image/*">
        <img src = "${profile_image}" class="profile-image">
        <button type="submit" class="submit-new-profile-photo">
        </button>
    </form>
</div>
</body>

</html>