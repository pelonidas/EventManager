package com.project.gui.controller;

import com.project.be.Admin;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.User;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.LogInModel;
import com.project.gui.view.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

    private List<Customer> allCustomers;
    private List<Coordinator> allCoordinators;
    private List<Admin> allAdmins;
    private boolean connected= false;

    public void setMainApp(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            logInModel = new LogInModel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            allCustomers= logInModel.getAllCustomers();
            allCoordinators= logInModel.getAllCoordinators();
            allAdmins= logInModel.getAllAdmins();
        } catch (SQLException | UserException e) {
            e.printStackTrace();
        }

        Text usersIcons = GlyphsDude.createIcon(FontAwesomeIcons.USERS, "35px");
        usersIcons.setFill(Paint.valueOf("#0598ff"));
        usersIcons.setLayoutX(152);
        usersIcons.setLayoutY(101);
        usersIcon.getChildren().add(usersIcons);

        Text keyIcon = GlyphsDude.createIcon(FontAwesomeIcons.LOCK, "20px");
        keyIcon.setFill(Paint.valueOf("#0598ff"));
        keyIcon.setLayoutX(69);
        keyIcon.setLayoutY(253);
        passwordICon.getChildren().add(keyIcon);

        Text userIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px");
        userIcon.setFill(Paint.valueOf("#0598ff"));
        userIcon.setLayoutX(70);
        userIcon.setLayoutY(200);
        userNameIcon.getChildren().add(userIcon);

        Text calendarIcon = GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "35px");
        calendarIcon.setFill(Paint.valueOf("white"));
        calendarIcon.setLayoutX(153);
        calendarIcon.setLayoutY(101);
        calenderIcon.getChildren().add(calendarIcon);
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


}
