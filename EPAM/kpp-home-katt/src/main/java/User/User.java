package User;

import java.io.File;

/**
 * Created by kesso on 4.3.17.
 */
public class User {
    private String firstName;
    private String secondName;
    private String eMail;
    private long load;
    private int type;

    public User(String firstName, String secondName, String eMail, int type) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.eMail = eMail;
        this.type = type;
    }

    public boolean checkLoad(File file){
        return (((this.load + file.length()) < 10485760) || type == 0);
    }

    public void addLoad(File file){
        this.load += file.length();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public long getLoad() {
        return load;
    }

    public void setLoad(long load) {
        this.load = load;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString(){
        return this.firstName + " " + this.secondName;
    }
}
