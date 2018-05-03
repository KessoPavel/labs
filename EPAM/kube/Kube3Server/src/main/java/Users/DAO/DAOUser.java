package Users.DAO;

import Users.User;

import java.util.ArrayList;

/**
 * Created by kesso on 25.04.17.
 */
public interface DAOUser {
    public void insert(User user, String password);
    public String searcheMail(String eMail);
    public User getUser(String eMail);
    public ArrayList<User> select();
}
