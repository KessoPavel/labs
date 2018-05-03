<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html id="imageA">
<head>
    <meta charset="UTF-8">
    <title>Alaska Hotel</title>
    <link rel="stylesheet" href="authorization.css" media="screen" type="text/css"/>
</head>

<body>

<!-- top bar -->

<div class="vladmaxi-top">
    <a href="">Alaska Hotel</a>
    <span class="right">
        <form action="chl" method="get">
        <input type="submit" name="a_en" value="EN">
        </form>
    </span>
</div>

<!--/ top bar -->

<div id="login-form">
    <h1>Добро пожаловать</h1>
    <fieldset>
        <form action="authorization" method="post">
            <input id="topAria" name="eMail" type="text" required value="Логин"
                   onBlur="if(this.value=='')this.value='Логин'" onFocus="if(this.value=='Логин')this.value='' ">
            <input id="bottomArea" name="password" type="password" required value="Пороль"
                   onBlur="if(this.value=='')this.value='Пороль'" onFocus="if(this.value=='Пороль')this.value='' ">
            <input type="submit" value="Войти">
            <h3>${error}</h3>
            <a href="singup"><h2>регистрация</h2></a>
        </form>
    </fieldset>
</div>
</body>
</html>