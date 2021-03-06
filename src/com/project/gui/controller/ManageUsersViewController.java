package com.project.gui.controller;

import com.project.be.Admin;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private TableColumn<Coordinator, String> FNameCoordinatorColumn, LNameCoordinatorColumn, UNameCoordinatorColumn, PassWordCoordinatorColumn, EmailCoordinatorColumn;

    @FXML
    private TableColumn<Coordinator, Integer> phoneNumberCoordinatorColumn;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> FNameColumn, LNameColumn, EmailColumn;
    @FXML
    private TableColumn<Customer, Integer> phoneNumberColumn;

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Coordinator> allCoordinators = FXCollections.observableArrayList();
    private ObservableList<Admin> allAdmins = FXCollections.observableArrayList();

    private Customer selectedCustomer;
    private Coordinator selectedCoordinator;
    private Integer test = 1;

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

    public void backView(ActionEvent actionEvent) throws SQLException, IOException {
        main.setLayoutChosen("admin");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerModel = CustomerModel.getInstance();
        coordinatorModel = CoordinatorModel.getInstance();
    }



    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customersTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSelectedCustomer(customersTable.getSelectionModel().getSelectedItem());
                ObservableList<com.project.be.Event> allEvents;
                allEvents = FXCollections.observableArrayList();
                allEvents.addAll(getSelectedCustomer().getMyEvents());
                eventsListView.setItems(allEvents);
            }
        });

        if (getSelectedCustomer() != null)
            eventsListView.getItems().addAll(getSelectedCustomer().getEventHistory());

        customersTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    try {
                        deleteCustomer(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        coordinatorsTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    try {
                        deleteCoordinator(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        searchFilterCustomer.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Customer> search = FXCollections.observableArrayList();
                for (Customer customer : allCustomers) {
                    if (customer.getFirstName().toLowerCase().contains(searchFilterCustomer.getText().toLowerCase(Locale.ROOT)) || customer.getLastName().toLowerCase(Locale.ROOT).contains(searchFilterCustomer.getText().toLowerCase(Locale.ROOT)) || String.valueOf(customer.getPhoneNumber()).contains(searchFilterCustomer.getText()) || customer.getEmail().toLowerCase().contains(searchFilterCustomer.getText().toLowerCase(Locale.ROOT)))
                        search.add(customer);
                }
                customersTable.setItems(search);

            }
        });

        filterSearchCoordinator.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Coordinator> search = FXCollections.observableArrayList();
                for (Coordinator coordinator : allCoordinators) {
                    if (coordinator.getFirstName().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)) || coordinator.getLastName().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)) || String.valueOf(coordinator.getPhoneNumber()).contains(filterSearchCoordinator.getText()) || coordinator.getEmail().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)) || coordinator.getUserName().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)) || coordinator.getPassWord().toLowerCase().contains(filterSearchCoordinator.getText().toLowerCase(Locale.ROOT)))
                        search.add(coordinator);
                }
                coordinatorsTable.setItems(search);
            }
        });
    }

    public void newCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/SignUp.fxml"));
        root = loader.load();

        SignUpController controller = loader.getController();
        controller.setCustomerModel(customerModel);
        controller.setCoordinatorModel(coordinatorModel);
        controller.setCoordinator(false);
        controller.checkTheBox();
        controller.disableUsernamePassword();
        Stage stage = new Stage();
        stage.setTitle("New customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        setSelectedCustomer(customersTable.getSelectionModel().getSelectedItem());
        if (getAllCustomers() == null)
            return;
        customerModel.deleteCustomer(getSelectedCustomer());
        allCustomers.remove(getSelectedCustomer());
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
        SignUpController controller = loader.getController();
        controller.setCoordinatorModel(coordinatorModel);
        controller.setCustomerModel(customerModel);
        controller.checkTheBox();
        Stage stage = new Stage();
        stage.setTitle("New Coordinator");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteCoordinator(ActionEvent actionEvent) throws SQLException {
        setSelectedCoordinator(coordinatorsTable.getSelectionModel().getSelectedItem());
        coordinatorModel.deleteCoordinator(getSelectedCoordinator());
        allCoordinators.remove(getSelectedCoordinator());
        setUpCoordinatorsTable();
    }

    public void setUpCustomersTable() throws SQLException {
        customersTable.setEditable(true);
        FNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        FNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        FNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
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
        LNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        LNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        LNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
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
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        EmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        EmailColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
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

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    test = Integer.parseInt(string);
                } catch (NumberFormatException nfe) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Number format exception");
                    alert.setContentText("Please find a number for your coordinator.");
                    alert.showAndWait();
                }
                return test;
            }
        }));
        phoneNumberColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, Integer> event) {
                Customer customer = event.getRowValue();
                if (test >= 0) {
                    customer.setPhoneNumber(event.getNewValue());
                    try {
                        customerModel.editCustomer(customer);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    test = 1;
                    customersTable.refresh();
                }
            }
        });
        customersTable.setItems(getAllCustomers());
    }

    public void setUpCoordinatorsTable() {

        coordinatorsTable.setEditable(true);

        FNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        FNameCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        FNameCoordinatorColumn.setOnEditCommit(new EventHandler<>() {
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
        LNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        LNameCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        LNameCoordinatorColumn.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coordinator, String> event) {
                Coordinator coordinator = event.getRowValue();
                coordinator.setLastName(event.getNewValue());
                try {
                    customerModel.editCoordinator(coordinator);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        UNameCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        UNameCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        UNameCoordinatorColumn.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coordinator, String> event) {
                Coordinator coordinator = event.getRowValue();
                coordinator.setUserName(event.getNewValue());
                try {
                    customerModel.editCoordinator(coordinator);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        PassWordCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("passWord"));
        PassWordCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        PassWordCoordinatorColumn.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coordinator, String> event) {
                Coordinator coordinator = event.getRowValue();
                coordinator.setPassWord(event.getNewValue());
                try {
                    customerModel.editCoordinator(coordinator);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        EmailCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        EmailCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        EmailCoordinatorColumn.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coordinator, String> event) {
                Coordinator coordinator = event.getRowValue();
                coordinator.setEmail(event.getNewValue());
                try {
                    customerModel.editCoordinator(coordinator);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        phoneNumberCoordinatorColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberCoordinatorColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    test = Integer.parseInt(string);
                } catch (NumberFormatException nfe) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Number format exception");
                    alert.setContentText("Please find a number for your coordinator.");
                    alert.showAndWait();
                }
                return test;
            }
        }));
        phoneNumberCoordinatorColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Coordinator, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Coordinator, Integer> event) {
                Coordinator coordinator = event.getRowValue();
                if (test >= 0) {
                    coordinator.setPhoneNumber(event.getNewValue());
                    try {
                        coordinatorModel.editCoordinator(coordinator);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    test = 1;
                    coordinatorsTable.refresh();
                }
            }

        });
        coordinatorsTable.setItems(getAllCoordinators());
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
}
