package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.gui.model.CustomerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {

    @FXML
    private TextField searchFilterCustomer;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<?, ?> fNameColumn;

    @FXML
    private TableColumn<?, ?> lNameColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> phoneNumberColumn;

    @FXML
    private ListView<Event> eventsListView;

    CustomerModel customerModel;

    public ManageUsersController() throws IOException {
        customerModel = new CustomerModel();
    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null)
                return;
        customerModel.deleteCustomer(selectedCustomer);
        customersTable.getItems().remove(selectedCustomer);
    }

    @FXML
    void editCustomer(ActionEvent event) {

    }

    @FXML
    private void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        SignUpController controller = loader.getController();
        controller.setCoordinator(false);
        //controller.disableUsernamePassword();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initCustomerTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initCustomerTable() throws SQLException {
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customersTable.setItems(customerModel.getAllCustomers());
    }
}

