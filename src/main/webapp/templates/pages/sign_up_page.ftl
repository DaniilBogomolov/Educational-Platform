<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Зарегистрироваться</title>
</head>
<body>
<h1>Зарегистрироваться</h1>
<form method="post" action="/signUp">
    <input type="text" name="firstName" placeholder="firstName" required>
    <input type="text" name="lastName" placeholder="lastName" required>
    <input type="email" name="email" placeholder="email" required>
    <input type="text" name="login" placeholder="login" required>
    <input type="password" name="password" placeholder="password" required>
    <input hidden="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit">Подтвердить</button>
</form>
</body>
</html>