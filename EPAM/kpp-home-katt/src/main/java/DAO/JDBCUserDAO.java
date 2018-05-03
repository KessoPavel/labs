package DAO;

import User.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kesso on 18.3.17.
 * class for working with a table of users in the database
 */
public class JDBCUserDAO implements DataDAO<User> {
    private static final Logger log = Logger.getLogger(JDBCUserDAO.class);
    private Connection connection = null;

    /**
     * method that connects to database
     * @param con Connection
     */
    public void setConnection(Connection con){
        this.connection = con;
    }

    /**
     * create a table users on the database, if it is not already created
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'firstName' text, 'secondName' text, 'eMail' text, 'password' text, 'type' INTEGER , 'loaded' INTEGER , 'lastLoaded' text);");
        log.info("create ar open table documents");
    }

    /**
     * adding new record to the table
     * @param user information about user
     * @param password encrypted password
     * @throws SQLException
     */
    public void insert(User user, String password) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String firstName = user.getFirstName();
            String secondName = user.getSecondName();
            String eMail = user.geteMail();
            int type = user.getType();


            statement.execute("INSERT INTO users (firstName, secondName, eMail, password, type)" +
                    "VALUES ('" + firstName + "', '" + secondName + "', '" + eMail + "', '" + password +
                    "', " + type + ");");
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * eMail search in database
     * @param eMail wanted eMail
     * @return password corresponding to eMail or "", if eMail is not found
     */
    public String searcheMail(String eMail){
        String password = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + eMail + "';");
             password = resultSet.getString("password");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return password;
    }

    /**
     * User search in database
     * @param eMail eMail the wanted user
     * @return object User containing information about wanted user or null
     */
    public User getUser(String eMail){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + eMail + "';");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            Date todayWithZeroTime = null;
            try {
                todayWithZeroTime = formatter.parse(formatter.format(today));
            } catch (ParseException e) {
                log.error(e.getMessage());
            }

            User user = new User(resultSet.getString("firstName"), resultSet.getString("secondName"),
                    resultSet.getString("eMail"), resultSet.getInt("type"));
            if (todayWithZeroTime.toString().equals(resultSet.getString("lastLoaded"))) {//если даты совподают
                user.setLoad(resultSet.getInt("loaded"));
            } else {
                user.setLoad(0);
            }
            return user;

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * save a user load today
     * @param user user
     */
    public void saveLoad(User user) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + user.geteMail() + "';");


            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            Date todayWithZeroTime = null;
            try {
                todayWithZeroTime = formatter.parse(formatter.format(today));
            } catch (ParseException e) {
                log.error(e.getMessage());
            }

            ResultSet temp = statement.executeQuery("update users set lastLoaded = '" + todayWithZeroTime.toString() +
                    "', loaded = " + (int)(user.getLoad()) + " where id = '" + resultSet.getString("id") + "'");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public void insert(User data) throws SQLException {

    }

    @Override
    public List<User> select() {
        return null;
    }
}
