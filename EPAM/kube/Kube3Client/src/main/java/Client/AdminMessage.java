package Client;

import Users.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kesso on 25.04.17.
 */
public class AdminMessage implements Serializable {
    public String message;
    public ArrayList<User> users;
    public String password;
}
