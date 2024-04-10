package com.example.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class menuController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private Label welcomeText;


   

    @FXML
    private Button btnok;

    @FXML
    private MenuItem closee;

    Stage stage;
    @FXML
    void logout(ActionEvent event) {

        stage=(Stage) root.getScene().getWindow();
        System.out.println("You successfully logged out!");
        stage.close();


    }

    @FXML
    void openabout(ActionEvent event) {

        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("Welcome to the book management application. You can choose the action you want to take. Click 'Search' button to find any book. Click 'Add' button to add a new book. Click 'Tags' button to filter books. Click 'Delete' button to delete a book");
        alert.setHeaderText("Information");
        alert.showAndWait();

    }

}
