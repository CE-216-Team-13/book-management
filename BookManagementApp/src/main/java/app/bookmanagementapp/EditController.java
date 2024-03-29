package app.bookmanagementapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    private String location;
    private Book book;
    private void setLocationAndBook(String location, Book book) {
        this.location = location;
        this.book = book;
    }
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField title, subtitle, isbn, rating, authors, translators, tags, publisher, edition, language;
    @FXML
    private RadioButton hardCover, paperback;
    private String cover;

    private ToggleGroup toggleGroup;
    @FXML
    private ListView<String> authorsList, translatorsList, tagsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setEditable(false);
        toggleGroup = new ToggleGroup();
        hardCover.setToggleGroup(toggleGroup);
        paperback.setToggleGroup(toggleGroup);
        hardCover.setSelected(true);

        /*      BELOW CODE IS NOT READY TO USE, DO NOT UNCOMMENT.
        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitle());
        isbn.setText(book.getIsbn());
        rating.setText(Float.toString(book.getRating()));
        for (String author: book.getAuthors()) {
            authorsList.getItems().add(author);
        }
        for (String translator: book.getTranslators()) {
            translatorsList.getItems().add(translator);
        }
        for (String tag: book.getTags()) {
            tagsList.getItems().add(tag);
        }
        publisher.setText(book.getPublisher());
        edition.setText(book.getEdition());
        language.setText(book.getLanguage());
        try {
            String currDate = book.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(currDate, formatter);
            datePicker.setValue(localDate);
        }
        catch (Exception e) {
            System.out.println("Error getting the date.");
        }
        if (book.getCover().equalsIgnoreCase("paperback")) {
            hardCover.setSelected(false);
            paperback.setSelected(true);
        }*/


    }

    @FXML
    protected void onAuthorSubmitClick() {
        if (!authors.getText().isBlank()) {
            authorsList.getItems().add(authors.getText());
        }
        authors.setText("");
    }
    @FXML
    protected void onTranslatorSubmitClick() {
        if (!translators.getText().isBlank()) {
            translatorsList.getItems().add(translators.getText());
        }
        translators.setText("");
    }
    @FXML
    protected void onTagSubmitClick() {
        if (!tags.getText().isBlank()) {
            tagsList.getItems().add(tags.getText());
        }
        tags.setText("");
    }

    @FXML
    protected void onSaveButtonClick() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title.getText());
            jsonObject.put("subtitle", subtitle.getText());
            jsonObject.put("authors", authorsList.getItems());
            jsonObject.put("translators", translatorsList.getItems());
            jsonObject.put("isbn", isbn.getText());
            jsonObject.put("publisher", publisher.getText());
            jsonObject.put("date", datePicker.getValue().toString());
            jsonObject.put("edition", Integer.parseInt(edition.getText()));//TO-DO: Handle the empty state
            jsonObject.put("cover", ((RadioButton) toggleGroup.getSelectedToggle()).getText());
            jsonObject.put("language", language.getText());
            jsonObject.put("rating", Float.parseFloat(rating.getText())); //TO-DO: Handle the empty state
            jsonObject.put("tags", tagsList.getItems());
            book = new Book(jsonObject);
            System.out.println(book.getTitle());
            System.out.println(book.getSubtitle());
            System.out.println(book.getAuthors());
            System.out.println(book.getTranslators());
            System.out.println(book.getIsbn());
            System.out.println(book.getPublisher());
            System.out.println(book.getDate());
            System.out.println(book.getEdition());
            System.out.println(book.getCover());
            System.out.println(book.getLanguage());
            System.out.println(book.getRating());
            System.out.println(book.getTags());
            FileWriter writer = new FileWriter("BookManagementApp\\books\\test.json");writer.write(jsonObject.toString(4));
            writer.flush();

        }
        catch (Exception e){
            System.out.println("Please check the fields and make sure they are filled in correctly.");
        }
    }
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window of the scene the button belongs
        stage.close();
    }
}