package Rooms;

/**
 * Created by kesso on 14.05.17.
 */
public class Room {
    private int number;
    private String type;
    private int numberOfSeats;
    private String data;
    private String user;

    public Room(int number, String type, int numberOfSeats, String data, String user) {
        this.number = number;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.data = data;
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
