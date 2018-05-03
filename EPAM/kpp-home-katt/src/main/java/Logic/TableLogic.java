package Logic;

import Documents.*;
import Interface.TableItem;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by kesso on 5.3.17.
 */
public class TableLogic {
    /**
     * Filling the table data with data from dataArray
     * @param dataArray
     * @param data
     */
    public static void setData(DataArray dataArray, ObservableList<TableItem> data){
        data.clear();
        for(Document a: dataArray.documents){
            data.add(new TableItem(a));
        }
        for(Book a: dataArray.books){
            data.add(new TableItem((Document)a));
        }
        for(Song a: dataArray.songs){
            data.add(new TableItem((Document)a));
        }
        for(Film a: dataArray.films){
            data.add(new TableItem((Document)a));
        }
    }

    /**
     * Filling the table data with data from documents
     * @param documents
     * @param data
     */
    public static void  setDocument(ArrayList<Document> documents, ObservableList<TableItem> data){
        data.clear();
        for(Document a: documents){
            data.add(new TableItem(a));
        }
    }

    /**
     * Filling the table data with data from books
     * @param books
     * @param data
     */
    public static void  setBook(ArrayList<Book> books, ObservableList<TableItem> data){
        data.clear();
        for(Book a: books){
            data.add(new TableItem(a));
        }
    }

    /**
     * Filling the table data with data from songs
     * @param songs
     * @param data
     */
    public static void  setSong(ArrayList<Song> songs, ObservableList<TableItem> data){
        data.clear();
        for(Song a: songs){
            data.add(new TableItem(a));
        }
    }

    /**
     * Filling the table data with data from films
     * @param films
     * @param data
     */
    public static void  setFilm(ArrayList<Film> films, ObservableList<TableItem> data){
        data.clear();
        for(Film a: films){
            data.add(new TableItem(a));
        }
    }
}
