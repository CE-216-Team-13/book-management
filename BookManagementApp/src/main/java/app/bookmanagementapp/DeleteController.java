package app.bookmanagementapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {
    private String location;
    private Book book;
    public void setLocationAndBook(String location, Book book) {
        this.location = location;
        this.book = book;
    }
    @FXML
    private Label label;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    @FXML
    protected void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window of the scene the button belongs
        stage.close();
    }
    @FXML
    protected void onDeleteButtonClick() {
        Library.getInstance().getBooks().remove(book);
        File file = new File(location);
        file.delete();
        Library.getInstance().getBooks().remove(book);
        label.setText("WORKS!");
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setLocationAndBook("BookManagementApp\\books\\test.json", Library.getInstance().getBooks().get(0)); // THIS IS A PLACEHOLDER
    }
}
