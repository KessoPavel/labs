package Interface;

import DAO.JDBCDocumentDAO;
import Documents.*;
import Logic.DataLogic;
import Logic.MainLogic;
import Logic.TableLogic;
import Logic.UserLogic;
import User.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;


/**
 * Created by kesso on 4.3.17.
 */
public class Main extends Application {
    private static final Logger log = Logger.getLogger(Main.class);
    private User mainUser;
    private String dir;
    private DataArray dataArray;
    private ObservableList<TableItem> data = null;
    TableView<TableItem> table = null;
    TreeView<String> treeView = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        dir = MainLogic.startWork();
        if (dir == null) {
            firstStart();
        } else {
            primaryStage.setTitle("HomeKat | SingIn");
            SingIn(primaryStage);
            primaryStage.show();
        }
    }

    /**
     * login window
     * sets naimUser
     * @param stage
     */
    private void SingIn(final Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label eMail = new Label("E-mail:");
        grid.add(eMail, 0, 1);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        userTextField.setMinWidth(230);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        final PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button guest = new Button("Guest");
        guest.setMinWidth(70);

        Button uBtn = new Button("Sing up");

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(guest);
        hbBtn.getChildren().add(btn);
        hbBtn.getChildren().add(uBtn);
        grid.add(hbBtn, 1, 4);


        final Text errorMassage = new Text();
        errorMassage.setFill(Color.RED);
        grid.add(errorMassage, 0, 6, 2, 1);

        guest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainUser = new User("Guest","","guest",2);
                stage.close();
                mainWindow();
            }
        });

        uBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                SingUp(stage);
            }
        });
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (!userTextField.getText().isEmpty() && !pwBox.getText().isEmpty()) {
                    mainUser = UserLogic.entranceCheck(userTextField.getText(), pwBox.getText());
                    if (mainUser != null) {
                        stage.close();
                        mainWindow();
                    } else {
                        errorMassage.setText("Email or/and password is not entered correctly");
                    }
                } else {
                    errorMassage.setText("Please fill in all fields");
                }
            }
        });
        Scene scene = new Scene(grid, 360, 200);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * registration window
     * sets mainUser
     * @param pStage
     */
    private void SingUp(final Stage pStage) {
        final Stage stage = new Stage();
        stage.setTitle("HomeKat | SingUp");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Sing Up");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        final TextField firstNameField = new TextField();
        grid.add(firstNameField, 1, 1);

        final Label firstName = new Label("First name:");
        grid.add(firstName, 0, 1);

        final TextField secondNameField = new TextField();
        grid.add(secondNameField, 1, 2);

        final Label secondName = new Label("Second name:");
        grid.add(secondName, 0, 2);

        final TextField eMailField = new TextField();
        grid.add(eMailField, 1, 3);

        final Label eMail = new Label("e-mail:");
        grid.add(eMail, 0, 3);

        final PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 4);

        final Label password = new Label("Password:");
        grid.add(password, 0, 4);

        final PasswordField rPasswordField = new PasswordField();
        grid.add(rPasswordField, 1, 5);

        final Label rPassword = new Label("Repeat password:");
        grid.add(rPassword, 0, 5);

        HBox hBoxButton = new HBox();
        hBoxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxButton.setSpacing(10);
        Button okButton = new Button("Ok");
        okButton.setMinWidth(70);
        hBoxButton.getChildren().add(okButton);
        Button backButton = new Button("Back");
        backButton.setMinWidth(70);
        hBoxButton.getChildren().add(backButton);
        grid.add(hBoxButton, 1, 6);

        final Text errorMessage = new Text();
        grid.add(errorMessage, 0, 7, 2, 1);
        errorMessage.setFill(Color.RED);


        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!firstNameField.getText().isEmpty() && !secondNameField.getText().isEmpty() &&
                        !eMailField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                        !rPasswordField.getText().isEmpty()) {
                    if (passwordField.getText().equals(rPasswordField.getText())) {
                        mainUser = UserLogic.creatingNewUser(firstNameField.getText(), secondNameField.getText(),
                                eMailField.getText(), passwordField.getText(), 1);
                        stage.close();
                        mainWindow();
                    } else {
                        errorMessage.setText("Passwords must be the same");
                    }
                } else {
                    errorMessage.setText("Please fill in all fields");
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                pStage.show();
            }
        });

        Scene scene = new Scene(grid, 350, 350);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * first call window
     * set dir, creted admin, created database
     */
    private void firstStart() {
        final Stage stage = new Stage();
        stage.setTitle("HomeKat | First start");
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Text startMessage = new Text("Hello");
        startMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(startMessage, 0, 0, 2, 1);

        final Text infMessage = new Text("This is the first launch of the program.\n" +
                "First you need to register the administrator.");
        infMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(infMessage, 0, 1, 2, 1);

        final Text firstName = new Text("First name:");
        grid.add(firstName, 0, 2);
        final TextField firstNameField = new TextField();
        grid.add(firstNameField, 1, 2);

        Text secondName = new Text("Second name:");
        grid.add(secondName, 0, 3);
        final TextField secondNameField = new TextField();
        grid.add(secondNameField, 1, 3);

        Text eMail = new Text("e-Mail:");
        grid.add(eMail, 0, 4);
        final TextField eMailField = new TextField();
        grid.add(eMailField, 1, 4);

        Text password = new Text("Password:");
        grid.add(password, 0, 5);
        final PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 5);

        Text rPassword = new Text("Repeat password:");
        grid.add(rPassword, 0, 6);
        final PasswordField rPaswordField = new PasswordField();
        grid.add(rPaswordField, 1, 6);

        final HBox hButtonBox = new HBox();
        hButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        hButtonBox.setSpacing(10);

        final Button cancelBytton = new Button("Cancel");
        cancelBytton.setMinWidth(70);
        hButtonBox.getChildren().add(cancelBytton);
        final Button nextButton = new Button("Next");
        nextButton.setMinWidth(70);
        hButtonBox.getChildren().add(nextButton);
        grid.add(hButtonBox, 0, 7, 2, 1);

        final Text errorMassage = new Text();
        errorMassage.setFill(Color.RED);
        grid.add(errorMassage, 0, 8, 2, 1);

        cancelBytton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!firstNameField.getText().isEmpty() && !secondNameField.getText().isEmpty() &&
                        !eMailField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                        !rPaswordField.getText().isEmpty()) {
                    if (passwordField.getText().equals(rPaswordField.getText())) {
                        stage.close();
                        final Stage childStage = new Stage();
                        childStage.setTitle("HomeKat | First start");
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(25));

                        Text secondInfMessage = new Text("Now we must choose a directory,\n" +
                                "where will store your files");
                        secondInfMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
                        gridPane.add(secondInfMessage, 0, 0, 2, 1);

                        final TextField dirField = new TextField();
                        dirField.setMinWidth(300);
                        gridPane.add(dirField, 0, 1);

                        HBox shButtonBox = new HBox();
                        shButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
                        shButtonBox.setSpacing(10);

                        Button openDir = new Button("Open");
                        openDir.setMinWidth(70);
                        gridPane.add(openDir, 1, 1);

                        Button backBytton = new Button("Back");
                        backBytton.setMinWidth(70);
                        shButtonBox.getChildren().add(backBytton);
                        Button okButton = new Button("Ok");
                        okButton.setMinWidth(70);
                        shButtonBox.getChildren().add(okButton);
                        gridPane.add(shButtonBox, 0, 2, 2, 1);

                        File selectedFile;
                        backBytton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                childStage.close();
                                stage.show();
                            }
                        });

                        openDir.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                DirectoryChooser directoryChooser = new DirectoryChooser();
                                directoryChooser.setTitle("Choouse directory");
                                File selectedFile = directoryChooser.showDialog(childStage);
                                dirField.setText(selectedFile.toString());
                            }
                        });

                        okButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                File testFile = new File(dirField.getText());
                                if (testFile.exists()) {
                                    mainUser = UserLogic.creatingNewUser(firstNameField.getText(), secondNameField.getText(),
                                            eMailField.getText(), passwordField.getText(), 0);
                                    dir = testFile.toString();
                                    MainLogic.createSettingsFile(dir);
                                    childStage.close();
                                    //Data Base
                                    dataArray = new DataArray();
                                    DataLogic.createDataArrayFromDir(dataArray, dir, "admin");
                                    DataLogic.saveDataArrayFromDataBase(dataArray);
                                    mainWindow();
                                }
                            }
                        });

                        Scene sScene = new Scene(gridPane);
                        childStage.setScene(sScene);
                        childStage.setResizable(false);
                        childStage.show();
                    } else {
                        errorMassage.setText("Passwords must be the same");
                    }
                } else {
                    errorMassage.setText("Please fill in all fields");
                }
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * main program window
     *
     */
    private void mainWindow() {
        dataArray = DataLogic.readDataArrayFromDataBase();
        data = FXCollections.observableArrayList();
        table = new TableView<TableItem>();
        table.setPrefSize(3000,3000);
        final TableInterface tableInterface = new TableInterface();



        final Stage mainStage = new Stage();
        mainStage.setTitle("HomerKatt");
        final BorderPane borderPane = new BorderPane();



        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.setSpacing(10);
        String UserName = new String("User: " + mainUser.toString());
        Text bottomUserName = new Text(UserName);
        bottomHBox.getChildren().add(bottomUserName);
        Text bottomProgres = new Text("Loaded " + mainUser.getLoad()/(1024*1024) + "/10 Mb");
        bottomHBox.getChildren().add(bottomProgres);
        ProgressBar progressBar = new ProgressBar();
        bottomHBox.getChildren().add(progressBar);
        borderPane.setBottom(bottomHBox);



        final TreeItem<String> root = new TreeItem<String>("Kat");
        root.setExpanded(true);
        final TreeItem<String> documentItem = new TreeItem<String>("Documents");
        documentItem.setExpanded(false);
        final TreeItem<String> bookItem = new TreeItem<String>("Books");
        bookItem.setExpanded(false);
        final TreeItem<String> songItem = new TreeItem<String>("Songs");
        songItem.setExpanded(false);
        final TreeItem<String> filmItem = new TreeItem<String>("Films");
        filmItem.setExpanded(false);
        root.getChildren().add(documentItem);
        root.getChildren().add(bookItem);
        root.getChildren().add(songItem);
        root.getChildren().add(filmItem);

        treeView = new TreeView<String>(root);
        treeView.setMaxWidth(150);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            public void changed(ObservableValue<? extends TreeItem<String>> ov,
                                TreeItem<String> old_val, TreeItem<String> new_val) {
                if (new_val == root) {
                    TableLogic.setData(dataArray, data);
                    tableInterface.setDocumentCols(table, mainUser.getType() == 0);
                }
                if (new_val == documentItem) {
                    TableLogic.setDocument(dataArray.documents, data);
                    tableInterface.setDocumentCols(table, mainUser.getType() == 0);

                }
                if (new_val == bookItem) {
                    TableLogic.setBook(dataArray.books, data);
                    tableInterface.setBookCols(table, mainUser.getType() == 0);
                }
                if (new_val == songItem) {
                    TableLogic.setSong(dataArray.songs, data);
                    tableInterface.setSongCols(table, mainUser.getType() == 0);
                }
                if (new_val == filmItem) {
                    TableLogic.setFilm(dataArray.films, data);
                    tableInterface.setFilmCols(table, mainUser.getType() == 0);
                }
                table.setItems(data);
            }
        });


        borderPane.setLeft(treeView);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        final TextField searchLine = new TextField();
        Button searchButton = new Button("Saerch");
        searchButton.setMinWidth(70);
        hBox.getChildren().addAll(searchLine,searchButton);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.BOTTOM_RIGHT);

        searchLine.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!searchLine.getText().equals("")) {
                    switch (treeView.getSelectionModel().getSelectedIndex()) {
                        case -1:
                        case 0: {
                            DataLogic.searchData(searchLine.getText(), dataArray, data);
                            break;
                        }
                        case 1: {
                            DataLogic.searchDocuments(searchLine.getText(), dataArray.documents, data,
                                    tableInterface.getDocumentFlags());
                            break;
                        }
                        case 2: {
                            DataLogic.searchBooks(searchLine.getText(), dataArray.books, data,
                                    tableInterface.getBookFlags());
                            break;
                        }
                        case 3: {
                            DataLogic.searchSongs(searchLine.getText(), dataArray.songs,data,
                                    tableInterface.getSongFlags());
                            break;
                        }
                        case 4: {
                            DataLogic.searchFilms(searchLine.getText(), dataArray.films,data,
                                    tableInterface.getFilmFlags());
                            break;
                        }
                    }
                }
                else{
                    paintTable();
                }
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                searchLine.textProperty().setValue(searchLine.getText());
            }
        });

        tableInterface.setDocumentCols(table,mainUser.getType() == 0);
        table.setEditable(true);
        TableLogic.setData(dataArray, data);
        table.setItems(data);
        vBox.getChildren().addAll(hBox,table);
        vBox.setPadding(new Insets(0));
        vBox.setSpacing(0);
        borderPane.setCenter(vBox);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(table.getSelectionModel().getSelectedItem() == null){
                    return;
                }
                String fileName = table.getSelectionModel().getSelectedItem().getName();
                if (fileName != null) {
                    Document document = DataLogic.getForName(dataArray.documents, fileName);
                    if (document != null) {
                        borderPane.setRight(documentGridPane(document, borderPane));
                    }
                    Book book = DataLogic.getForName(dataArray.books, fileName);
                    if (book != null) {
                        borderPane.setRight(bookGridPane(book, borderPane));
                    }
                    Song song = DataLogic.getForName(dataArray.songs, fileName);
                    if (song != null) {
                        borderPane.setRight(songGridPane(song, borderPane));
                    }
                    Film film = DataLogic.getForName(dataArray.films, fileName);
                    if (film != null) {
                        borderPane.setRight(filmGridPane(film, borderPane));
                    }
                }
            }
        });

 /*       table.setRowFactory(new Callback<TableView<TableItem>, TableRow<TableItem>>() {
            @Override
            public TableRow<TableItem> call(TableView<TableItem> tableItemTableView) {
                final TableRow<TableItem> row = new TableRow<TableItem>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getClickCount() == 2 && !row.isEmpty()){
                            TableItem rowData = row.getItem();
                            Desktop desktop = null;
                            if(Desktop.isDesktopSupported()){
                                desktop = Desktop.getDesktop();
                            }
                            try{
                                desktop.open(new File(dir + "/" + rowData.getName()));
                            }catch (IOException ex){
                                log.error(ex.getMessage());
                            }
                        }
                    }
                });
                return row ;
            }
        });
*/
        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(10);
        menuBar.setLayoutY(10);
        menuBar.setCursor(Cursor.CLOSED_HAND);

        Menu fileMenu = new Menu("File");
        MenuItem addFile = new MenuItem("add file");
        Menu viewMenu = new Menu("View");
        MenuItem tableView = new MenuItem("table view");

        DocumentAdditionInterface documentAdditionInterface =
                new DocumentAdditionInterface(mainUser,dataArray,dir,table,treeView,data,bottomProgres);
        final DocumentAdditionInterface finalDocumentAdditionInterface = documentAdditionInterface;

        if(mainUser.getType()==2){
            addFile.setDisable(true);
        }

        addFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                File newFile = fileChooser.showOpenDialog(mainStage);

                if (newFile.isFile()) {
                    Document document = new Document(newFile, mainUser.geteMail(), "");
                    String type = DataLogic.fileType(newFile);
                    finalDocumentAdditionInterface.createdNewDocument(type, newFile);
                }
            }
        });

        viewMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tableInterface.windowUpdateColumns(table,  mainUser.getType() == 0,treeView.getSelectionModel().getSelectedIndex());
            }
        });

        fileMenu.getItems().addAll(addFile);
        viewMenu.getItems().addAll(tableView);

        menuBar.getMenus().addAll(fileMenu, viewMenu);
        borderPane.setTop(menuBar);

        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                UserLogic.saveLoadUser(mainUser);
            }
        });

        Scene scene = new Scene(borderPane);
        mainStage.setScene(scene);
        mainStage.show();
    }


    private GridPane documentGridPane(final Document document, final BorderPane parentBorderPane) {
        if (document == null)
            return null;
        final GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Text fileName = new Text(document.getName());
        fileName.setFont(new Font(16));
        fileName.setWrappingWidth(250);

        gridPane.add(fileName, 0, 0, 2, 1);


        Text length = new Text("length : ");
        length.setFont(new Font(14));
        gridPane.add(length, 0, 1);
        Text lengthValue = new Text(new Long(document.getLength()).toString());
        gridPane.add(lengthValue, 1, 1);

        Text resolution = new Text("resolution : ");
        gridPane.add(resolution, 0, 2);
        resolution.setFont(new Font(14));
        Text resolutionValue = new Text(document.getResolution());
        gridPane.add(resolutionValue, 1, 2);

        if(mainUser.getType() == 0) {
            Text creator = new Text("creator :");
            gridPane.add(creator, 0, 3);
            creator.setFont(new Font(14));
            Text creatorValue = new Text(document.getCreator());
            gridPane.add(creatorValue, 1, 3);
        }

        Text description = new Text("description :");
        gridPane.add(description, 0, 4);
        description.setFont(new Font(14));

        final TextArea descriptionField = new TextArea(document.getDescription());
        descriptionField.setPrefWidth(250);
        descriptionField.setEditable(false);
        gridPane.add(descriptionField, 0, 5, 2, 1);


        HBox hBox = new HBox();
        Button redact = new Button("redact");
        redact.setMinWidth(70);
        Button save = new Button("save");
        save.setMinWidth(70);
        Button dell = new Button("delete");
        dell.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().add(redact);
        hBox.getChildren().add(save);
        hBox.getChildren().add(dell);

        if(mainUser.getType() == 2){
            save.setDisable(true);
            dell.setDisable(true);
            redact.setDisable(true);
        }

        redact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(document.getCreator()) || mainUser.getType() == 0){
                    descriptionField.setEditable(true);
                }
                else{
                    message();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataLogic.saveDocumentChange(document,descriptionField.getText());
                descriptionField.setEditable(false);
            }
        });

        dell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(document.getCreator()) || mainUser.getType() == 0) {
                    DataLogic.deleteDocument(dataArray, document, dir);
                    paintTable();
                    table.setItems(data);
                    parentBorderPane.setRight(new GridPane());
                }
                else {
                    message();
                }
                }
        });
        gridPane.add(hBox, 0, 6, 2, 1);
        gridPane.setMaxWidth(300);
        return gridPane;
    }

    private GridPane bookGridPane(final Book book, final BorderPane parentBorderPane) {
        if (book == null)
            return null;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Text fileName = new Text(book.getName());
        fileName.setFont(new Font(16));
        fileName.setWrappingWidth(250);

        gridPane.add(fileName, 0, 0, 2, 1);


        Text length = new Text("length : ");
        gridPane.add(length, 0, 1);
        length.setFont(new Font(14));
        Text lengthValue = new Text(new Long(book.getLength()).toString());
        gridPane.add(lengthValue, 1, 1);

        Text resolution = new Text("resolution : ");
        gridPane.add(resolution, 0, 2);
        resolution.setFont(new Font(14));
        Text resolutionValue = new Text(book.getResolution());
        gridPane.add(resolutionValue, 1, 2);

        if(mainUser.getType() == 0) {
            Text creator = new Text("creator :");
            gridPane.add(creator, 0, 3);
            creator.setFont(new Font(14));
            Text creatorValue = new Text(book.getCreator());
            gridPane.add(creatorValue, 1, 3);
        }

        Text bookName = new Text("book name :");
        gridPane.add(bookName, 0, 4);
        bookName.setFont(new Font(14));
        final TextField bookNameField = new TextField(book.getBookName());
        bookNameField.setEditable(false);
        gridPane.add(bookNameField, 1, 4);

        Text autor = new Text("autor :");
        gridPane.add(autor, 0, 5);
        autor.setFont(new Font(14));
        final TextField autorField = new TextField(book.getAutor());
        autorField.setEditable(false);
        gridPane.add(autorField, 1, 5);

        Text genre = new Text("genre :");
        gridPane.add(genre, 0, 6);
        genre.setFont(new Font(14));
        final TextField genreField = new TextField(book.getGenre());
        genreField.setEditable(false);
        gridPane.add(genreField, 1, 6);


        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 7);

        final TextArea descriptionField = new TextArea(book.getDescription());
        descriptionField.setPrefWidth(250);
        descriptionField.setEditable(false);
        gridPane.add(descriptionField, 0, 8, 2, 1);


        HBox hBox = new HBox();
        Button redact = new Button("redact");
        redact.setMinWidth(70);
        Button save = new Button("save");
        save.setMinWidth(70);
        Button dell = new Button("delete");
        dell.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().add(redact);
        hBox.getChildren().add(save);
        hBox.getChildren().add(dell);


        if(mainUser.getType() == 2){
            save.setDisable(true);
            dell.setDisable(true);
            redact.setDisable(true);
        }

        redact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(book.getCreator()) || mainUser.getType() == 0) {
                    descriptionField.setEditable(true);
                    bookNameField.setEditable(true);
                    autorField.setEditable(true);
                    genreField.setEditable(true);
                }
                else {
                    message();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataLogic.saveBookChange(book,bookNameField.getText(), autorField.getText(),
                        genreField.getText(),descriptionField.getText());
                descriptionField.setEditable(false);
                bookNameField.setEditable(false);
                autorField.setEditable(false);
                genreField.setEditable(false);
            }
        });

        dell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(book.getCreator()) || mainUser.getType() == 0) {
                    DataLogic.deleteBook(dataArray, book, dir);
                    paintTable();
                    table.setItems(data);
                    parentBorderPane.setRight(new GridPane());
                }
                else {
                    message();
                }
            }
        });

        gridPane.add(hBox, 0, 9, 2, 1);
        gridPane.setMaxWidth(300);
        return gridPane;
    }

    private GridPane songGridPane(final Song song, final BorderPane parentBorderPane) {
        if (song == null)
            return null;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Text fileName = new Text(song.getName());
        fileName.setFont(new Font(16));
        fileName.setWrappingWidth(250);

        gridPane.add(fileName, 0, 0, 2, 1);


        Text length = new Text("length : ");
        gridPane.add(length, 0, 1);
        length.setFont(new Font(14));
        Text lengthValue = new Text(new Long(song.getLength()).toString());
        gridPane.add(lengthValue, 1, 1);

        Text resolution = new Text("resolution : ");
        gridPane.add(resolution, 0, 2);
        resolution.setFont(new Font(14));
        Text resolutionValue = new Text(song.getResolution());
        gridPane.add(resolutionValue, 1, 2);

        if(mainUser.getType() == 0) {
            Text creator = new Text("creator :");
            gridPane.add(creator, 0, 3);
            creator.setFont(new Font(14));
            Text creatorValue = new Text(song.getCreator());
            gridPane.add(creatorValue, 1, 3);
        }


        Text songName = new Text("song name :");
        gridPane.add(songName, 0, 4);
        songName.setFont(new Font(14));
        final TextField songNameField = new TextField(song.getSongName());
        songNameField.setEditable(false);
        gridPane.add(songNameField, 1, 4);

        Text executor = new Text("executor :");
        gridPane.add(executor, 0, 5);
        executor.setFont(new Font(14));
        final TextField executorField = new TextField(song.getExecutor());
        executorField.setEditable(false);
        gridPane.add(executorField, 1, 5);

        Text album = new Text("album :");
        gridPane.add(album, 0, 6);
        album.setFont(new Font(14));
        final TextField albumField = new TextField(song.getAlbum());
        albumField.setEditable(false);
        gridPane.add(albumField, 1, 6);

        Text year = new Text("year :");
        gridPane.add(year, 0, 7);
        year.setFont(new Font(14));
        final TextField yearField = new TextField(new Integer(song.getYear()).toString());
        yearField.setEditable(false);
        gridPane.add(yearField, 1, 7);

        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 8);

        final TextArea descriptionField = new TextArea(song.getDescription());
        descriptionField.setPrefWidth(250);
        descriptionField.setEditable(false);
        gridPane.add(descriptionField, 0, 9, 2, 1);


        HBox hBox = new HBox();
        Button redact = new Button("redact");
        redact.setMinWidth(70);
        Button save = new Button("save");
        save.setMinWidth(70);
        Button dell = new Button("delete");
        dell.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().add(redact);
        hBox.getChildren().add(save);
        hBox.getChildren().add(dell);

        if(mainUser.getType() == 2){
            save.setDisable(true);
            dell.setDisable(true);
            redact.setDisable(true);
        }

        redact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(song.getCreator()) || mainUser.getType() == 0) {
                    descriptionField.setEditable(true);
                    songNameField.setEditable(true);
                    executorField.setEditable(true);
                    albumField.setEditable(true);
                    yearField.setEditable(true);
                }else {
                    message();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataLogic.saveSongChange(song,songNameField.getText(),executorField.getText(),
                        albumField.getText(),Integer.parseInt(yearField.getText()),descriptionField.getText());
                descriptionField.setEditable(false);
                songNameField.setEditable(false);
                executorField.setEditable(false);
                albumField.setEditable(false);
                yearField.setEditable(false);
            }
        });

        dell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(song.getCreator()) || mainUser.getType() == 0) {
                    DataLogic.deleteSong(dataArray, song, dir);
                    System.out.println(treeView.getSelectionModel().getSelectedIndex());
                    paintTable();
                    table.setItems(data);
                    parentBorderPane.setRight(new GridPane());
                }else {
                    message();
                }
            }
        });

        gridPane.add(hBox, 0, 10, 2, 1);
        gridPane.setMaxWidth(300);
        return gridPane;
    }

    private GridPane filmGridPane(final Film film, final BorderPane parentBorderPane) {
        if (film == null)
            return null;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(15));

        Text fileName = new Text(film.getName());
        fileName.setFont(new Font(16));
        fileName.setWrappingWidth(250);

        gridPane.add(fileName, 0, 0, 2, 1);


        Text length = new Text("length : ");
        gridPane.add(length, 0, 1);
        length.setFont(new Font(14));
        Text lengthValue = new Text(new Long(film.getLength()).toString());
        gridPane.add(lengthValue, 1, 1);

        Text resolution = new Text("resolution : ");
        gridPane.add(resolution, 0, 2);
        resolution.setFont(new Font(14));
        Text resolutionValue = new Text(film.getResolution());
        gridPane.add(resolutionValue, 1, 2);

        if(mainUser.getType() == 0) {
            Text creator = new Text("creator :");
            gridPane.add(creator, 0, 3);
            creator.setFont(new Font(14));
            Text creatorValue = new Text(film.getCreator());
            gridPane.add(creatorValue, 1, 3);
        }

        Text filmName = new Text("film name :");
        gridPane.add(filmName, 0, 4);
        filmName.setFont(new Font(14));
        final TextField filmNameField = new TextField(film.getFilmName());
        filmNameField.setEditable(false);
        gridPane.add(filmNameField, 1, 4);

        Text producer = new Text("producer :");
        gridPane.add(producer, 0, 5);
        producer.setFont(new Font(14));
        final TextField producerField = new TextField(film.getProducer());
        producerField.setEditable(false);
        gridPane.add(producerField, 1, 5);

        Text genre = new Text("genre :");
        gridPane.add(genre, 0, 6);
        genre.setFont(new Font(14));
        final TextField genreField = new TextField(film.getGenre());
        genreField.setEditable(false);
        gridPane.add(genreField, 1, 6);

        Text description = new Text("description :");
        description.setFont(new Font(14));
        gridPane.add(description, 0, 7);

        final TextArea descriptionField = new TextArea(film.getDescription());
        descriptionField.setPrefWidth(250);
        descriptionField.setEditable(false);
        gridPane.add(descriptionField, 0, 8, 2, 1);


        HBox hBox = new HBox();
        Button redact = new Button("redact");
        redact.setMinWidth(70);
        Button save = new Button("save");
        save.setMinWidth(70);
        final Button dell = new Button("delete");
        dell.setMinWidth(70);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().add(redact);
        hBox.getChildren().add(save);
        hBox.getChildren().add(dell);


        if(mainUser.getType() == 2){
            save.setDisable(true);
            dell.setDisable(true);
            redact.setDisable(true);
        }

        redact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(film.getCreator()) || mainUser.getType() == 0) {
                    descriptionField.setEditable(true);
                    filmNameField.setEditable(true);
                    producerField.setEditable(true);
                    genreField.setEditable(true);
                }else {
                    message();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataLogic.saveFilmChange(film,filmNameField.getText(),producerField.getText(),
                        genreField.getText(),descriptionField.getText());
                descriptionField.setEditable(false);
                filmNameField.setEditable(false);
                producerField.setEditable(false);
                genreField.setEditable(false);
            }
        });

        dell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(mainUser.geteMail().equals(film.getCreator()) || mainUser.getType() == 0) {
                    DataLogic.deleteFilm(dataArray, film, dir);
                    System.out.println(treeView.getSelectionModel().getSelectedIndex());
                    paintTable();
                    table.setItems(data);
                    parentBorderPane.setRight(new GridPane());
                }else {
                    message();
                }
            }
        });

        gridPane.add(hBox, 0, 9, 2, 1);
        gridPane.setMaxWidth(300);
        return gridPane;
    }

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

    private void message(){
        final Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);

        VBox vBox = new VBox();
        Text mess = new Text("Sorry, but you do not have permission to edit this file.");
        Button button = new Button("Ok");
        button.setMinWidth(70);
        vBox.getChildren().addAll(mess,button);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
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