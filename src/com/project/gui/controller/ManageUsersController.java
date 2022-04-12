package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.gui.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {

    @FXML
    private TextField searchFilterCustomer;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer,String> fNameColumn;

    @FXML
    private TableColumn<Customer,String> lNameColumn;

    @FXML
    private TableColumn<Customer,String> emailColumn;

    @FXML
    private TableColumn<Customer,String> phoneNumberColumn;

    @FXML
    private ListView<Event> eventsListView;

    CustomerModel customerModel;

    public ManageUsersController() throws IOException, SQLException {
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
    private void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();

        SignUpController controller = loader.getController();
        controller.setCustomerModel(customerModel);
        controller.setCoordinator(false);
        controller.disableUsernamePassword();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initCustomerTable();
            setupCustomerTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupCustomerTable() {
        customersTable.setEditable(true);
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                Customer customer = event.getRowValue();
                customer.setFirstName(event.getNewValue());
                try {
                    customerModel.editCustomer(customer);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                Customer customer = event.getRowValue();
                customer.setLastName(event.getNewValue());
                try {
                    customerModel.editCustomer(customer);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                Customer customer = event.getRowValue();
                customer.setEmail(event.getNewValue());

                try {
                    customerModel.editCustomer(customer);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initCustomerTable() throws SQLException {
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customersTable.setItems(customerModel.getAllCustomers());
    }

    private void refreshUsers() throws SQLException {
        customersTable.setItems(customerModel.getAllCustomers());
    }

    public void handleSortCustomers(KeyEvent keyEvent) throws SQLException {
        String query = searchFilterCustomer.getText();
        if (query.isEmpty()){
            refreshUsers();
            return;
        }

        List<Customer> matchingUsers = new ArrayList<>();
        for (Customer customer : customerModel.getAllCustomers()) {
            if (customer.toString().contains(query))
                matchingUsers.add(customer);
        }
        customersTable.setItems(FXCollections.observableArrayList(matchingUsers));
    }

    @FXML
    private void handleSelectUser(MouseEvent mouseEvent) {
    }
}

