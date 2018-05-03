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
        <input type="submit" name="ra_en" value="EN">
        </form>
    </span>
</div>
<!--/ top bar -->

<div id="menu-form">
    <fieldset>
        <form>
            <table>
                <tr>
                    <form action="reservation" method="get">
                        <td><input type="submit" value="Бронирование"></td>
                    </form>
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
            <h1>Бронирование</h1>
            <table class="table_style">
                <tr>
                    <td><h2>Имя</h2></td>
                    <td>
                        <div class="select_style"><input name="firstName" type="text" name="FirstName"></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Фамилия</h2></td>
                    <td>
                        <div class="select_style"><input name="secondName" type="text" name="SecondName"></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>e-Mail</h2></td>
                    <td>
                        <div class="select_style"><input name="eMail" type="text" name="eMail"></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Тип номера</h2></td>
                    <td>
                        <div class="select_style"><select name="type">
                            <option>Люкс</option>
                            <option>Обычный</option>
                            <option>Бюджетный</option>
                        </select></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Число мест</h2></td>
                    <td>
                        <div class="select_style"><select name="seats">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Дата заселения</h2></td>
                    <td>
                        <div class="date_style">
                            <input name="date1" type="date" name="arraval">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Дата выезда</h2></td>
                    <td>
                        <div class="date_style">
                            <input name="date2" type="date" name="arraval">
                        </div>
                    </td>
                </tr>
            </table>
            <input type="submit" name="reservation" value="забронировать">
        </form>
    </fieldset>
</div>

</body>
</html>