<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Авторизация</title>
    <link rel="stylesheet" href="/resources/css/sign_in_styles.css"/>
</head>
<body>
<h1>Войти</h1>
<form action="/signIn" method="post">
    <input type="text" name="login" placeholder="login" required>
    <br>
    <input type="password" name="password" placeholder="password" required>
    <br>
    <label>
        <input type="checkbox" name="remember-me"><@spring.message 'rememberme'/>
    </label>
    <input hidden="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}">
    <button type="submit"><@spring.message 'navbar.sign.in'/></button>
</form>
</body>
</html>