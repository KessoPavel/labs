package Interface;

import Client.AdminMessage;
import Client.Message;
import Client.Worker;
import Data.Dossier;
import Data.Person;
import Data.StudyPlace;
import Data.WorkPalace;
import Logic.TableLogic;
import Users.User;
import javafx.application.Application;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * Created by kesso on 17.04.17.
 */
public class MainWindow extends Application {

    private Worker worker;
    private User mainUser;
    private Message lastShow = null;

    public void startWork(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        worker = new Worker(2222);
        this.SingIn(stage);
        //stage.show();
        //this.showMainWindow(stage);
    }

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


        Button Sup = new Button("Sing up");

        Button Sin = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(Sin);
        hbBtn.getChildren().add(Sup);
        grid.add(hbBtn, 1, 4);


        final Text errorMassage = new Text();
        errorMassage.setFill(Color.RED);
        grid.add(errorMassage, 0, 6, 2, 1);

        Sup.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.close();
                SingUp(stage);
            }
        });
        Sin.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!userTextField.getText().equals("") && !pwBox.getText().equals("")) {

                    AdminMessage question = new AdminMessage();
                    question.message = "check";
                    question.users = new ArrayList<User>();
                    question.users.add(new User(userTextField.getText(), -1));
                    question.password = pwBox.getText();

                    mainUser = worker.work(question).get(0);
                    if (mainUser != null) {
                        stage.close();
                        showMainWindow();
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
            public void handle(ActionEvent actionEvent) {
                if (!eMailField.getText().equals("") && !passwordField.getText().equals("") &&
                        !rPasswordField.getText().equals("")) {
                    if (!passwordField.getText().equals(rPasswordField.getText())) {
                        errorMessage.setText("Passwords must be the same");
                    } else {
                        AdminMessage question = new AdminMessage();
                        question.message = "add";
                        question.users = new ArrayList<User>();
                        question.users.add(new User(eMailField.getText(), 1));
                        question.password = passwordField.getText();

                        mainUser = worker.work(question).get(0);
                        if (mainUser != null) {
                            stage.close();
                            showMainWindow();
                        }
                    }
                } else {
                    errorMessage.setText("Please fill in all fields");
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.close();
                pStage.show();
            }
        });

        Scene scene = new Scene(grid, 350, 350);
        stage.setScene(scene);
        stage.show();
    }


    private void showMainWindow() {
        Stage stage = new Stage();


        stage.setTitle("Kube");

        final TableView<TableItem> answerTable = new TableView<TableItem>();
        final ObservableList<TableItem> data = FXCollections.observableArrayList();

        final HBox hBox = new HBox();

        final GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        Text requestTitle = new Text("Request");
        requestTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(requestTitle, 0, 0);
        Text answerTitle = new Text("Answer");
        answerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(answerTitle, 1, 0);

        ObservableList<String> types = FXCollections.observableArrayList(
                "Get"/*, "Add", "Change", "Delete"*/);
        if (mainUser.getType() > 1 || mainUser.getType() == 0) {
            types.add("Add");
        }
        if (mainUser.getType() > 2 || mainUser.getType() == 0) {
            types.add("Change");
        }
        if (mainUser.getType() > 3 || mainUser.getType() == 0) {
            types.add("Delete");
        }
        if (mainUser.getType() == 0) {
            types.add("Change user");
            types.add("Delete user");
        }
        ChoiceBox<String> requestType = new ChoiceBox<String>(types);
        requestType.setLayoutX(10);
        requestType.setLayoutY(10);
        requestType.setValue("Get");
        //gridPane.add(requestType,0,1);

        hBox.getChildren().add(GetAdd(0, answerTable, data));
        //gridPane.add(hBox,0,1);

        VBox requestBox = new VBox();
        requestBox.setMinWidth(300);
        requestBox.setSpacing(30);
        requestBox.getChildren().add(requestType);
        requestBox.getChildren().add(hBox);
        gridPane.add(requestBox, 0, 1);

        requestType.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if (number.intValue() != -1) {
                            switch (t1.intValue()) {
                                case 0:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(GetAdd(0, answerTable, data));
                                    break;
                                case 1:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(GetAdd(1, answerTable, data));
                                    break;
                                case 2:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(GetAdd(2, answerTable, data));
                                    break;
                                case 3:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(GetAdd(3, answerTable, data));
                                    break;
                                case 4:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(AGetAdd(0, answerTable, data));
                                    break;
                                case 5:
                                    hBox.getChildren().clear();
                                    hBox.getChildren().add(AGetAdd(1, answerTable, data));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
        );


        answerTable.setMinWidth(600);
        TableInterface tableInterface = new TableInterface();
        tableInterface.setCols(answerTable);

        gridPane.add(answerTable, 1, 1);


        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    //ОТПравлять таблицу что бы переопределять клик по ней
    private GridPane GetAdd(final int t, final TableView<TableItem> table, final ObservableList<TableItem> data) {


        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(290);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0));


        int i = 0;

        Text line = new Text();
        if (t == 2) {
            line.setText("Select a personal file to modify from the\ntable on the right");
            gridPane.add(line, 0, i, 2, 1);
            i++;
        }
        if (t == 3) {
            line.setText("Select a private file to delete from the\ntable on the right");
            gridPane.add(line, 0, i, 2, 1);
            i++;
        }
        if (t == 0 || t == 1) {
            line.setText("                                        \n ");
            gridPane.add(line, 0, i, 2, 1);
            i++;
        }


        TextField firstName = null;
        TextField secondName = null;
        TextField sex = null;
        TextField workPlace = null;
        TextField position = null;
        TextField institution = null;
        TextField startEd = null;
        TextField endEd = null;

        if (t != 3) {
            Text firstNameText = new Text("First Name : ");
            gridPane.add(firstNameText, 0, i);
            firstName = new TextField();
            gridPane.add(firstName, 1, i);
            i++;
            Text secondNameText = new Text("Second Name : ");
            gridPane.add(secondNameText, 0, i);
            secondName = new TextField();
            gridPane.add(secondName, 1, i);
            i++;

            Text sexText = new Text("Sex : ");
            gridPane.add(sexText, 0, i);
            sex = new TextField();
            gridPane.add(sex, 1, i);
            i++;
            Text workPlaceText = new Text("Work Place : ");
            gridPane.add(workPlaceText, 0, i);
            workPlace = new TextField();
            gridPane.add(workPlace, 1, i);
            i++;
            Text positionText = new Text("Position : ");
            gridPane.add(positionText, 0, i);
            position = new TextField();
            gridPane.add(position, 1, i);
            i++;
            Text institutionText = new Text("Institution : ");
            gridPane.add(institutionText, 0, i);
            institution = new TextField();
            gridPane.add(institution, 1, i);
            i++;

            Text startEdText = new Text("Start Ed. : ");
            gridPane.add(startEdText, 0, i);
            startEd = new TextField();
            gridPane.add(startEd, 1, i);
            i++;

            Text endEdText = new Text("End Ed. : ");
            gridPane.add(endEdText, 0, i);
            endEd = new TextField();
            gridPane.add(endEd, 1, i);
            i++;
        }
        final Text[] text = {new Text()};
        gridPane.add(text[0], 0, i);
        Button send = new Button("Send request");

        final TextField finalFirstName = firstName;
        final TextField finalSecondName = secondName;
        final TextField finalSex = sex;
        final TextField finalWorkPlace = workPlace;
        final TextField finalPosition = position;
        final TextField finalInstitution = institution;
        final TextField finalStartEd = startEd;
        final TextField finalEndEd = endEd;

        final TableInterface tableInterface = new TableInterface();
        tableInterface.setCols(table);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int row = table.getSelectionModel().getSelectedIndex();
                if (row != -1 && t == 2) {
                    TableItem tableItem = table.getItems().get(row);
                    finalFirstName.setText(tableItem.getFirstName());
                    finalSecondName.setText(tableItem.getSecondName());
                    finalSex.setText(tableItem.getSex());
                    finalWorkPlace.setText(tableItem.getWorkPlace());
                    finalPosition.setText(tableItem.getPosition());
                    finalInstitution.setText(tableItem.getInst());
                    finalStartEd.setText(tableItem.getStart());
                    finalEndEd.setText(tableItem.getEnd());
                }
            }
        });

        send.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Dossier dossier = new Dossier();
                Person person = new Person();
                dossier.setID(-1);
                if (t == 0 || t == 1 || t == 2) {
                    person.setFirstName(finalFirstName.getText());
                    person.setSecondName(finalSecondName.getText());
                    person.setSex(finalSex.getText());
                    WorkPalace workPalace = new WorkPalace(finalWorkPlace.getText(), finalPosition.getText());
                    person.setWorkPalace(workPalace);
                    StudyPlace studyPlace = new StudyPlace(finalInstitution.getText(), finalStartEd.getText(), finalEndEd.getText());
                    person.setStudyPlace(studyPlace);
                    dossier.setPerson(person);
                }
                if (t == 2 || t == 3) {
                    if (table.getSelectionModel().getSelectedIndex() != -1) {
                        int row = table.getSelectionModel().getSelectedIndex();
                        TableItem tableItem = table.getItems().get(row);

                        dossier.setID(Integer.parseInt(tableItem.getID()));//id from table
                    } else
                        return;
                }
                Message question = new Message();
                switch (t) {
                    case 0:
                        question.message = "show";
                        break;//TODo cохранять последний запрос и вызывать после каждого запроса
                    case 1:
                        question.message = "add";
                        break;
                    case 2:
                        question.message = "change";
                        break;
                    case 3:
                        question.message = "delete";
                        break;
                    default:
                        break;
                }
                question.dossiers = new ArrayList<Dossier>();
                question.dossiers.add(dossier);

                ArrayList<Dossier> answer = worker.work(question);


                if (answer != null) {
                    text[0].setText("Answer: ok");
                    text[0].setFill(Color.GREEN);
                } else {
                    text[0].setText("Answer: error");
                    text[0].setFill(Color.RED);
                }


                tableInterface.setCols(table);

                if (t == 0) {
                    TableLogic.setData(answer, data);
                    table.setItems(data);
                    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    lastShow = question;
                } else {
                    if (answer != null) {
                        ArrayList<Dossier> s = worker.work(lastShow);
                        TableLogic.setData(s, data);

                        table.setItems(data);
                        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    }
                }
            }
        });

        HBox hSend = new HBox(10);
        hSend.getChildren().add(send);
        hSend.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(hSend, 1, i);
        i++;
        return gridPane;
    }

    private GridPane AGetAdd(final int t, final TableView<TableItem> table, final ObservableList<TableItem> data) {
        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(290);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0));

        Text line = new Text();
        line.setText("Select a personal file to modify from the\ntable on the right");
        gridPane.add(line, 0, 0, 2, 1);

        Text eMailText = new Text("eMail : ");
        gridPane.add(eMailText, 0, 1);
        final Text eMail = new Text();
        gridPane.add(eMail, 1, 1);

        ChoiceBox<String> requestType = null;
        if (t == 0) {
            Text type = new Text("type : ");
            gridPane.add(type, 0, 2);

            ObservableList<String> types = FXCollections.observableArrayList(
                    "1 - read", "2 - write", "3 - change", "4 - delete");
            requestType = new ChoiceBox<String>(types);
            requestType.setLayoutX(10);
            requestType.setLayoutY(10);
            requestType.setValue("1 - read");
            gridPane.add(requestType, 1, 2);
        }
        final Text[] text = {new Text()};
        gridPane.add(text[0], 0, 3);
        Button send = new Button("Send request");
        gridPane.add(send, 1, 3);

        AdminTableInterface tableInterface = new AdminTableInterface();
        tableInterface.setCols(table);

        AdminMessage show = new AdminMessage();
        show.message = "show";
        ArrayList<User> users = worker.work(show);
        TableLogic.setAData(users, data);
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int row = table.getSelectionModel().getSelectedIndex();
                if (row != -1) {
                    TableItem tableItem = table.getItems().get(row);
                    eMail.setText(tableItem.geteMail());
                }
            }
        });
        final int[] tt = {-1};
        if (t == 0) {
            requestType.getSelectionModel().selectedIndexProperty().addListener(
                    new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            if (number.intValue() != -1) {
                                tt[0] = t1.intValue() + 1;
                            }
                        }
                    }
            );
        }
        send.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                AdminMessage question = new AdminMessage();
                switch (t) {
                    case 0: {
                        question.message = "change";
                        break;
                    }
                    case 1:
                        question.message = "delete";
                        break;
                    default:
                        break;
                }

                question.users = new ArrayList<User>();
                //todo обработку перекдючения комбо бокса
                question.users.add(new User(eMail.getText(), tt[0]));

                question.password = "";

                worker.work(question);
                AdminMessage show = new AdminMessage();
                show.message = "show";
                ArrayList<User> users = worker.work(show);
                TableLogic.setAData(users, data);
                table.setItems(data);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        });


        return gridPane;
    }
}

