<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>
<#if error??>
    <h2 style="color: red">${error}</h2>
</#if>
<form method="post" action="/signUp">
    <input type="text" name="firstName" placeholder="firstName">
    <input type="text" name="lastName" placeholder="lastName">
    <input type="email" name="email" placeholder="email">
    <input type="text" name="login" placeholder="login">
    <input type="password" name="password" placeholder="password">
    <input hidden="hidden" name="token" value="${_csrf.token}">
    <button type="submit">Register</button>
</form>
</body>
</html>