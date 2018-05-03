package Logic;

import DAO.JDBCUserDAO;
import User.User;
import org.apache.log4j.*;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by kesso on 4.3.17.
 */
public class UserLogic {
    private static final Logger log = Logger.getLogger(UserLogic.class);

    /**
     * checks database and create new user
     * @param firstName
     * @param secondName
     * @param eMail
     * @param password
     * @param type
     * @return
     */
    public static User creatingNewUser (String firstName, String secondName, String eMail, String password, int type){
        if(!emailInDataBase(eMail)) {
            User newUser = new User(firstName, secondName, eMail, type);
            saveFromDataBase(newUser, password);
            if(newUser.getType() == 0){
                newUser.seteMail("admin");
            }
            return newUser;
        }
        log.info("Create new user error");
        return null;
    }

    /**
     * Checks for e-mail in the database
     * @param eMail
     * @return
     */
    private static boolean emailInDataBase(String eMail){
        DataBaseLogic dataBaseLogic = new DataBaseLogic();
        JDBCUserDAO userDAO = new JDBCUserDAO();
        userDAO.setConnection(dataBaseLogic.getConnection());
        if(!userDAO.searcheMail(eMail).equals("")){
            try {
                dataBaseLogic.closeDataBase();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
            return true;
        }
        try {
            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * save new user in database
     * @param newUser
     * @param password
     */
    private static void saveFromDataBase(User newUser, String password){
        try {
            DataBaseLogic dataBaseLogic = new DataBaseLogic();
            JDBCUserDAO userDAO = new JDBCUserDAO();

            userDAO.setConnection(dataBaseLogic.getConnection());
            userDAO.createTable();

            userDAO.insert(newUser, MainLogic.coding(password));
            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.info("New User created and added to the database");
    }

    /**
     * check the entrance to the program
     * @param eMail
     * @param password
     * @return
     */
    public static User entranceCheck(String eMail, String password){
       User user = null;
       DataBaseLogic dataBaseLogic = new DataBaseLogic();
       JDBCUserDAO userDAO = new JDBCUserDAO();
       userDAO.setConnection(dataBaseLogic.getConnection());
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
           dataBaseLogic.closeDataBase();
       } catch (SQLException e) {
           log.error(e.getMessage());
       }
        return user;
    }

    /**
     * at the end of the program, storing information about the user's uploads to the database
     * @param user
     */
    public static void saveLoadUser(User user){
       DataBaseLogic dataBaseLogic = new DataBaseLogic();
       JDBCUserDAO userDAO = new JDBCUserDAO();
       userDAO.setConnection(dataBaseLogic.getConnection());
       userDAO.saveLoad(user);
        try {
            dataBaseLogic.closeDataBase();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}


