<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html id="imageUp">
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
        <input type="submit" name="sup_en" value="EN">
        </form>
    </span>
</div>
<!--/ top bar -->

<div id="login-form">
    <h1>Добро пожаловать</h1>
    <fieldset>
        <form action="singup" method="post">
            <input id="topAria" name="eMail" type="email" required value="Логин"
                   onBlur="if(this.value=='')this.value='Логин'" onFocus="if(this.value=='Логин')this.value='' ">
            <input name="firstName" id="centralArea" required value="Имя"
                   onBlur="if(this.value=='')this.value='Имя'"
                   onFocus="if(this.value=='Имя')this.value='' ">
            <input name="secondName" id="bottomArea" required value="Фамилия"
                   onBlur="if(this.value=='')this.value='Фамилия'"
                   onFocus="if(this.value=='Фамилия')this.value='' ">
            <input id="topAria" name="password" type="password" required value="Пороль"
                   onBlur="if(this.value=='')this.value='Пороль'" onFocus="if(this.value=='Пороль')this.value='' ">
            <input id="bottomArea" name="RPassword" type="password" required value="RPassword"
                   onBlur="if(this.value=='')this.value='RPassword'"
                   onFocus="if(this.value=='RPassword')this.value='' ">
            <input type="submit" value="Регистрация">
            <h3>${error}</h3>
            <a href="authorization"><h2>авторизация</h2></a>
        </form>
    </fieldset>
</div>
</body>
</html>