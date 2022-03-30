package com.project.gui.controller;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.CustomerModel;
import com.project.gui.view.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.hibernate.mapping.Column;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ManageUsersViewController implements Initializable {
    @FXML
    private ListView<Event> eventsListView;
    @FXML
    private TextField filterSearchCoordinator;
    @FXML
    private TextField searchFilterCustomer;
    @FXML
    private TableView<Coordinator> coordinatorsTable;
    @FXML
    private TableColumn<Coordinator,String> FNameCoordinatorColumn,LNameCoordinatorColumn,UNameCoordinatorColumn,PassWordCoordinatorColumn,EmailCoordinatorColumn;

    @FXML
    private TableColumn<Customer,LocalDate> BDateCoordinatorColumn;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer,String> FNameColumn, LNameColumn,EmailColumn;
    @FXML
    private TableColumn<Customer, LocalDate> BDateColumn;

    private final ObservableList <Customer>allCustomer=FXCollections.observableArrayList();
    private final ObservableList <Coordinator>allCoordinators=FXCollections.observableArrayList();

    private Customer selectedCustomer;
    private Coordinator selectedCoordinator;

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public Coordinator getSelectedCoordinator() {
        return selectedCoordinator;
    }

    public void setSelectedCoordinator(Coordinator selectedCoordinator) {
        this.selectedCoordinator = selectedCoordinator;
    }

    @FXML
    private TextField customersSearchFilter;
    @FXML
    private TextField eventCoordinatorSearchFilter;

    Main main;
    private CoordinatorModel coordinatorModel;
    private CustomerModel customerModel;
    public void backView(ActionEvent actionEvent) {
        main.setLayoutChosen("admin");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setMain(Main main) {
        this.main = main;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            coordinatorModel = new CoordinatorModel();
            customerModel = new CustomerModel();
            allCustomer.setAll(customerModel.getAllCustomers());
            allCoordinators.setAll(coordinatorModel.getAllCoordinators());
            setUpCoordinatorsTable();
            setUpCustomersTable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        customersTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSelectedCustomer(customersTable.getSelectionModel().getSelectedItem());
            }
        });

        if (getSelectedCustomer()!=null)
        eventsListView.getItems().addAll(getSelectedCustomer().getEventHistory());

        searchFilterCustomer.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Customer>search = FXCollections.observableArrayList();
                for(Customer customer : allCustomer){
                    if (customer.getFirstName().toLowerCase().contains(searchFilterCustomer.getText().toLowerCase(Locale.ROOT))||customer.getLastName().toLowerCase(Locale.ROOT).contains(searchFilterCustomer.getText().toLowerCase(Locale.ROOT)))
                        search.add(customer);}
                    customersTable.setItems(search);

            }
        });

        filterSearchCoordinator.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Coordinator>search = FXCollections.observableArrayList();
                for(Coordinator coordinator : allCoordinators){
                    if (coordinator.getFirstName().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT))||coordinator.getLastName().toLowerCase(Locale.ROOT).contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)))
                        search.add(coordinator);}
                coordinatorsTable.setItems(search);

            }
        });
    }

    public void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        setSelectedCustomer(customersTable.getSelectionModel().getSelectedItem());
        customerModel.deleteCustomer(getSelectedCustomer());
        allCustomer.remove(getSelectedCustomer());
        setUpCustomersTable();
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        main.initLogin();
    }

    public void newCoordinator(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New Coordinator");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCoordinator(ActionEvent actionEvent) throws SQLException {
        setSelectedCoordinator(coordinatorsTable.getSelectionModel().getSelectedItem());
        coordinatorModel.deleteCoordinator(getSelectedCoordinator());
        allCustomer.remove(getSelectedCustomer());
        setUpCoordinatorsTable();
    }

    private void setUpCustomersTable() throws SQLException {
        List<TableColumn<Customer,String>>allColumns=new ArrayList<>();
        FNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        BDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        allColumns.add(FNameColumn);
        allColumns.add(LNameColumn);
        allColumns.add(EmailColumn);
        customersTable.setEditable(true);

        for (TableColumn<Customer,String> tableColumn : allColumns){
            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
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
        });}
        customersTable.setItems(customerModel.getAllCustomers());
    }
    private void setUpCoordinatorsTable() throws SQLException {
        List<TableColumn<Coordinator,String>>allColumns=new ArrayList<>();

        FNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        UNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        PassWordCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        EmailCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        BDateCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        allColumns.add(FNameCoordinatorColumn);
        allColumns.add(LNameCoordinatorColumn);
        allColumns.add(UNameCoordinatorColumn);
        allColumns.add(PassWordCoordinatorColumn);
        allColumns.add(EmailCoordinatorColumn);

        coordinatorsTable.setEditable(true);

        for (TableColumn<Coordinator,String> tableColumn : allColumns){
            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coordinator, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Coordinator, String> event) {
                    Coordinator coordinator = event.getRowValue();
                    coordinator.setFirstName(event.getNewValue());
                    try {
                        customerModel.editCoordinator(coordinator);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        coordinatorsTable.setItems(coordinatorModel.getAllCoordinators());
    }
}
}
