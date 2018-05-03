package Logic;


import Interface.TableItem;
import Data.*;
import Users.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by kesso on 18.04.17.
 */
public class TableLogic {
    public static void  setData(ArrayList<Dossier> dossiers, ObservableList<TableItem> data){
        data.clear();
        for(Dossier a: dossiers){
            data.add(new TableItem(a));
        }
    }
    public static void  setAData(ArrayList<User> users, ObservableList<TableItem> data){
        data.clear();
        for(User a: users){
            data.add(new TableItem(a));
        }
    }
}
