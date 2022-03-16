package com.project.gui.view;

import com.project.gui.controller.RootLayoutController;
import com.project.gui.controller.SignInController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
        BorderPane rootLayout = loader.load();

        if(layoutChosen.get().equals("admin")) {
            FXMLLoader loaderStudent = new FXMLLoader();
            loaderStudent.setLocation(getClass().getResource("AdminView.fxml"));
            AnchorPane adminDisplay = loaderStudent.load();
            rootLayout.getChildren().add(adminDisplay);
            primaryStage.setTitle("Admin window");

        }
        if(layoutChosen.get().equals("coordinator")) {
            FXMLLoader loaderTeacher = new FXMLLoader();
            loaderTeacher.setLocation(getClass().getResource("CoordinatorView.fxml"));
            AnchorPane coordinatorDisplay = loaderTeacher.load();
            rootLayout.getChildren().add(coordinatorDisplay);
            primaryStage.setTitle("coordinator window");
        }

        if(layoutChosen.get().equals("cdustomer")) {
            FXMLLoader loaderTeacher = new FXMLLoader();
            loaderTeacher.setLocation(getClass().getResource("CustomerView.fxml"));
            AnchorPane customerDisplay = loaderTeacher.load();
            rootLayout.getChildren().add(customerDisplay);
            primaryStage.setTitle("Customer window");
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
