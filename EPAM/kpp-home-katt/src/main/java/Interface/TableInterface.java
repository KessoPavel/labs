package Interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kesso on 19.3.17.
 */

/**
 * interface for working whits table
 */
public class TableInterface {

    private TableColumn nameCol;
    private TableColumn sizeCol;
    private TableColumn typeCol;
    private TableColumn creatorCol;
    private Collection<TableColumn> documentCollection;
    //book column
    private TableColumn bookNameCol;
    private TableColumn autorCol;
    private TableColumn genreCol;
    private ArrayList<TableColumn> bookCollection;
    //song column
    private TableColumn songNameCol;
    private TableColumn executorCol;
    private TableColumn albumCol;
    private TableColumn yearCol;
    private ArrayList<TableColumn> songCollection;
    //film column
    private TableColumn filmNameCol;
    private TableColumn producerCol;
    private TableColumn filmGenreCol;
    private ArrayList<TableColumn> filmCollection;

    public TableInterface(){
        nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(400);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("name"));

        sizeCol = new TableColumn("Size");
        sizeCol.setPrefWidth(70);
        sizeCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("size"));

        typeCol = new TableColumn("Type");
        typeCol.setPrefWidth(55);
        typeCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("type"));

        creatorCol = new TableColumn("Creator");
        creatorCol.setPrefWidth(150);
        creatorCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("creator"));

        documentCollection = new ArrayList<TableColumn>();
        documentCollection.add(nameCol);
        documentCollection.add(sizeCol);
        documentCollection.add(typeCol);

        bookNameCol = new TableColumn("Book name");
        bookNameCol.setPrefWidth(250);
        bookNameCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("bookName"));

        autorCol = new TableColumn("Autor");
        autorCol.setPrefWidth(150);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("autor"));

        genreCol = new TableColumn("Genre");
        genreCol.setPrefWidth(150);
        genreCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("genre"));

        bookCollection = new ArrayList<TableColumn>();
        bookCollection.add(nameCol);
        bookCollection.add(bookNameCol);
        bookCollection.add(autorCol);
        bookCollection.add(genreCol);

        songNameCol = new TableColumn("Song name");
        songNameCol.setPrefWidth(250);
        songNameCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("songName"));

        executorCol = new TableColumn("Executor");
        executorCol.setPrefWidth(150);
        executorCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("executor"));

        albumCol = new TableColumn("Album");
        albumCol.setPrefWidth(150);
        albumCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("album"));

        yearCol = new TableColumn("Year");
        yearCol.setPrefWidth(150);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("year"));

        songCollection = new ArrayList<TableColumn>();
        songCollection.add(nameCol);
        songCollection.add(songNameCol);
        songCollection.add(executorCol);
        songCollection.add(albumCol);
        songCollection.add(yearCol);

        filmNameCol = new TableColumn("Film name");
        filmNameCol.setPrefWidth(250);
        filmNameCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("filmName"));

        producerCol = new TableColumn("Producer");
        producerCol.setPrefWidth(150);
        producerCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("producer"));

        filmGenreCol = new TableColumn("Genre");
        filmGenreCol.setPrefWidth(150);
        filmGenreCol.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("filmGenre"));

        filmCollection = new ArrayList<TableColumn>();
        filmCollection.add(nameCol);
        filmCollection.add(filmNameCol);
        filmCollection.add(producerCol);
        filmCollection.add(filmGenreCol);
    }

    /**
     * set in table columns for document
     * @param table table
     * @param adminFlag determines which columns will be installed
     */
    public void setDocumentCols(TableView<TableItem> table, boolean adminFlag){
        table.getColumns().clear();
        for(TableColumn tableColumn : documentCollection){
            table.getColumns().add(tableColumn);
        }
        if(adminFlag)
            table.getColumns().add(creatorCol);
    }

    /**
     * set in table columns for book
     * @param table table
     * @param adminFlag determines which columns will be installed
     */
    public void setBookCols(TableView<TableItem> table, boolean adminFlag){
        table.getColumns().clear();
        for(TableColumn tableColumn : bookCollection){
            table.getColumns().add(tableColumn);
        }
        if(adminFlag){
            table.getColumns().add(creatorCol);
        }
    }

    /**
     * set in table columns for song
     * @param table table
     * @param adminFlag determines which columns will be installed
     */
    public void setSongCols(TableView<TableItem> table, boolean adminFlag){
        table.getColumns().clear();
        for(TableColumn tableColumn : songCollection){
            table.getColumns().add(tableColumn);
        }
        if(adminFlag){
            table.getColumns().add(creatorCol);
        }
    }

    /**
     * set in table columns for film
     * @param table table
     * @param adminFlag determines which columns will be installed
     */
    public void setFilmCols(TableView<TableItem> table, boolean adminFlag){
        table.getColumns().clear();
        for(TableColumn tableColumn : filmCollection){
            table.getColumns().add(tableColumn);
        }
        if(adminFlag){
            table.getColumns().add(creatorCol);
        }
    }

    /**
     * set columns for document
     * @param flags name, size, type, creator
     */
    public void updateDocumentCol(boolean[] flags){
        documentCollection.clear();
        if(flags[0]){
            documentCollection.add(nameCol);
        }
        if(flags[1]){
            documentCollection.add(sizeCol);
        }
        if(flags[2]){
            documentCollection.add(typeCol);
        }
        if(flags[3]){
            documentCollection.add(creatorCol);
        }
    }

    /**
     * set columns for book
     * @param flags name, size, type, bookName, autor, genre, creator
     */
    public void updateBookCol(boolean[] flags){
        bookCollection.clear();
        if(flags[0]){
            bookCollection.add(nameCol);
        }
        if(flags[1]){
            bookCollection.add(sizeCol);
        }
        if(flags[2]){
            bookCollection.add(typeCol);
        }
        if(flags[3]){
            bookCollection.add(bookNameCol);
        }
        if(flags[4]){
            bookCollection.add(autorCol);
        }
        if(flags[5]){
            bookCollection.add(genreCol);
        }
        if(flags[6]){
            bookCollection.add(creatorCol);
        }
    }

    /**
     * set columns for book
     * @param flags name, size, type, songName, executor, album, yaer, creator
     */
    public void updateSongCol(boolean[] flags){
        songCollection.clear();
        if(flags[0]){
            songCollection.add(nameCol);
        }
        if(flags[1]){
            songCollection.add(sizeCol);
        }
        if(flags[2]){
            songCollection.add(typeCol);
        }
        if(flags[3]){
            songCollection.add(songNameCol);
        }
        if(flags[4]){
            songCollection.add(executorCol);
        }
        if(flags[5]){
            songCollection.add(albumCol);
        }
        if(flags[6]){
            songCollection.add(yearCol);
        }
        if(flags[7]){
            songCollection.add(creatorCol);
        }
    }

    /**
     * set columns for book
     * @param flags name, size, type, filmName, producer, filmGenre, creator
     */
    public void updateFilmCol(boolean[] flags){
        filmCollection.clear();
        if(flags[0]){
            filmCollection.add(nameCol);
        }
        if(flags[1]){
            filmCollection.add(sizeCol);
        }
        if(flags[2]){
            filmCollection.add(typeCol);
        }
        if(flags[3]){
            filmCollection.add(filmNameCol);
        }
        if(flags[4]){
            filmCollection.add(producerCol);
        }
        if(flags[5]) {
            filmCollection.add(filmGenreCol);
        }
        if(flags[6]){
            filmCollection.add(creatorCol);
        }
    }

    /**
     *
     * @return flags for document
     */
    public boolean[] getDocumentFlags(){
        boolean[] flags = new boolean[4];

        flags[0] = documentCollection.contains(nameCol);
        flags[1] = documentCollection.contains(sizeCol);
        flags[2] = documentCollection.contains(typeCol);
        flags[3] = documentCollection.contains(creatorCol);
        return flags;
    }

    /**
     *
     * @return flags for book
     */
    public boolean[] getBookFlags(){
        boolean[] flags = new boolean[7];
        flags[0] = bookCollection.contains(nameCol);
        flags[1] = bookCollection.contains(sizeCol);
        flags[2] = bookCollection.contains(typeCol);
        flags[3] = bookCollection.contains(bookNameCol);
        flags[4] = bookCollection.contains(autorCol);
        flags[5] = bookCollection.contains(genreCol);
        flags[6] = bookCollection.contains(creatorCol);
        return flags;
    }

    /**
     *
     * @return flag for song
     */
    public boolean[] getSongFlags(){
        boolean[] flags = new boolean[8];
        flags[0] = songCollection.contains(nameCol);
        flags[1] = songCollection.contains(sizeCol);
        flags[2] = songCollection.contains(typeCol);
        flags[3] = songCollection.contains(songNameCol);
        flags[4] = songCollection.contains(executorCol);
        flags[5] = songCollection.contains(albumCol);
        flags[6] = songCollection.contains(yearCol);
        flags[7] = songCollection.contains(creatorCol);
        return flags;
    }

    /**
     *
     * @return flag for film
     */
    public boolean[] getFilmFlags(){
        boolean[] flags = new boolean[7];
        flags[0] = filmCollection.contains(nameCol);
        flags[1] = filmCollection.contains(sizeCol);
        flags[2] = filmCollection.contains(typeCol);
        flags[3] = filmCollection.contains(filmNameCol);
        flags[4] = filmCollection.contains(producerCol);
        flags[5] = filmCollection.contains(filmGenreCol);
        flags[6] = filmCollection.contains(creatorCol);
        return flags;
    }

    /**
     * window for chenge column flahs
     * @param table table
     * @param flag mainUser is admin&
     * @param n selected in tree
     */
    public void windowUpdateColumns(final TableView<TableItem> table, final boolean flag, final int n){
        final Stage stage = new Stage();
        stage.setTitle("Home Katt|Table view");
        final boolean[] documentFlags = this.getDocumentFlags();
        final boolean[] bookFlags = this.getBookFlags();
        final boolean[] songFlags = this.getSongFlags();
        final boolean[] filmFlags = this.getFilmFlags();

        TabPane tabPane = new TabPane();
        Tab documentTab = new Tab("Document");
        Tab bookTab = new Tab("Book");
        Tab songTab = new Tab("Song");
        Tab filmTab = new Tab("Film");

        GridPane documentGridPane = new GridPane();
        documentGridPane.setHgap(15);
        documentGridPane.setVgap(15);
        documentGridPane.setPadding(new Insets(15));

        Text name = new Text("Name");
        name.setFont(new Font(14));
        final CheckBox nameCB = new CheckBox();
        nameCB.setSelected(documentFlags[0]);
        documentGridPane.add(name, 0, 0);
        documentGridPane.add(nameCB, 1,0);

        Text size = new Text("Size");
        size.setFont(new Font(14));
        final CheckBox sizeCB = new CheckBox();
        sizeCB.setSelected(documentFlags[1]);
        documentGridPane.add(size,0,1);
        documentGridPane.add(sizeCB,1,1);

        Text type = new Text("Type");
        type.setFont(new Font(14));
        final CheckBox typCB = new CheckBox();
        typCB.setSelected(documentFlags[2]);
        documentGridPane.add(type, 0,2);
        documentGridPane.add(typCB, 1, 2);

        documentTab.setContent(documentGridPane);
        documentTab.setClosable(false);


        GridPane bookGridPane = new GridPane();
        bookGridPane.setHgap(15);
        bookGridPane.setVgap(15);

        bookGridPane.setPadding(new Insets(15));
        Text bname = new Text("Name");
        bname.setFont(new Font(14));
        final CheckBox bnameCB = new CheckBox();
        bnameCB.setSelected(bookFlags[0]);
        bookGridPane.add(bname, 0, 0);
        bookGridPane.add(bnameCB, 1,0);
        Text bsize = new Text("Size");
        bsize.setFont(new Font(14));
        final CheckBox bsizeCB = new CheckBox();
        bsizeCB.setSelected(bookFlags[1]);
        bookGridPane.add(bsize,0,1);
        bookGridPane.add(bsizeCB,1,1);
        Text btype = new Text("Type");
        btype.setFont(new Font(14));
        final CheckBox btypCB = new CheckBox();
        btypCB.setSelected(bookFlags[2]);
        bookGridPane.add(btype, 0,2);
        bookGridPane.add(btypCB, 1, 2);

        Text bookName = new Text("Book name");
        bookName.setFont(new Font(14));
        final CheckBox BookNameCB = new CheckBox();
        BookNameCB.setSelected(bookFlags[3]);
        bookGridPane.add(bookName, 0,3);
        bookGridPane.add(BookNameCB, 1, 3);

        Text autor = new Text("Autor");
        autor.setFont(new Font(14));
        final CheckBox autorCB = new CheckBox();
        autorCB.setSelected(bookFlags[4]);
        bookGridPane.add(autor, 0,4);
        bookGridPane.add(autorCB, 1, 4);

        Text genre = new Text("Genre");
        genre.setFont(new Font(14));
        final CheckBox genreCB = new CheckBox();
        genreCB.setSelected(bookFlags[5]);
        bookGridPane.add(genre, 0,5);
        bookGridPane.add(genreCB, 1, 5);

        bookTab.setContent(bookGridPane);
        bookTab.setClosable(false);

        GridPane songGridPane = new GridPane();
        songGridPane.setHgap(15);
        songGridPane.setVgap(15);

        songGridPane.setPadding(new Insets(15));
        final Text sname = new Text("Name");
        sname.setFont(new Font(14));
        final CheckBox snameCB = new CheckBox();
        snameCB.setSelected(songFlags[0]);
        songGridPane.add(sname, 0, 0);
        songGridPane.add(snameCB, 1,0);
        Text ssize = new Text("Size");
        ssize.setFont(new Font(14));
        final CheckBox ssizeCB = new CheckBox();
        ssizeCB.setSelected(songFlags[1]);
        songGridPane.add(ssize,0,1);
        songGridPane.add(ssizeCB,1,1);
        Text stype = new Text("Type");
        stype.setFont(new Font(14));
        final CheckBox stypCB = new CheckBox();
        stypCB.setSelected(songFlags[2]);
        songGridPane.add(stype, 0,2);
        songGridPane.add(stypCB, 1, 2);

        Text songName = new Text("Song name");
        songName.setFont(new Font(14));
        final CheckBox songNameCB = new CheckBox();
        songNameCB.setSelected(songFlags[3]);
        songGridPane.add(songName, 0,3);
        songGridPane.add(songNameCB, 1, 3);

        Text executor = new Text("Executor");
        executor.setFont(new Font(14));
        final CheckBox executorCB = new CheckBox();
        executorCB.setSelected(songFlags[4]);
        songGridPane.add(executor, 0,4);
        songGridPane.add(executorCB, 1, 4);

        Text album = new Text("Album");
        album.setFont(new Font(14));
        final CheckBox albunCB= new CheckBox();
        albunCB.setSelected(songFlags[5]);
        songGridPane.add(album, 0,5);
        songGridPane.add(albunCB, 1, 5);

        Text year = new Text("Year");
        year.setFont(new Font(14));
        final CheckBox yearCB= new CheckBox();
        yearCB.setSelected(songFlags[6]);
        songGridPane.add(year, 0,6);
        songGridPane.add(yearCB, 1, 6);

        songTab.setContent(songGridPane);
        songTab.setClosable(false);

        GridPane filmGridPane = new GridPane();
        filmGridPane.setHgap(15);
        filmGridPane.setVgap(15);

        filmGridPane.setPadding(new Insets(15));
        Text fname = new Text("Name");
        fname.setFont(new Font(14));
        final CheckBox fnameCB = new CheckBox();
        fnameCB.setSelected(filmFlags[0]);
        filmGridPane.add(fname, 0, 0);
        filmGridPane.add(fnameCB, 1,0);
        Text fsize = new Text("Size");
        fsize.setFont(new Font(14));
        final CheckBox fsizeCB = new CheckBox();
        fsizeCB.setSelected(filmFlags[1]);
        filmGridPane.add(fsize,0,1);
        filmGridPane.add(fsizeCB,1,1);
        Text ftype = new Text("Type");
        ftype.setFont(new Font(14));
        final CheckBox ftypCB = new CheckBox();
        ftypCB.setSelected(filmFlags[2]);
        filmGridPane.add(ftype, 0,2);
        filmGridPane.add(ftypCB, 1, 2);

        Text filmName = new Text("Film name");
        filmName.setFont(new Font(14));
        final CheckBox filmNameCB = new CheckBox();
        filmNameCB.setSelected(filmFlags[3]);
        filmGridPane.add(filmName, 0,3);
        filmGridPane.add(filmNameCB, 1, 3);

        Text producer = new Text("Producer");
        producer.setFont(new Font(14));
        final CheckBox producerCB = new CheckBox();
        producerCB.setSelected(filmFlags[4]);
        filmGridPane.add(producer, 0,4);
        filmGridPane.add(producerCB, 1, 4);

        Text filmGenre = new Text("Genre");
        filmGenre.setFont(new Font(14));
        final CheckBox filmGenreCB = new CheckBox();
        filmGenreCB.setSelected(filmFlags[5]);
        filmGridPane.add(filmGenre, 0,5);
        filmGridPane.add(filmGenreCB, 1, 5);

        filmTab.setContent(filmGridPane);
        filmTab.setClosable(false);
        tabPane.getTabs().addAll(documentTab,bookTab,songTab,filmTab);

        HBox hBox = new HBox();
        Button save = new Button("Save");
        save.setPrefWidth(70);

        Button cancel = new Button("Cancel");
        cancel.setPrefWidth(70);

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateDocumentCol(new boolean[]{nameCB.isSelected(),sizeCB.isSelected(),
                        typCB.isSelected(),documentFlags[3]});
                updateBookCol(new boolean[]{bnameCB.isSelected(),bsizeCB.isSelected(),btypCB.isSelected(),
                        BookNameCB.isSelected(), autorCB.isSelected(), genreCB.isSelected(), bookFlags[6]});
                updateSongCol(new boolean[]{snameCB.isSelected(),ssizeCB.isSelected(),stypCB.isSelected(),
                        songNameCB.isSelected(), executorCB.isSelected(), albunCB.isSelected(), yearCB.isSelected(),
                        songFlags[7]});
                updateFilmCol(new boolean[]{fnameCB.isSelected(),fsizeCB.isSelected(),ftypCB.isSelected(),
                        filmNameCB.isSelected(), producerCB.isSelected(),filmGenreCB.isSelected(),filmFlags[6]});
                switch (n){
                    case 1: {
                        setDocumentCols(table,flag);
                        break;
                    }
                    case 2:{
                        setBookCols(table, flag);
                        break;
                    }
                    case 3:{
                        setSongCols(table, flag);
                        break;
                    }
                    case 4:{
                        setFilmCols(table, flag);
                        break;
                    }
                    default: break;
                }
                stage.close();
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        hBox.getChildren().addAll(save,cancel);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tabPane,hBox);


        stage.setScene(new Scene((vBox)));
        stage.show();
    }
}