package Data;

import java.io.Serializable;

/**
 * Created by kesso on 23.04.17.
 */
public class Person implements Serializable{
    private String firstName = "";
    private String secondName = "";
    private String sex = "";
    private WorkPalace workPalace = new WorkPalace();
    private StudyPlace studyPlace = new StudyPlace();
    private String note = "";

    public Person(){
        this.workPalace = new WorkPalace();
        this.studyPlace = new StudyPlace();
    }

    public Person(String firstName, String secondName, String sex, WorkPalace workPalace, StudyPlace studyPlace, String note) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.sex = sex;
        this.workPalace = workPalace;
        this.studyPlace = studyPlace;
        this.note = note;
    }

    public boolean equals(Person person){
        if(!person.firstName.equals("")){
            if(!person.firstName.equals(this.firstName))
                return false;
        }
        if(!person.secondName.equals("")){
            if(!person.secondName.equals(this.secondName))
                return false;
        }
        if(!person.sex.equals("")){
            if(!person.sex.equals(this.sex))
                return false;
        }
        if(!this.workPalace.equals(person.workPalace))//error
            return false;
        if(!this.studyPlace.equals(person.studyPlace))
            return false;
        return true;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public WorkPalace getWorkPalace() {
        return workPalace;
    }

    public void setWorkPalace(WorkPalace workPalace) {
        this.workPalace = workPalace;
    }

    public StudyPlace getStudyPlace() {
        return studyPlace;
    }

    public void setStudyPlace(StudyPlace studyPlace) {
        this.studyPlace = studyPlace;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
