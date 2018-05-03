package Data;

import java.io.Serializable;

/**
 * Created by kesso on 23.04.17.
 */
public class WorkPalace implements Serializable{
    private String workPlace = "";
    private String position = "";

    public WorkPalace(){}

    public WorkPalace(String workPlace, String position){
        this.workPlace = workPlace;
        this.position = position;
    }

    public boolean equals(WorkPalace wp){
        if(!wp.workPlace.equals("")){
            if(!wp.workPlace.equals(this.workPlace))
                return false;
        }
        if(!wp.position.equals("")){
            if(!wp.position.equals(this.position))
                return false;
        }
        return true;
    }

    public boolean isEmpty(){
        if(!this.workPlace.equals(""))
            return false;
        if(!this.position.equals(""))
            return false;
        return true;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
