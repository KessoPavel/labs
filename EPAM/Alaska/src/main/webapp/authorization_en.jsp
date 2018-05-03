<!DOCTYPE html>
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
        <input type="submit" name="a_ru" value="RU">
        </form>
    </span>
</div>

<!--/ top bar -->

<div id="login-form">
    <h1>Welcome to Alaska</h1>
    <fieldset>
        <form action="authorization" method="post">
            <input id="topAria" name="eMail" type="text" required value="Login"
                   onBlur="if(this.value=='')this.value='Login'" onFocus="if(this.value=='Login')this.value='' ">
            <input id="bottomArea" name="password" type="password" required value="Password"
                   onBlur="if(this.value=='')this.value='Password'" onFocus="if(this.value=='Password')this.value='' ">
            <input type="submit" value="Sing In">
            <h3>${error}</h3>
            <a href="singup"><h2>registration</h2></a>
        </form>
    </fieldset>
</div>
</body>
</html>