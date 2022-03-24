package com.project.gui.controller;

import com.project.be.Admin;
import com.project.be.Coordinator;
import com.project.be.Customer;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LogInController implements Initializable {
    @FXML
    private AnchorPane anchorPaneLeft;
    @FXML
    private AnchorPane anchorPaneRight;
    @FXML
    private TextField userName;
    @FXML
    private CustomPasswordField passWord;

    LogInModel logInModel;

    private Main main;

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
        Text usersIcons = GlyphsDude.createIcon(FontAwesomeIcons.USERS, "35px");
        usersIcons.setFill(Paint.valueOf("#0598ff"));
        usersIcons.setLayoutX(152);
        usersIcons.setLayoutY(101);
        anchorPaneRight.getChildren().add(usersIcons);

        Text keyIcon = GlyphsDude.createIcon(FontAwesomeIcons.LOCK, "20px");
        keyIcon.setFill(Paint.valueOf("#0598ff"));
        keyIcon.setLayoutX(69);
        keyIcon.setLayoutY(253);
        anchorPaneRight.getChildren().add(keyIcon);

        Text userIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER, "20px");
        userIcon.setFill(Paint.valueOf("#0598ff"));
        userIcon.setLayoutX(70);
        userIcon.setLayoutY(200);
        anchorPaneRight.getChildren().add(userIcon);

        Text calendarIcon = GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "35px");
        calendarIcon.setFill(Paint.valueOf("white"));
        calendarIcon.setLayoutX(153);
        calendarIcon.setLayoutY(101);
        anchorPaneLeft.getChildren().add(calendarIcon);
    }

    public void logIn(ActionEvent actionEvent) throws Exception, UserException {

        Admin admin = logInModel.logInAdminCredentials(userName.getText());
        Customer customer = logInModel.logInCustomerCredentials(userName.getText());
        Coordinator coordinator = logInModel.logInCoordinatorCredentials(userName.getText());

        if (admin == null && customer == null && coordinator == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("User name not found");
            alert.setContentText("Please try to sign up before you can be able to log in.");
            alert.showAndWait();
        }
        if(admin!=null){
        if (passWord.getText().equals(admin.getPassWord())) {
            main.setLayoutChosen("admin");
            main.initRootLayout();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("wrong credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }}
        if(customer!=null){
        if (passWord.getText().equals(customer.getPassWord())) {
            main.setLayoutChosen("customer");
            main.initRootLayout();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("wrong credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }}
if (coordinator!=null){
        if (passWord.getText().equals(coordinator.getPassWord())) {
            main.setLayoutChosen("coordinator");
            main.initRootLayout();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("wrong credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }}
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
