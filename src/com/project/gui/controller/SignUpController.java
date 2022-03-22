package com.project.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.project.be.User;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.mapping.Value;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public JFXButton closeWindow;
    @FXML
    private TextField firstName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField userName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private DatePicker birthDate;
    @FXML
    private JFXCheckBox termsConditions;
    @FXML
    private JFXComboBox<String> categoryComboBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label siLabel;
    @FXML
    private HBox hbLabel;
    @FXML
    private HBox userNameHBox;
    @FXML
    private HBox passwordHBox;
    @FXML
    private HBox emailHBox;
    @FXML
    private HBox genderHBox;

    private boolean customer = true;

    private static final String[] items ={"Coordinator", "Customer"};
    private CoordinatorModel coordinatorModel;
    private CustomerModel customerModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLimitsDatePicker(birthDate);

        userNameHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.USER));
        passwordHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.LOCK));
        emailHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.ENVELOPE));
        genderHBox.getChildren().add(GlyphsDude.createIcon(FontAwesomeIcons.USERS));

        categoryComboBox.getItems().addAll(items);

        try {
            coordinatorModel = new CoordinatorModel();
            customerModel = new CustomerModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public void setHbLabel (){
        siLabel.setFont(new javafx.scene.text.Font(12));
        if (customer){
            siLabel.setText("New customer");
            categoryComboBox.getSelectionModel().select(1);
            categoryComboBox.setDisable(true);
        }
        else{
            siLabel.setFont(new javafx.scene.text.Font(10));
            categoryComboBox.getSelectionModel().select(0);
            categoryComboBox.setDisable(true);
            siLabel.setText("New coordinator");}
    }

    public void closeWindow(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Are you sure you want to close this window ?");
        alert.setContentText("All your infos will be lost in this case.");
        alert.showAndWait();
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) closeWindow.getScene().getWindow();
            stage.close();
        }
    }

    public void signUp(ActionEvent actionEvent) throws SQLException {

        try {
            if(categoryComboBox.getSelectionModel().getSelectedItem().equals("Customer")){
                try {
                    customerModel.createCustomer(firstName.getText(),lastName.getText(),userName.getText(),password.getText(),email.getText(),birthDate.getValue());
                    Stage stage = (Stage) closeWindow.getScene().getWindow();
                    stage.close();
                }catch (UserException userException){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(userException.getExceptionMessage());
                    alert.setContentText(userException.getInstructions());
                    alert.showAndWait();
                }
            }
            else {
                try {
                    coordinatorModel.createCoordinator(firstName.getText(),lastName.getText(),userName.getText(),password.getText(),email.getText(),birthDate.getValue());
                    Stage stage = (Stage) closeWindow.getScene().getWindow();
                    stage.close();
                }catch (UserException userException){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(userException.getExceptionMessage());
                    alert.setContentText(userException.getInstructions());
                    alert.showAndWait();
                }
            }
        }catch (NullPointerException ignored){
            try {
                if (categoryComboBox.getSelectionModel().getSelectedItem().isEmpty());
            }catch (NullPointerException npe){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText("Please find which category of user you are.");
                alert.setContentText("Select choice from combo box");
                alert.showAndWait();
            }
            if (!termsConditions.isSelected()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText("Please make sure to accept our terms & conditions.");
                alert.setContentText("Please check the check box.");
                alert.showAndWait();
            }
            if (birthDate.getValue()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText("Please select your date of birth.");
                alert.showAndWait();
            }
    }}
    private void setLimitsDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(empty || item.compareTo(LocalDate.now()) > 0);
                    }
                };
            }
        });
    }
}
