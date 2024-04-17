package app.bookmanagementapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox vBox;
    @FXML
    private MenuItem importMenu;
    private ImportBookScreen importscreen;
    private addBookScreen addbook;
    private GridPane gridPane;

    @FXML
    protected void onSearchButtonClick() {

    }

    @FXML
    protected void onImportButtonClick() throws Exception {
        importscreen = new ImportBookScreen();
        importscreen.start(new Stage());
    }
    @FXML
    protected void onCreateButtonClick() throws IOException {
        addbook = new addBookScreen();
        addbook.start(new Stage());
}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}