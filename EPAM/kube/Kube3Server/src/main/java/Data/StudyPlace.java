package Data;

import java.io.Serializable;

/**
 * Created by kesso on 23.04.17.
 */
public class StudyPlace implements Serializable {
    private String educationalInstitution = "";
    private String yearOfReceipt = "";
    private String yearOfEnding = "";

    public StudyPlace(){}

    public StudyPlace(String educationalInstitution, String yearOfReceipt, String yearOfEnding) {
        this.educationalInstitution = educationalInstitution;
        this.yearOfReceipt = yearOfReceipt;
        this.yearOfEnding = yearOfEnding;
    }

    public boolean equals(StudyPlace sp){
        if(!sp.educationalInstitution.equals("")){
            if(!sp.educationalInstitution.equals(this.educationalInstitution))
                return false;
        }
        if(!sp.yearOfReceipt.equals("")){
            if(!sp.getYearOfReceipt().equals(this.yearOfReceipt))
                return false;
        }
        if(!sp.yearOfEnding.equals("")){
            if(!sp.yearOfEnding.equals(this.yearOfEnding))
                return false;
        }

        return true;
    }

    public boolean isEmpty(){
        if(!this.educationalInstitution.equals(""))
            return false;
        if(!this.yearOfEnding.equals(""))
            return false;
        if(!this.yearOfReceipt.equals(""))
            return false;
        return true;
    }

    public String getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(String educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public String getYearOfReceipt() {
        return yearOfReceipt;
    }

    public void setYearOfReceipt(String yearOfReceipt) {
        this.yearOfReceipt = yearOfReceipt;
    }

    public String getYearOfEnding() {
        return yearOfEnding;
    }

    public void setYearOfEnding(String yearOfEnding) {
        this.yearOfEnding = yearOfEnding;
    }
}
