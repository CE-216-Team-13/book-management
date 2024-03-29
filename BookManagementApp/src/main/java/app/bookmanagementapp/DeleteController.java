package app.bookmanagementapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteController {

    @FXML
    private Label label;
    @FXML
    private Button cancelButton;

    @FXML
    protected void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window of the scene the button belongs
        stage.close();
    }
    @FXML
    protected void onDeleteButtonClick() {
        //TO-DO: Implement deletion operation
        label.setText("WORKS!");
    }

}
