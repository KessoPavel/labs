package Users;

import Server.MainLogic;
import Users.DAO.JDBCUserDAO;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by kesso on 25.04.17.
 */
public class UserLogic {
    private static final Logger log = Logger.getLogger(UserLogic.class);

    private static boolean emailInDataBase(String eMail){
        JDBCUserDAO userDAO = new JDBCUserDAO();
        try {
            userDAO.createTable();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        if(!userDAO.searcheMail(eMail).equals("")){
            try {
                userDAO.closeDataBase();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
            return true;
        }
        try {
            userDAO.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public static User creatingNewUser (String eMail, String password, int type){
        if(!emailInDataBase(eMail)) {
            User newUser = new User(eMail, type);
            saveFromDataBase(newUser, password);
            if(newUser.getType() == 0){
                newUser.seteMail("admin");
            }
            return newUser;
        }
        log.info("Create new user error");
        return null;
    }

    public static void saveFromDataBase(User newUser, String password){
        try {
            JDBCUserDAO userDAO = new JDBCUserDAO();
            userDAO.createTable();

            userDAO.insert(newUser, MainLogic.coding(password));
            userDAO.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.info("New User created and added to the database");
    }

    public static User entranceCheck(String eMail, String password){
        User user = null;
        JDBCUserDAO userDAO = new JDBCUserDAO();
        try {
            userDAO.createTable();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        String dbPassword = userDAO.searcheMail(eMail);
        if(dbPassword.equals(MainLogic.coding(password))){
            user = userDAO.getUser(eMail);
            if(user.getType()== 0){
                user.seteMail("admin");
            }
            log.info("email and password are correct");
        }
        else{
            log.info("email and password are not correct");
        }
        try {
            userDAO.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return user;
    }

}
