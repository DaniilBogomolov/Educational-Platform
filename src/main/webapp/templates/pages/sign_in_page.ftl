<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>Sign In</h1>
<#if error??>
    <h2 style="color: red">${error}</h2>
</#if>
<form action="/signIn" method="post">
    <input type="text" name="login" placeholder="login"><br>
    <input type="password" name="password" placeholder="password"><br>
    <input hidden="hidden" name="token" value="${_csrf.token}">
    <button type="submit">Sign In</button>
</form>
</body>
</html>