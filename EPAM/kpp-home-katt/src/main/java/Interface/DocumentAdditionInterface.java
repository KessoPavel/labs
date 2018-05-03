package Interface;

import Documents.*;
import Logic.TableLogic;
import User.User;
import Logic.DataLogic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by kesso on 19.3.17.
 */

/**
 * class for working with document addition interface
 */
public class DocumentAdditionInterface {
    private User mainUser;
    private DataArray dataArray;
    private String dir;
    private TableView<TableItem> table;
    private TreeView<String> treeView;
    private ObservableList<TableItem> data;
    private Text bottomProgres;


    /**
     * constructor
     * @param mainUser
     * @param dataArray
     * @param dir
     * @param table
     * @param treeView
     * @param data
     * @param bottomProgres
     */
    public DocumentAdditionInterface(User mainUser, DataArray dataArray, String dir, TableView<TableItem> table,
                                     TreeView<String> treeView, ObservableList<TableItem> data, Text bottomProgres){
        this.mainUser = mainUser;
        this.dataArray = dataArray;
        this.dir = dir;
        this.table = table;
        this.treeView = treeView;
        this.data = data;
        this.bottomProgres = bottomProgres;
    }

    /**
     * Window for adding a new file
     * is made depending on the type of file to add
     * you can manually select the type of file to add
     * @param type default type
     * @param file file to add
     */
    public void createdNewDocument(final String type, final File file) {

        final Document document = new Document(file,mainUser.geteMail(),"");

        final Stage stage = new Stage();
        stage.setTitle("Home Katt|new document");
        final GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));


        startGridPane(gridPane, document);

        ObservableList<String> types = FXCollections.observableArrayList(
                "Document","Book","Song", "Film");
        final ChoiceBox<String> choiceBox = new ChoiceBox<String>(types);
        choiceBox.setLayoutX(10);
        choiceBox.setLayoutY(10);
        gridPane.add(choiceBox,0,4,2,1);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if (number.intValue() != -1) {
                            switch (t1.intValue()) {
                                case 0: {
                                    GridPane docPane = new GridPane();
                                    docPane.setHgap(15);
                                    docPane.setVgap(15);
                                    docPane.setPadding(new Insets(15));
                                    startGridPane(docPane, document);
                                    docPane.add(choiceBox, 0, 4, 2, 1);
                                    addDocumentGridPane(stage, docPane, file, document);
                                    Scene docScene = new Scene(docPane);
                                    stage.setScene(docScene);
                                    break;
                                }
                                case 1: {
                                    GridPane bookPane = new GridPane();
                                    bookPane.setHgap(15);
                                    bookPane.setVgap(15);
                                    bookPane.setPadding(new Insets(15));
                                    startGridPane(bookPane, document);
                                    bookPane.add(choiceBox, 0, 4, 2, 1);
                                    addBookGridPane(stage, bookPane, file);
                                    Scene docScene = new Scene(bookPane);
                                    stage.setScene(docScene);
                                    break;
                                }
                                case 2: {
                                    GridPane songPane = new GridPane();
                                    songPane.setHgap(15);
                                    songPane.setVgap(15);
                                    songPane.setPadding(new Insets(15));
                                    startGridPane(songPane, document);
                                    songPane.add(choiceBox, 0, 4, 2, 1);
                                    addSongGridPane(stage, songPane, file);
                                    Scene docScene = new Scene(songPane);
                                    stage.setScene(docScene);
                                    break;
                                }
                                case 3: {
                                    GridPane filmPane = new GridPane();
                                    filmPane.setHgap(15);
                                    filmPane.setVgap(15);
                                    filmPane.setPadding(new Insets(15));
                                    startGridPane(filmPane, document);
                                    filmPane.add(choiceBox, 0, 4, 2, 1);
                                    addFilmGridPane(stage, filmPane, file);
                                    Scene docScene = new Scene(filmPane);
                                    stage.setScene(docScene);
                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    }
                }
        );

        if(type.equals("Document")) {
            choiceBox.setValue("Document");
            addDocumentGridPane(stage, gridPane, file, document);
        }
        if(type.equals("Book")){
            choiceBox.setValue("Book");
            addBookGridPane(stage,gridPane,file);
        }
        if(type.equals("Song")){
            choiceBox.setValue("Song");
            addSongGridPane(stage, gridPane, file);
        }
        if(type.equals("Film")){
            choiceBox.setValue("Film");
            addFilmGridPane(stage, gridPane, file);
        }

        Scene scene= new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adding fields in the grid for the document
     * @param stage stage window for adding a new file
     * @param gridPane gridPane to adding fields
     * @param file file to adding
     * @param document document contains information about file
     */
    private void addDocumentGridPane(final Stage stage, GridPane gridPane, final File file, final Document document){
        Text description = new Text("description :");
        gridPane.add(description, 0, 5);
        description.setFont(new Font(14));

        final TextArea descriptionField = new TextArea(document.getDescription());
        descriptionField.setPrefWidth(300);
        gridPane.add(descriptionField, 0, 6, 2, 1);

        HBox hBox = new HBox();
        Button ok = new Button("OK");
        ok.setMinWidth(70);
        Button cancel = new Button("cancel");
        cancel.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ok,cancel);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                document.setDescription(descriptionField.getText());
                if(!DataLogic.addDocument(dataArray, document, file, dir, mainUser)){
                    message();
                }
                bottomProgres.setText("Loaded " + mainUser.getLoad()/(1024*1024) + "/10 Mb");
                stage.close();
                paintTable();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        gridPane.add(hBox,0,7,2,1);
    }

    /**
     * Adding fields in the grid for the book
     * @param stage stage window for adding a new file
     * @param gridPane gridPane to adding fields
     * @param file file to adding
     */
    public void addBookGridPane(final Stage stage, GridPane gridPane, final File file){
        Text bookName = new Text("book name :");
        gridPane.add(bookName, 0, 5);
        bookName.setFont(new Font(14));
        final TextField bookNameField = new TextField();
        gridPane.add(bookNameField, 1, 5);

        Text autor = new Text("autor :");
        gridPane.add(autor, 0, 6);
        autor.setFont(new Font(14));
        final TextField autorField = new TextField();
        gridPane.add(autorField, 1, 6);

        Text genre = new Text("genre :");
        gridPane.add(genre, 0, 7);
        genre.setFont(new Font(14));
        final TextField genreField = new TextField();
        gridPane.add(genreField, 1, 7);

        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 8);

        final TextArea descriptionField = new TextArea();
        descriptionField.setPrefWidth(300);
        gridPane.add(descriptionField, 0, 9, 2, 1);

        HBox hBox = new HBox();
        Button ok = new Button("OK");
        ok.setMinWidth(70);
        Button cancel = new Button("cancel");
        cancel.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ok,cancel);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Book book = new Book(file,mainUser.geteMail(),descriptionField.getText(),bookNameField.getText(),
                        autorField.getText(), genreField.getText());
                if(!DataLogic.addBook(dataArray,book, file, dir , mainUser)){
                    message();
                }
                bottomProgres.setText("Loaded " + mainUser.getLoad()/(1024*1024) + "/10 Mb");
                stage.close();
                paintTable();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        gridPane.add(hBox,0,10,2,1);
    }

    /**
     * Adding fields in the grid for the song
     * @param stage stage window for adding a new file
     * @param gridPane gridPane to adding fields
     * @param file file to adding
     */
    public void addSongGridPane(final Stage stage, GridPane gridPane, final File file){
        final Text songName = new Text("song name :");
        gridPane.add(songName, 0, 5);
        songName.setFont(new Font(14));
        final TextField songNameField = new TextField();
        gridPane.add(songNameField, 1, 5);

        Text executor = new Text("executor :");
        gridPane.add(executor, 0, 6);
        executor.setFont(new Font(14));
        final TextField executorField = new TextField();
        gridPane.add(executorField, 1, 6);

        Text album = new Text("album :");
        gridPane.add(album, 0, 7);
        album.setFont(new Font(14));
        final TextField albumField = new TextField();
        gridPane.add(albumField, 1, 7);

        Text year = new Text("year :");
        gridPane.add(year, 0, 8);
        year.setFont(new Font(14));
        final TextField yearField = new TextField();
        gridPane.add(yearField, 1, 8);

        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 9);

        final TextArea descriptionField = new TextArea();
        descriptionField.setPrefWidth(300);
        gridPane.add(descriptionField, 0, 10, 2, 1);

        HBox hBox = new HBox();
        Button ok = new Button("OK");
        ok.setMinWidth(70);
        Button cancel = new Button("cancel");
        cancel.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ok,cancel);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int year;
                if(yearField.getText().equals("")){
                    year = 0;
                }
                else{
                    year = Integer.parseInt(yearField.getText());
                }
                Song song = new Song(file, mainUser.geteMail(), descriptionField.getText(),
                        executorField.getText(),songNameField.getText(), albumField.getText(),
                        year);

                if(!DataLogic.addSong(dataArray, song, file, dir, mainUser)){
                    message();
                }
                bottomProgres.setText("Loaded " + mainUser.getLoad()/(1024*1024) + "/10 Mb");
                stage.close();
                paintTable();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        gridPane.add(hBox,0,11,2,1);

    }

    /**
     * Adding fields in the grid for the film
     * @param stage stage window for adding a new file
     * @param gridPane gridPane to adding fields
     * @param file file to adding
     */
    public void addFilmGridPane(final Stage stage, GridPane gridPane, final File file){
        Text filmName = new Text("film name :");
        gridPane.add(filmName, 0, 5);
        filmName.setFont(new Font(14));
        final TextField filmNameField = new TextField();
        gridPane.add(filmNameField, 1, 5);

        Text producer = new Text("producer :");
        gridPane.add(producer, 0, 6);
        producer.setFont(new Font(14));
        final TextField producerField = new TextField();
        gridPane.add(producerField, 1, 6);

        Text genre = new Text("genre :");
        gridPane.add(genre, 0, 7);
        genre.setFont(new Font(14));
        final TextField genreField = new TextField();
        gridPane.add(genreField, 1, 7);

        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 8);

        final TextArea descriptionField = new TextArea();
        descriptionField.setPrefWidth(300);
        gridPane.add(descriptionField, 0, 9, 2, 1);

        HBox hBox = new HBox();
        Button ok = new Button("OK");
        ok.setMinWidth(70);
        Button cancel = new Button("cancel");
        cancel.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(ok,cancel);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Film film = new Film(file,mainUser.geteMail(),descriptionField.getText(),filmNameField.getText(),
                        producerField.getText(), genreField.getText());
                if(!DataLogic.addFilm(dataArray, film, file, dir, mainUser)){
                    message();
                }
                bottomProgres.setText("Loaded " + mainUser.getLoad()/(1024*1024) + "/10 Mb");
                stage.close();
                paintTable();
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        gridPane.add(hBox,0,10,2,1);

    }

    /**
     * Adding fields in the grid
     * @param gridPane
     * @param document
     */
    public void startGridPane(GridPane gridPane, final Document document){
        Text fileName = new Text(document.getName());
        fileName.setFont(new Font(16));
        fileName.setWrappingWidth(250);

        gridPane.add(fileName, 0, 0, 2, 1);


        Text length = new Text("length :                ");
        length.setFont(new Font(14));
        gridPane.add(length, 0, 1);
        Text lengthValue = new Text(DataLogic.fileLength(document.getLength()));
        gridPane.add(lengthValue, 1, 1);

        Text resolution = new Text("resolution : ");
        gridPane.add(resolution, 0, 2);
        resolution.setFont(new Font(14));
        Text resolutionValue = new Text(document.getResolution());
        gridPane.add(resolutionValue, 1, 2);

        Text creator = new Text("creator :");
        gridPane.add(creator, 0, 3);
        creator.setFont(new Font(14));
        Text creatorValue = new Text(document.getCreator());
        gridPane.add(creatorValue, 1, 3);
    }

    /**
     * table update
     */
    private void paintTable(){
        switch (treeView.getSelectionModel().getSelectedIndex()) {
            case -1:
            case 0: {
                TableLogic.setData(dataArray, data);
                break;
            }
            case 1: {
                TableLogic.setDocument(dataArray.documents, data);
                break;
            }
            case 2: {
                TableLogic.setBook(dataArray.books, data);
                break;
            }
            case 3: {
                TableLogic.setSong(dataArray.songs, data);
                break;
            }
            case 4: {
                TableLogic.setFilm(dataArray.films, data);
                break;
            }
        }
        table.setItems(data);
    }

    /**
     * Message about the end of daily limit
     */
    private void message(){
        final Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);

        VBox vBox = new VBox();
        Text mess = new Text("The file can not be added.\n" +
                "When uploading a file, the download limit will be exceeded.");
        Button button = new Button("Ok");
        vBox.getChildren().addAll(mess,button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        stage.setScene(new Scene(vBox));
        stage.show();
    }

}
