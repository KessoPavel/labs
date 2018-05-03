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
        <input type="submit" name="l_ru" value="RU">
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
                        <td><input type="submit" value="List of rooms"></td>
                    </form>
                </tr>
            </table>
        </form>
    </fieldset>
</div>

<div id="reserv-form">
    <fieldset>
        <form action="reservation" method="post">
            <h1>List of rooms</h1>
            ${table}
        </form>
    </fieldset>
</div>

</body>
</html>