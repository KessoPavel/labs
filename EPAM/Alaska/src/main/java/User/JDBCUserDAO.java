package User;

import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Created by kesso on 14.05.17.
 */
public class JDBCUserDAO {
    private static final Logger log = Logger.getLogger(JDBCUserDAO.class);
    private Connection connection = null;

    public JDBCUserDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:Users.db");
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
        statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'firstName' text, 'secondName' text, 'eMail' text, 'password' text, 'rooms' text);");
        log.info("create ar open table documents");
    }

    /**
     * adding new record to the table
     *
     * @param user     information about user
     * @param password encrypted password
     * @throws SQLException
     */
    public void insert(User user, String password) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String firstName = user.getFirstName();
            String secondName = user.getSecondName();
            String eMail = user.geteMail();
            String rooms = " ";
            for (Integer integer : user.getRooms()) {
                rooms += integer.toString();
                rooms += " ";
            }

            statement.execute("INSERT INTO users (firstName, secondName, eMail, password, rooms)" +
                    "VALUES ('" + firstName + "', '" + secondName + "', '" + eMail + "', '" + password +
                    "', '" + rooms + "');");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * eMail search in database
     *
     * @param eMail wanted eMail
     * @return password corresponding to eMail or "", if eMail is not found
     */
    public String searcheMail(String eMail) {
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
     *
     * @param eMail eMail the wanted user
     * @return object User containing information about wanted user or null
     */
    public User getUser(String eMail) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + eMail + "';");

            User user = new User(resultSet.getString("eMail"), resultSet.getString("firstName"), resultSet.getString("secondName"),
                    resultSet.getString("rooms"));

            return user;

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * save a user
     *
     * @param user user
     */
    public void save(User user) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + user.geteMail() + "';");

            String rooms = " ";
            for (Integer integer : user.getRooms()) {
                rooms += integer.toString();
                rooms += " ";
            }

            ResultSet temp = statement.executeQuery("update users set firstName= '" + user.getFirstName() +
                    "', secondName = '" + user.getSecondName() + "', rooms = '" + rooms + "' where id = '" + resultSet.getString("id") + "'");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
