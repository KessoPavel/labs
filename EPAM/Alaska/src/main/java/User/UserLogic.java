package User;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by kesso on 14.05.17.
 */
public class UserLogic {
    private static final Logger log = Logger.getLogger(UserLogic.class);

    /**
     * checks database and create new user
     *
     * @param firstName
     * @param secondName
     * @param eMail
     * @param password
     * @return
     */
    public static User creatingNewUser(String firstName, String secondName, String eMail, String password, String rooms) {
        if (!emailInDataBase(eMail)) {
            User newUser = new User(eMail, firstName, secondName, rooms);
            saveFromDataBase(newUser, password);
            return newUser;
        }
        log.info("Create new user error");
        return null;
    }

    /**
     * Checks for e-mail in the database
     *
     * @param eMail
     * @return
     */
    private static boolean emailInDataBase(String eMail) {
        JDBCUserDAO userDAO = new JDBCUserDAO();
        try {
            userDAO.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!userDAO.searcheMail(eMail).equals("")) {
            return true;
        }
        try {
            userDAO.closeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * save new user in database
     *
     * @param newUser
     * @param password
     */
    private static void saveFromDataBase(User newUser, String password) {
        try {
            JDBCUserDAO userDAO = new JDBCUserDAO();

            userDAO.createTable();

            userDAO.insert(newUser, coding(password));
            userDAO.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.info("New User created and added to the database");
    }

    /**
     * check the entrance to the program
     *
     * @param eMail
     * @param password
     * @return
     */
    public static User entranceCheck(String eMail, String password) {
        User user = null;
        JDBCUserDAO userDAO = new JDBCUserDAO();
        String dbPassword = userDAO.searcheMail(eMail);
        if (dbPassword.equals(coding(password))) {
            user = userDAO.getUser(eMail);
            log.info("email and password are correct");
        } else {
            log.info("email and password are not correct");
        }
        try {
            userDAO.closeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * at the end of the program, storing information about the user's uploads to the database
     *
     * @param user
     */
    public static void saveUser(User user) {

        JDBCUserDAO userDAO = new JDBCUserDAO();
        userDAO.save(user);
        try {
            userDAO.closeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String coding(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}
