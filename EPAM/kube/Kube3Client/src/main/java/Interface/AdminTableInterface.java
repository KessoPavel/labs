package Interface;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * Created by kesso on 28.04.17.
 */
public class AdminTableInterface {
    private TableColumn eMail;
    private TableColumn type;

    private ArrayList<TableColumn> coll;

    public AdminTableInterface(){
        eMail = new TableColumn("eMail");
        eMail.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("eMail")
        );

        type = new TableColumn("Type");
        type.setCellValueFactory(
                new PropertyValueFactory<TableItem,String>("type")
        );

        coll = new ArrayList<TableColumn>();
        coll.add(eMail);
        coll.add(type);
    }

    public void setCols(TableView<TableItem> table){
        table.getColumns().clear();
        for(TableColumn tableColumn: coll){
            table.getColumns().add(tableColumn);
        }
    }

    public TableColumn geteMail() {
        return eMail;
    }

    public void seteMail(TableColumn eMail) {
        this.eMail = eMail;
    }

    public TableColumn getType() {
        return type;
    }

    public void setType(TableColumn type) {
        this.type = type;
    }
}
