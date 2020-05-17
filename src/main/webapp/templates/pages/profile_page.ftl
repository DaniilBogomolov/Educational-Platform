<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>

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

            $("a[class='new-confirm-code']").click(function () {
                $.ajax({
                    url: "/confirm",
                    type: "POST",
                    success: function (status) {
                        alert(status);
                    }
                })
            })
        }


    </script>
</head>
<body>
<div class="navbar-container">
    <a style="color: white" class="files" href="/files"><@spring.message 'files'/></a>
    <div class="profile-links">
        <div class="profile-image-small">
            <a href="/profile"><img src="${user.navbarPhotoLink}" class="profile-image-in-a"></a>
        </div>
        <a style="color: white" class="profile">${user.navbarFirstName}</a>
    </div>
</div>

<div class="content">
    <div class="big-profile-image">
        <form action="/profile?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            <input type="file" name="file" class="input-file" accept="image/*">
            <figure>
                <img src="${user.profilePhotoLink}" class="profile-image">
                <figcaption>${user.role}</figcaption>
            </figure>
            <button type="submit" class="submit-new-profile-photo">
            </button>
        </form>
    </div>

    <div class="profile-info">
        <#if user.myProfile == true>
            <#if user.role == "USER">
                <h3><@spring.message 'student.status.first'/><br><@spring.message 'student.status.second'/></h3>
                <h6><@spring.message 'student.status.third'/><br>
                    <form action="/confirm" method="post">
                        <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input class="new-confirm-code" type="submit" value=<@spring.message 'student.status.fourth'/>/>
                    </form>
                </h6>
            <#elseif user.role == "STUDENT">
                <form action="/confirm/confirmTeacher" method="post">
                    <input type="submit"
                           style="color: black"
                           class="teacher-status"
                           value="<@spring.message 'teacher.status.get'/>"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            <#elseif user.role == "TEACHER">
                <a style="color: black;" href="/room">Создать комнату</a>
            </#if>
        <#else>
            <@spring.message 'textme'/>
        </#if>
        <hr>
        <#if user.rooms??>
            <#list user.rooms>
                <ul>
                    <#items as room>
                        <li><a style="color: black" href="/room/${room.generatedName}">${room.originalName}</a></li>
                    </#items>
                </ul>
            </#list>
        </#if>
    </div>
</div>
</body>

</html>