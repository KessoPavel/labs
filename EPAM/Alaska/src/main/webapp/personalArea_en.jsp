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
        <input type="submit" name="p_ru" value="RU">
        </form>
    </span>
</div>
<!--/ top bar -->

<div id="menu-form">
    <fieldset>
        <form action="reservation" , method="get">
            <table>
                <tr>
                    <td><input type="submit" value="Reservation"></td>
                    <form action="listofrooms" method="get">
                        <td><input type="submit" value="Personal Area"></td>
                    </form>
                </tr>
            </table>
        </form>
    </fieldset>
</div>

<div id="reserv-form">
    <fieldset>
        <form action="exit" method="get">
            <h1>Personal Area</h1>
            <table class="table_style">
                <tr>
                    <td><h2>First name</h2></td>
                    <td><h2>${firstName}</h2></td>
                </tr>
                <tr>
                    <td><h2>Second Name</h2></td>
                    <td><h2>${secondName}</h2></td>
                </tr>
                <tr>
                    <td><h2>e-Mail</h2></td>
                    <td><h2>${eMail}</h2></td>
                </tr>
            </table>
            <input type="submit" name="exit" value="Exit">
        </form>

    </fieldset>
</div>

</body>
</html>