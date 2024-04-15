package app.bookmanagementapp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox vbox;
    @FXML
    protected void onSearchButtonClick() {
        if (!searchBar.getText().isBlank()) {
            ArrayList<Book> books = Library.getInstance().getBooks();
            for (Book book: books) {
                String data = book.getDate() + book.getEdition() + book.getIsbn() + book.getLanguage() + book.getPublisher() + book.getSubtitle() + book.getTitle() + book.getAuthors().toString() + book.getTranslators().toString() + book.getTags().toString();
                if (data.contains(searchBar.getText())) {

                }
            }
        }
    }
}