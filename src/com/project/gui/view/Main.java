package com.project.gui.view;

import com.project.gui.controller.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private StringProperty layoutChosen;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        layoutChosen = new SimpleStringProperty("");
        initLogin();
    }
    public void initLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SingInView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        SignInController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle("Main window");
        primaryStage.show();
    }
    public void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class
                .getResource("RootLayout.fxml"));
        AnchorPane rootLayout = loader.load();

        if(layoutChosen.get().equals("admin")) {
            FXMLLoader loaderAdmin = new FXMLLoader();
            loaderAdmin.setLocation(getClass().getResource("AdminView.fxml"));
            GridPane adminDisplay = loaderAdmin.load();
            AdminController controller = loaderAdmin.getController();
            controller.setMainApp(this);
            rootLayout.getChildren().setAll(adminDisplay);
            primaryStage.setTitle("Admin window");

        }
        if(layoutChosen.get().equals("coordinator")) {
            FXMLLoader loaderCoordinator = new FXMLLoader();
            loaderCoordinator.setLocation(getClass().getResource("CoordinatorView.fxml"));
            VBox coordinatorDisplay = loaderCoordinator.load();
            rootLayout.getChildren().add(coordinatorDisplay);
            primaryStage.setTitle("coordinator window");
        }

        if(layoutChosen.get().equals("customer")) {
            FXMLLoader loaderCustomer = new FXMLLoader();
            loaderCustomer.setLocation(getClass().getResource("CustomerView.fxml"));
            AnchorPane customerDisplay = loaderCustomer.load();
            rootLayout.getChildren().add(customerDisplay);
            primaryStage.setTitle("Customer window");
        }

        if(layoutChosen.get().equals("events")) {
            FXMLLoader loaderEvent = new FXMLLoader();
            loaderEvent.setLocation(getClass().getResource("ManageEvents.fxml"));
            AnchorPane eventsDisplay = loaderEvent.load();
            ManageEventsController controller = loaderEvent.getController();
            controller.setMain(this);
            rootLayout.getChildren().add(eventsDisplay);
            primaryStage.setTitle("Manage events window");
        }

        if(layoutChosen.get().equals("users")) {
            FXMLLoader loaderUser = new FXMLLoader();
            loaderUser.setLocation(getClass().getResource("ManageUsers.fxml"));
            TabPane usersDisplay = loaderUser.load();
            ManageUsersViewController controller = loaderUser.getController();
            controller.setMain(this);
            rootLayout.getChildren().add(usersDisplay);
            primaryStage.setTitle("Manage users window");
        }

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        // Give the controller access to the main app.
        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }

    public void setLayoutChosen(String layoutChosen) {
        this.layoutChosen.set(layoutChosen);
    }
}
