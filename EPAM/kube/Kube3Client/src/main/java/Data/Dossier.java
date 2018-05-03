package Data;

import java.io.Serializable;

/**
 * Created by kesso on 23.04.17.
 */
public class Dossier implements Serializable {
    private int ID;
    private Person person;
    private String dataOfCreation = "";
    private String dataOfChange = "";

    public Dossier(){
        this.person = new Person();
    }

    public Dossier(int id, Person person, String dataOfCreation, String dataOfChange) {
        ID = id;
        this.person = person;
        this.dataOfCreation = dataOfCreation;
        this.dataOfChange = dataOfChange;
    }

    public boolean equals(Dossier dossier){
        if(dossier.ID != -1){
            if(dossier.ID != this.ID)
                return false;
        }
        if(!dossier.person.equals(this.person))
            return false;
        if(!dossier.dataOfCreation.equals("")){
            if(!dossier.dataOfCreation.equals(this.dataOfCreation))
                return false;
        }
        if(!dossier.dataOfChange.equals("")){
            if(!dossier.dataOfChange.equals(this.dataOfChange))
                return  false;
        }
        return true;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDataOfCreation() {
        return dataOfCreation;
    }

    public void setDataOfCreation(String dataOfCreation) {
        this.dataOfCreation = dataOfCreation;
    }

    public String getDataOfChange() {
        return dataOfChange;
    }

    public void setDataOfChange(String dataOfChange) {
        this.dataOfChange = dataOfChange;
    }
}
