package Users.DAO;

import Archive.DAO.JDBCDossierDAO;
import Users.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by kesso on 25.04.17.
 */
public class JDBCUserDAO implements DAOUser{
    private static final Logger log = Logger.getLogger(JDBCDossierDAO.class);
    private Connection connection = null;

    public JDBCUserDAO(){
        try{
            Class.forName("org.sqlite.JDBC");
            if(connection == null){
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

    public void createTable() throws  SQLException{
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'users'('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'eMail' text, 'password' text, 'type' INTEGER);");
        log.info("create or open table 'dossier'");
    }

    public void insert(User user, String password) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO users (eMail, password, type)" +
                    "VALUES ('" + user.geteMail() + "', '" + password +
                    "', " + user.getType() + ");");
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public String searcheMail(String eMail) {
        String password = "";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + eMail + "'");
            password = resultSet.getString("password");
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return password;
    }

    public void changeUser(User user){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("update users set type = " + user.getType()+ " " +
                    "where eMail = '" + user.geteMail() + "'");
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public User getUser(String eMail) {

        Statement statement = null;
        User user = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE eMail = '" + eMail + "';");

            user = new User(resultSet.getString("eMail"),resultSet.getInt("type"));
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return user;
    }

    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<User>();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            User user = null;
            while (resultSet.next()) {
                user = new User(resultSet.getString("eMail"),resultSet.getInt("type"));
                if(user.getType() != 0)users.add(user);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return users;
    }

    public void delete(User user){
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "DELETE FROM users WHERE eMail = '" + user.geteMail() + "' ");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
