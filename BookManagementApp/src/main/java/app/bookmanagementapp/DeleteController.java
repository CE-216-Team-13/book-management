package app.bookmanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteController {

    @FXML
    private Label label;

    @FXML
    protected void onCancelButtonClick() {
        label.setText("WORKS!");
    }

}
