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
        <input type="submit" name="l_en" value="EN">
        </form>
    </span>
</div>
<!--/ top bar -->

<div id="menu-form">
    <fieldset>
        <form action="reservation" , method="get">
            <table>
                <tr>
                    <td><input type="submit" value="Бронирование"></td>
                    <form action="listofrooms" method="get">
                        <td><input type="submit" value="Список номеров"></td>
                    </form>
                </tr>
            </table>
        </form>
    </fieldset>
</div>

<div id="reserv-form">
    <fieldset>
        <form action="reservation" method="post">
            <h1>Список номеров</h1>
            ${table}
        </form>
    </fieldset>
</div>

</body>
</html>