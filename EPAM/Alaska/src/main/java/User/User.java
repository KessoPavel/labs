package User;

import java.util.ArrayList;

/**
 * Created by kesso on 14.05.17.
 */
public class User {
    private String eMail;
    private String firstName;
    private String secondName;
    private ArrayList<Integer> rooms;

    public User(String eMail, String firstName, String secondName, ArrayList<Integer> rooms) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.secondName = secondName;
        this.rooms = rooms;
    }

    public User(String eMail, String firstName, String secondName, String rooms) {
        this.eMail = eMail;
        this.firstName = firstName;
        this.secondName = secondName;

        this.rooms = new ArrayList<Integer>();
        try {
            if (!rooms.equals(" ")) {
                try {
                    for (String room : rooms.split(" ")) {
                        this.rooms.add(new Integer(room));
                    }
                } catch (NumberFormatException ex) {
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
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

    public ArrayList<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Integer> rooms) {
        this.rooms = rooms;
    }
}
