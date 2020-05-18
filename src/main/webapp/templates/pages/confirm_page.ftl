<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#if error??>
    <h1>${error}</h1>
<#else>
    <h1><@spring.message 'confirmed'/></h1>
    <a href="/profile"><@spring.message "link.profile"/></a>
</#if>
</body>
</html>