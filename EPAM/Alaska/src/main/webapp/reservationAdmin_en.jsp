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
        <input type="submit" name="ra_ru" value="RU">
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
                        <td><input type="submit" value="Reservation"></td>
                    </form>
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
            <h1>Reservation</h1>
            <table class="table_style">
                <tr>
                    <td><h2>First Name</h2></td>
                    <td>
                        <div class="select_style"><input name="firstName" type="text" name="FirstName"></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Second Name</h2></td>
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
                    <td><h2>Type of room</h2></td>
                    <td>
                        <div class="select_style"><select name="type">
                            <option>Luxe</option>
                            <option>Normal</option>
                            <option>Budgetary</option>
                        </select></div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Number of seats</h2></td>
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
                    <td><h2>Date of arrival</h2></td>
                    <td>
                        <div class="date_style">
                            <input name="date1" type="date" name="arraval">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><h2>Date of departure</h2></td>
                    <td>
                        <div class="date_style">
                            <input name="date2" type="date" name="arraval">
                        </div>
                    </td>
                </tr>
            </table>
            <input type="submit" name="reservation" value="reservation">
        </form>
    </fieldset>
</div>

</body>
</html>