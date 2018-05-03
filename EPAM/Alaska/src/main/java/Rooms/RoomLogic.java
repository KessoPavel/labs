package Rooms;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by kesso on 14.05.17.
 */
public class RoomLogic {

    public static Room reservation(Room room) {
        JDBCRoomsDAO roomsDAO = new JDBCRoomsDAO();
        ArrayList<Room> rooms = roomsDAO.select();
        try {
            roomsDAO.closeDataBase();
        } catch (SQLException e) {
        }

        for (Room r : rooms) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date rDate = null;
            java.util.Date roomDate = null;
            try {
                rDate = format.parse(r.getData());
                roomDate = format.parse(room.getData());

            } catch (ParseException e) {
            }

            if (r.getUser().equals("") || rDate.before(roomDate)) {
                if (r.getType().equals(room.getType()) && r.getNumberOfSeats() == room.getNumberOfSeats()) {
                    return r;
                }
            }
        }
        return null;
    }

    public static String getRoom(String l) {
        JDBCRoomsDAO roomsDAO = new JDBCRoomsDAO();
        ArrayList<Room> rooms = roomsDAO.select();
        try {
            roomsDAO.closeDataBase();
        } catch (SQLException e) {
        }
        int flag = 1;
        String html = "";
        if (l.equals("en")) {


            html = "<table class=\"table_style_1\">\n" +
                    "                <tr class=\"table_string_color_1\">\n" +
                    "                    <td><h3>Number</h3></td>\n" +
                    "                    <td><h3>Type</h3></td>\n" +
                    "                    <td><h3>Number of seats</h3></td>\n" +
                    "                    <td><h3>Lodger</h3></td>\n" +
                    "                </tr>\n";
        } else {
            html = "<table class=\"table_style_1\">\n" +
                    "                <tr class=\"table_string_color_1\">\n" +
                    "                    <td><h3>Номер</h3></td>\n" +
                    "                    <td><h3>Тип</h3></td>\n" +
                    "                    <td><h3>Число мест</h3></td>\n" +
                    "                    <td><h3>Жилец</h3></td>\n" +
                    "                </tr>\n";
        }
        for (Room room : rooms) {
            if (flag == 1) {
                html += "                <tr>\n";
                flag = 0;
            } else {
                html += "                <tr class=\"table_string_color_1\">\n";
                flag = 1;
            }

            html += "                    <td><h3>" + room.getNumber() + "</h3></td>\n";
            html += "                    <td><h3>" + room.getType() + "</h3></td>\n";
            html += "                    <td><h3>" + room.getNumberOfSeats() + "</h3></td>\n";
            if (room.getUser() == null) {
                html += "                    <td><h3>Free</h3></td>\n";
            } else {
                html += "                    <td><h3>" + room.getUser() + "</h3></td>\n";
            }
            html += "                </tr>\n";
        }

        html += "            </table>";
        return html;
    }

    public static void saveRoom(Room room) {
        JDBCRoomsDAO roomsDAO = new JDBCRoomsDAO();
        roomsDAO.save(room);
        try {
            roomsDAO.closeDataBase();
        } catch (SQLException e) {
        }
    }
}
