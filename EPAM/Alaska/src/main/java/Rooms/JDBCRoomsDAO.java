package Rooms;

import User.JDBCUserDAO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by kesso on 14.05.17.
 */
public class JDBCRoomsDAO {
    private static final Logger log = Logger.getLogger(JDBCUserDAO.class);
    private Connection connection = null;

    public JDBCRoomsDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:Rooms.db");
                log.info("connecting from database");
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void closeDataBase() throws SQLException {
        connection.close();
    }

    /**
     * create a table users on the database, if it is not already created
     *
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'rooms' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'number' text, 'type' text, 'seats' text, 'date' text, 'eMail' text);");
        log.info("create ar open table documents");
    }

    /**
     * adding new record to the table
     *
     * @param room information about room
     * @throws SQLException
     */
    public void insert(Room room) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String number = new Integer(room.getNumber()).toString();
            String type = room.getType();
            String seats = new Integer(room.getNumberOfSeats()).toString();
            String date = room.getData();
            String eMail = room.getUser();


            statement.execute("INSERT INTO rooms (number,type,seats,date,eMail)" +
                    "VALUES ('" + number + "', '" + type + "', '" + seats + "', '" + date +
                    "', '" + eMail + "');");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Room> select() {
        ArrayList<Room> rooms = new ArrayList<Room>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");
            Room room = null;
            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String type = resultSet.getString("type");
                String seats = resultSet.getString("seats");
                String date = resultSet.getString("date");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date0 = new Date(System.currentTimeMillis());

                java.util.Date date1 = format.parse(date);

                String eMail = "";
                if (date1.after(date0)) {
                    eMail = resultSet.getString("eMail");
                }

                room = new Room(Integer.parseInt(number), type, Integer.parseInt(seats), date, eMail);

                rooms.add(room);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rooms;
    }


    /**
     * save a room
     *
     * @param room room
     */
    public void save(Room room) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE number = " + new Integer(room.getNumber()).toString() + ";");

            System.out.print(new Integer(room.getNumber()).toString());

            ResultSet temp = statement.executeQuery("update rooms set date = '" + room.getData() +
                    "', eMail = '" + room.getUser() + "' where id = '" + resultSet.getString("id") + "'");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
