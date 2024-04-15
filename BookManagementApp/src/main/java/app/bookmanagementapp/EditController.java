package app.bookmanagementapp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    private String location;
    private Book book;
    public void setLocationAndBook(String location, Book book) {
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

    private ToggleGroup toggleGroup;
    @FXML
    private ListView<String> authorsList, translatorsList, tagsList;
    String INVALID = "-fx-border-color: red; -fx-focus-color: red;"; // Style for highlighting with red

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setEditable(false);
        toggleGroup = new ToggleGroup();
        hardCover.setToggleGroup(toggleGroup);
        paperback.setToggleGroup(toggleGroup);
        hardCover.setSelected(true);

        //THE TWO LINES BELOW ARE PLACEHOLDERS FOR TESTING. THEY ARE GOING TO BE DELETED SOON.
        Book testBook = new Book();
        this.setLocationAndBook("BookManagementApp\\books\\test.json", testBook);


        title.setText(book.getTitle());
        subtitle.setText(book.getSubtitle());
        isbn.setText(book.getIsbn());
        try {
            if (!Float.toString(book.getRating()).isBlank()) {
                rating.setText(Float.toString(book.getRating()));
            }
            else {
                System.out.println("Error getting the rating: no rating");
            }

        }
        catch (Exception e) {
            System.out.println("Error getting the rating: invalid rating");
        }
        try {
            for (String author: book.getAuthors()) {
                authorsList.getItems().add(author);
            }
        }
        catch (Exception e) {
            System.out.println("Error getting the authors");
        }
        try {
            for (String translator: book.getTranslators()) {
                translatorsList.getItems().add(translator);
            }
        }
        catch (Exception e) {
            System.out.println("Error getting the translators");
        }
        try {
            for (String tag: book.getTags()) {
                tagsList.getItems().add(tag);
            }
        }
        catch (Exception e) {
            System.out.println("Error getting the tags");
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
            System.out.println("Error getting the date");
        }
        try {
            if (book.getCover().equalsIgnoreCase("paperback")) {
                hardCover.setSelected(false);
                paperback.setSelected(true);
            }
        }
        catch (Exception e) {
            System.out.println("Error getting the cover type");
        }
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
        if (isValidTitle() && isValidAuthors()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", title.getText());
                jsonObject.put("subtitle", subtitle.getText());
                jsonObject.put("authors", authorsList.getItems());
                jsonObject.put("translators", translatorsList.getItems());
                jsonObject.put("isbn", isbn.getText());
                jsonObject.put("publisher", publisher.getText());
                if (datePicker.getValue() == null) {
                    jsonObject.put("date", "");
                }
                else {
                    jsonObject.put("date", datePicker.getValue().toString());
                }
                if (!isValidEdition(jsonObject)) {
                    return;
                }

                if (!isValidRating(jsonObject)) {
                    return;
                }

                jsonObject.put("cover", ((RadioButton) toggleGroup.getSelectedToggle()).getText());
                jsonObject.put("language", language.getText());
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
                FileWriter writer = new FileWriter(this.location);
                writer.write(jsonObject.toString(4));
                writer.flush();
                writer.close();

            }
            catch (Exception e){
                System.out.println("Please check the fields and make sure they are filled in correctly.");
            }
        }

    }
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //Gets the window of the scene the button belongs
        stage.close();
    }

    private boolean isValidTitle() {
        try {
            if (title.getText().isBlank()) {
                title.setStyle(INVALID);
                return false;
            }
            title.setStyle("");
            return true;
        }
        catch (Exception e) {
            title.setStyle(INVALID);
            return false;
        }
    }
    private boolean isValidAuthors() {
        try {
            if (authorsList.getItems().isEmpty()) {
                authorsList.setStyle(INVALID);
                authors.setStyle(INVALID);
                return false;
            }
            authors.setStyle("");
            authorsList.setStyle("");
            return true;
        }
        catch (Exception e) {
            authorsList.setStyle(INVALID);
            authors.setStyle(INVALID);
            return false;
        }
    }
    private boolean isValidEdition(JSONObject jsonObject) {
        try {
            int checker = Integer.parseInt(edition.getText());
            if (checker > 0) {
                edition.setStyle("");
                jsonObject.put("edition", Integer.parseInt(edition.getText()));
                return true;
            }
            else {
                edition.setStyle(INVALID);
                return false;
            }
        }
        catch (Exception e) {
            if (edition.getText() == null || edition.getText().isBlank()) {
                edition.setStyle("");
                jsonObject.put("edition", "");
                return true;
            }
            else {
                edition.setStyle(INVALID);
                return false;
            }
        }
    }
    private boolean isValidRating(JSONObject jsonObject) {
        try {
            float checker = Float.parseFloat(rating.getText());
            if (checker >= 0 && checker <= 5 && (Float.compare(checker, -0.0f) != 0)) {
                rating.setStyle("");
                jsonObject.put("rating", Math.round(Float.parseFloat(rating.getText()) * 10) / 10f);
                return true;
            }
            else {
                rating.setStyle(INVALID);
                return false;
            }
        }
        catch (Exception e) {
            if (rating.getText() == null || rating.getText().isBlank()) {
                rating.setStyle("");
                jsonObject.put("rating", 0.0f);
                return true;
            }
            else {
                rating.setStyle(INVALID);
                return false;
            }
        }
    }
}