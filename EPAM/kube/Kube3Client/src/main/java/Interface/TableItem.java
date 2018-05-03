package Interface;

import Data.*;
import Users.User;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by kesso on 17.04.17.
 */
public class TableItem {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty secondName;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty workPlace;
    private final SimpleStringProperty position;
    private final SimpleStringProperty inst;
    private final SimpleStringProperty start;
    private final SimpleStringProperty end;

    private final SimpleStringProperty eMail;
    private final SimpleStringProperty type;


    public TableItem(Dossier dossier){
        this.ID = new SimpleStringProperty(Integer.toString(dossier.getID()));
        this.firstName = new SimpleStringProperty(dossier.getPerson().getFirstName());
        this.secondName = new SimpleStringProperty(dossier.getPerson().getSecondName());
        this.sex = new SimpleStringProperty(dossier.getPerson().getSex());
        this.workPlace = new SimpleStringProperty(dossier.getPerson().getWorkPalace().getWorkPlace());
        this.position = new SimpleStringProperty(dossier.getPerson().getWorkPalace().getPosition());
        this.inst = new SimpleStringProperty(dossier.getPerson().getStudyPlace().getEducationalInstitution());
        this.start = new SimpleStringProperty(dossier.getPerson().getStudyPlace().getYearOfReceipt());
        this.end = new SimpleStringProperty(dossier.getPerson().getStudyPlace().getYearOfEnding());
        this.eMail = null;
        this.type = null;
    }
    public TableItem(User user){
        eMail = new SimpleStringProperty(user.geteMail());
        type = new SimpleStringProperty(Integer.toString(user.getType()));

        ID = null;
        firstName = null;
        secondName = null;
        sex = null;
        workPlace = null;
        position = null;
        inst = null;
        start = null;
        end = null;
    }

    public String geteMail() {
        return eMail.get();
    }

    public SimpleStringProperty eMailProperty() {
        return eMail;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getSecondName() {
        return secondName.get();
    }

    public SimpleStringProperty secondNameProperty() {
        return secondName;
    }

    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public String getWorkPlace() {
        return workPlace.get();
    }

    public SimpleStringProperty workPlaceProperty() {
        return workPlace;
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public String getInst() {
        return inst.get();
    }

    public SimpleStringProperty instProperty() {
        return inst;
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }
}
