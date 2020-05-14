<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Зарегистрироваться</title>
</head>
<body>
<h1><@spring.message 'navbar.sign.up'/></h1>
<div>
    <@spring.bind "signUpDto"/>
    <form method="post" action="/signUp">
        <@spring.message 'sign.up.firstName'/> <br>
        <@spring.formInput "signUpDto.firstName"/>
        <@spring.showErrors "<br>"/>
        <br>
        <@spring.message 'sign.up.lastName'/> <br>
        <@spring.formInput "signUpDto.lastName"/>
        <@spring.showErrors "<br>"/>
        <br>
        <@spring.message 'sign.up.email'/> <br>
        <@spring.formInput "signUpDto.email"/>
        <@spring.showErrors "<br>"/>
        <br>
        <@spring.message 'sign.up.login'/> <br>
        <input type="text" name="login" placeholder="login" required>
        <br>
        <@spring.message 'sign.up.password'/> <br>
        <input type="password" name="password" placeholder="password" required>
        <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <br>
        <button type="submit">Подтвердить</button>
    </form>
</div>
</body>
</html>