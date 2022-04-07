package com.project.gui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.project.be.*;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.Mail;
import com.project.gui.model.LogInModel;
import com.project.gui.model.ManageEvents;
import com.project.gui.view.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class LogInController implements Initializable {
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private JFXCheckBox rememberMe;
    @FXML
    private HBox signUpBox;
    @FXML
    private Button signUpButton;
    @FXML
    private Label welcomeLabel0;
    @FXML
    private Label welcomeLabel1;
    @FXML
    private Label welcomeLabel2;
    @FXML
    private Label welcomeLabel3;
    @FXML
    private Label dhaLabel;
    @FXML
    private Text userLogInLabel;
    @FXML
    private Label eventManagementLabel;
    @FXML
    private GridPane rightPane;
    @FXML
    private GridPane leftPane;
    @FXML
    private HBox bigBox;
    @FXML
    private VBox leftBigBox;
    @FXML
    private VBox rightBigBox;
    @FXML
    private HBox calenderIcon;
    @FXML
    private HBox usersIcon;
    @FXML
    private HBox userNameIcon;
    @FXML
    private HBox passwordICon;

    @FXML
    private TextField userName;
    @FXML
    private CustomPasswordField passWord;

    LogInModel logInModel;

    private Main main;

    private Admin admin;
    // to be implemented : List<User>allUsers;

    private ObservableList<Customer> allCustomers;
    private ObservableList<Coordinator> allCoordinators;
    private ObservableList<Admin> allAdmins;
    private ObservableList<Event> allEvents;

    private boolean connected= false;

    private final DoubleProperty fontSizeBigLabel = new SimpleDoubleProperty(22);
    private final DoubleProperty fontSizeDhaLabel = new SimpleDoubleProperty(20);
    private final DoubleProperty fontSizeSmallLabel = new SimpleDoubleProperty(13);

    private ManageEvents manageEvents;

    public void setMainApp(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            logInModel = new LogInModel();
            manageEvents = new ManageEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            allCustomers= FXCollections.observableArrayList();
            allCustomers.setAll(logInModel.getAllCustomers());
            allCoordinators= FXCollections.observableArrayList();
            allCoordinators.setAll(logInModel.getAllCoordinators());
            allAdmins= FXCollections.observableArrayList();
            allAdmins.setAll(logInModel.getAllAdmins());
            allEvents=FXCollections.observableArrayList();
            allEvents.setAll(manageEvents.getAllEvents());
        } catch (SQLException | UserException e) {
            e.printStackTrace();
        }

        Text usersIcons = GlyphsDude.createIcon(FontAwesomeIcons.USERS, "35px");
        usersIcons.setFill(Paint.valueOf("#0598ff"));
        usersIcon.getChildren().add(usersIcons);

        Text keyIcon = GlyphsDude.createIcon(FontAwesomeIcons.LOCK, "20px");
        keyIcon.setFill(Paint.valueOf("#0598ff"));
        passwordICon.getChildren().add(keyIcon);

        Text userIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px");
        userIcon.setFill(Paint.valueOf("#0598ff"));
        userNameIcon.getChildren().add(userIcon);

        Text calendarIcon = GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "35px");
        calendarIcon.setFill(Paint.valueOf("white"));
        calenderIcon.getChildren().add(calendarIcon);



        leftBigBox.prefWidthProperty().bind(bigBox.widthProperty().multiply(55).divide(100));
        rightBigBox.prefWidthProperty().bind(bigBox.widthProperty().multiply(45).divide(100));
        leftPane.prefHeightProperty().bind(bigBox.heightProperty());
        rightPane.prefHeightProperty().bind(bigBox.heightProperty());
        /*
        fontSizeBigLabel.bind(leftPane.heightProperty().divide(100000).multiply(5128));
        fontSizeDhaLabel.bind(leftPane.heightProperty().divide(100000).multiply(4662));
        fontSizeSmallLabel.bind(leftPane.heightProperty().divide(100).multiply(3));


        eventManagementLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeBigLabel.asString()));
        userLogInLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeBigLabel.asString()));
        dhaLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDhaLabel.asString()));

        welcomeLabel0.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeSmallLabel.asString()));
        welcomeLabel1.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeSmallLabel.asString()));
        welcomeLabel2.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeSmallLabel.asString()));
        welcomeLabel3.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeSmallLabel.asString()));

        userName.prefHeightProperty().bind(rightPane.heightProperty().divide(1000).multiply(63));
        userName.prefWidthProperty().bind(rightPane.widthProperty().divide(100).multiply(70));

        passwordICon.prefHeightProperty().bind(rightPane.heightProperty().divide(1000).multiply(63));
        userName.prefWidthProperty().bind(rightPane.widthProperty().divide(100).multiply(70));




        //signUpButton.prefHeightProperty().bind(leftPane.heightProperty().divide(100000).multiply(246));
        ///signUpButton.prefWidthProperty().bind(leftPane.widthProperty().divide(1000000).multiply(678));

         */

        passWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    if (userName.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Alert");
                        alert.setHeaderText("Please enter a username");
                        alert.showAndWait();}
                    else
                        try {
                            logIn(new ActionEvent());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
        });

        userName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    if (passWord.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Please enter a password");
                    alert.showAndWait();}
                    else
                    try {
                        logIn(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void logIn(ActionEvent actionEvent) throws Exception {

        try {
            for (Admin admin: allAdmins){
                if (admin.getUserName().equals(userName.getText())&&admin.getPassWord().equals(passWord.getText())){
                    main.setLayoutChosen("admin");
                    connected=true;
                    try {
                        main.initRootLayout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Coordinator coordinator: allCoordinators) {
                if (coordinator.getUserName().equals(userName.getText())&& coordinator.getPassWord().equals(passWord.getText())){
                    main.setLayoutChosen("coordinator");
                    connected= true;
                    try {
                        main.initRootLayout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!connected){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText("Wrong credentials.");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        }catch (NullPointerException ignored){}

    }



    public void signUp(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Sign Up");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public ObservableList<Coordinator> getAllCoordinators() {
        return allCoordinators;
    }

    public void setAllCoordinators(ObservableList<Coordinator> allCoordinators) {
        this.allCoordinators = allCoordinators;
    }

    public ObservableList<Admin> getAllAdmins() {
        return allAdmins;
    }

    public void setAllAdmins(ObservableList<Admin> allAdmins) {
        this.allAdmins = allAdmins;
    }

    public void sendMail(ActionEvent actionEvent) throws Exception {
        Mail.sendMail("amine.aouina.lp.4@gmail.com");
    }

    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ObservableList<Event> allEvents) {
        this.allEvents = allEvents;
    }
}
