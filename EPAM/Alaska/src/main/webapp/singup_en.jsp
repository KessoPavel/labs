<!DOCTYPE html>
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
        <input type="submit" name="sup_ru" value="RU">
        </form>
    </span>
</div>
<!--/ top bar -->

<div id="login-form">
    <h1>Welcome to Alaska</h1>
    <fieldset>
        <form action="singup" method="post">
            <input id="topAria" name="eMail" type="email" required value="Login"
                   onBlur="if(this.value=='')this.value='Login'" onFocus="if(this.value=='Login')this.value='' ">
            <input name="firstName" id="centralArea" required value="FirstName"
                   onBlur="if(this.value=='')this.value='FirstName'"
                   onFocus="if(this.value=='FirstName')this.value='' ">
            <input name="secondName" id="bottomArea" required value="SecondName"
                   onBlur="if(this.value=='')this.value='SecondName'"
                   onFocus="if(this.value=='SecondName')this.value='' ">
            <input id="topAria" name="password" type="password" required value="Password"
                   onBlur="if(this.value=='')this.value='Password'" onFocus="if(this.value=='Password')this.value='' ">
            <input id="bottomArea" name="RPassword" type="password" required value="RPassword"
                   onBlur="if(this.value=='')this.value='RPassword'"
                   onFocus="if(this.value=='RPassword')this.value='' ">
            <input type="submit" value="Registration">
            <h3>${error}</h3>
            <a href="authorization"><h2>Sing In</h2></a>
        </form>
    </fieldset>
</div>
</body>
</html>