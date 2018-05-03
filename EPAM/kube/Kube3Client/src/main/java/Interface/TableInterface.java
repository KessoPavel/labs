package Interface;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 *private String firstName = "";
 private String secondName = "";
 private String sex = "";
 private WorkPalace workPalace = new WorkPalace();
 private StudyPlace studyPlace = new StudyPlace();
 private String note = "";
 * Created by kesso on 17.04.17.
 */
public class TableInterface {
    private TableColumn ID;
    private TableColumn firstName;
    private TableColumn secondName;
    private TableColumn sex;
    private TableColumn workPlace;
    private TableColumn position;
    private TableColumn institution;
    private TableColumn startS;
    private TableColumn endS;

    private ArrayList<TableColumn> coll;

    public TableInterface(){

        ID = new TableColumn("ID");
        ID.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("ID")
        );

        firstName = new TableColumn("First name");
        firstName.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("firstName")
        );

        secondName = new TableColumn("Second name");
        secondName.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("secondName")
        );

        sex = new TableColumn("Sex");
        sex.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("sex")
        );

        workPlace = new TableColumn("Work Place");
        workPlace.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("workPlace")
        );

        position = new TableColumn("Position");
        position.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("position")
        );

        institution = new TableColumn("Institution");
        institution.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("inst")
        );

        startS = new TableColumn("Start");
        startS.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("start")
        );

        endS = new TableColumn("End");
        endS.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("end")
        );

        coll = new ArrayList<TableColumn>();
        coll.add(ID);
        coll.add(firstName);
        coll.add(secondName);
        coll.add(sex);
        coll.add(workPlace);
        coll.add(position);
        coll.add(institution);
        coll.add(startS);
        coll.add(endS);

    }

    public void setCols(TableView<TableItem> table){
        table.getColumns().clear();
        for(TableColumn tableColumn: coll){
            table.getColumns().add(tableColumn);
        }
    }

    public TableColumn getID() {
        return ID;
    }

    public void setID(TableColumn ID) {
        this.ID = ID;
    }
}
