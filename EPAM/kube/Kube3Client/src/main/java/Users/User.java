package Users;

import java.io.Serializable;

/**
 * Created by kesso on 25.04.17.
 */
public class User implements Serializable{
    private String eMail;
    private int type;


    public User(String eMail, int type) {
        this.eMail = eMail;
        this.type = type;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
