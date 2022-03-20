package com.project.gui.controller;

import com.project.gui.view.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.CustomPasswordField;
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

    private Main main;
    public void setMainApp(Main main) {
        this.main=main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Node usersIcons= GlyphsDude.createIcon(FontAwesomeIcons.USERS,"35px");
       usersIcons.setLayoutX(152);
       usersIcons.setLayoutY(101);
    anchorPaneRight.getChildren().add(usersIcons);

        Node keyIcon= GlyphsDude.createIcon(FontAwesomeIcons.LOCK,"20px");
        keyIcon.setLayoutX(69);
        keyIcon.setLayoutY(253);
        anchorPaneRight.getChildren().add(keyIcon);

        Node userIcon= GlyphsDude.createIcon(FontAwesomeIcons.USER,"20px");
        userIcon.setLayoutX(70);
        userIcon.setLayoutY(200);
        anchorPaneRight.getChildren().add(userIcon);

        Node calendarIcon= GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR,"35px");
        calendarIcon.setLayoutX(153);
        calendarIcon.setLayoutY(101);
        anchorPaneLeft.getChildren().add(calendarIcon);
    }

    public void logIn(ActionEvent actionEvent) throws Exception {
        if (userName.getText().equals("admin")&&passWord.getText().equals("admin")){
            main.setLayoutChosen("admin");
            main.initRootLayout();
        }
        if (userName.getText().equals("coordinator")&&passWord.getText().equals("coordinator")){
            main.setLayoutChosen("coordinator");
            main.initRootLayout();
        }
        if (userName.getText().equals("customer")&&passWord.getText().equals("customer")){
            main.setLayoutChosen("customer");
            main.initRootLayout();
        }
    }
}
