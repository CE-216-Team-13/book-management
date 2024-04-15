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

public class MenuController implements Initializable{
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox vBox;
    @FXML
    private MenuItem importMenu;

    private GridPane gridPane;
    @FXML
    protected void onSearchButtonClick() {
        if (!searchBar.getText().isBlank()) {
            gridPane.getChildren().clear();
            ArrayList<Book> books = Library.getInstance().getBooks();
            ArrayList<Book> results = new ArrayList<>();
            int index = 0;
            for (Book book: books) {
                System.out.println();
                String data = book.getTitle();
                if (data.toLowerCase().contains(searchBar.getText().toLowerCase())) {
                    results.add(book);
                    VBox bookBox = new VBox();
                    Button deleteButton = new Button("Delete");
                    Button editButton = new Button("Edit");
                    deleteButton.setOnAction(actionEvent -> {try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete_Window.fxml"));
                        Parent root = loader.load();
                        DeleteController deleteController = loader.getController();
                        deleteController.setLocationAndBook(book.getLocation(), book);
                        System.out.println(book.getLocation());

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));

                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }});

                    editButton.setOnAction(actionEvent -> {try {
                        FXMLLoader editLoader = new FXMLLoader(getClass().getResource("Edit_Window.fxml"));
                        Parent editRoot = editLoader.load();
                        EditController editController = editLoader.getController();
                        editController.setLocationAndBook(book.getLocation(), book);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(editRoot));
                        stage.show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    // IMPORT LOGIC GOES HERE importMenu.setOnAction(actionEvent {});
                    bookBox.getChildren().add(new Label("Title: " + book.getTitle()));
                    bookBox.getChildren().add(new Label("Subtitle: " + book.getSubtitle()));
                    bookBox.getChildren().add(deleteButton);
                    bookBox.getChildren().add(editButton);
                    GridPane.setRowIndex(bookBox, index);
                    gridPane.getChildren().add(bookBox);
                    bookBox.setStyle("-fx-border-color: blue; -fx-focus-color: blue;");
                    index++;
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        VBox.setVgrow(gridPane, Priority.ALWAYS);
        VBox.setVgrow(vBox, Priority.ALWAYS);

        vBox.getChildren().add(gridPane);
    }
}