package app.bookmanagementapp;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addBookScreen extends Application {
    private TextField TFtitle = new TextField();
    private TextField TFsubtitle = new TextField();
    private TextField TFauthors = new TextField();
    private TextField TFtranslator = new TextField();
    private TextField TFpublisher = new TextField();
    private TextField TFedition = new TextField();
    private TextField TFlanguage = new TextField();
    private TextField TFcover = new TextField();
    private TextField TFisbn = new TextField();
    TextField TFtags = new TextField();
    private DatePicker DPdate = new DatePicker();
    private Spinner<Object> Srating = new Spinner<>(0, 5, 0, 0.1);
    private GridPane GPdetails = new GridPane();
    Label Ltitle = new Label("Title");
    Label Lsubtitle = new Label("Subtitle");
    Label Lauthors = new Label("Authors");
    Label Ltranslator = new Label("Translator");
    Label Lpusblisher = new Label("Publisher");
    Label Ledition = new Label("Edition");
    Label Llanguage = new Label("Language");
    Label Ldate = new Label("Date");
    Label Lcover = new Label("Cover");
    Label Lisbn = new Label("ISBN");
    Label Lrating = new Label("Rating");
    Label Ltags = new Label("Tags");

    private Book book = new Book();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinHeight(510);
        stage.setMinWidth(310);
        VBox main = new VBox();
        HBox HBdetails = new HBox();
        HBox HBbuttons = new HBox();

        GPdetails.setPadding(new Insets(10, 10, 10, 10));
        GPdetails.addRow(1, Ltitle, TFtitle);
        GPdetails.addRow(2, Lsubtitle, TFsubtitle);
        GPdetails.addRow(3, Lauthors, TFauthors);
        GPdetails.addRow(4, Ltranslator, TFtranslator);
        GPdetails.addRow(5, Lpusblisher, TFpublisher);
        GPdetails.addRow(6, Ledition, TFedition);
        GPdetails.addRow(7, Llanguage, TFlanguage);
        GPdetails.addRow(8, Ldate, DPdate);
        GPdetails.addRow(9, Lcover, TFcover);
        GPdetails.addRow(10, Lisbn, TFisbn);
        GPdetails.addRow(11, Lrating, Srating);
        GPdetails.addRow(12, Ltags, TFtags);
        GPdetails.setHgap(15);
        GPdetails.setVgap(10);
        GPdetails.setAlignment(Pos.CENTER);
        HBdetails.getChildren().addAll(GPdetails);
        HBdetails.fillHeightProperty().set(true);
        HBox.setHgrow(GPdetails, Priority.ALWAYS);
        Button Bcancel = new Button("Cancel");
        Bcancel.setOnAction(e -> stage.close());
        Button Bok = new Button("OK");
        Bok.setOnAction(e -> addBook(stage));
        Bcancel.setMinWidth(stage.getMinWidth() / 2);
        Bok.setMinWidth(stage.getMinWidth() / 2);
        HBbuttons.getChildren().addAll(Bcancel, Bok);
        HBbuttons.setAlignment(Pos.BOTTOM_CENTER);

        main.getChildren().addAll(HBdetails, HBbuttons);
        Scene scene = new Scene(main, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    public String generateName() {
        return "book_" + String.valueOf(System.currentTimeMillis()) + ".json";
    }

    public void addBook(Stage stage) {
        book.setTitle(String.valueOf(TFtitle.getText()));
        book.setSubtitle(String.valueOf(TFsubtitle.getText()));
        book.setAuthors(new ArrayList<>(List.of(String.valueOf(TFauthors.getText()).split(";"))));
        book.setTranslators(new ArrayList<>(List.of(String.valueOf(TFtranslator.getText()).split(";"))));
        book.setPublisher(String.valueOf(TFpublisher.getText()));
        book.setEdition(String.valueOf(TFedition.getText()));
        book.setLanguage(String.valueOf(TFlanguage.getText()));
        if (DPdate.getValue() != null) {
            try {
                book.setDate(DPdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeParseException d) {
                book.setDate("");
            }
        }
        book.setCover(String.valueOf(TFcover.getText()));
        book.setIsbn(String.valueOf(TFisbn.getText()));
        book.setRating(Float.parseFloat(Srating.getValue().toString()));
        book.setTags(new ArrayList<>(List.of(Arrays.toString(TFtags.getText().split(";")))));
        try {
            String filename = generateName();
            Path directory = Paths.get("books", filename);
            Files.createFile(directory);
            JSONObject json = new JSONObject();
            json.put("title", book.getTitle());
            json.put("subtitle", book.getSubtitle());
            json.put("isbn", book.getIsbn());
            json.put("publisher", book.getPublisher());
            json.put("date", book.getDate());
            json.put("cover", book.getCover());
            json.put("language", book.getLanguage());
            json.put("image", book.getImage());
            json.put("edition", book.getEdition());
            json.put("rating", book.getRating());
            json.put("authors", book.getAuthors());
            json.put("translators", book.getTranslators());
            json.put("tags", book.getTags());
            FileWriter fw = new FileWriter(directory.toString());
            fw.write(json.toString());
            fw.close();
        } catch (Exception a) {
        }

        stage.close();

    }

    public static void main(String[] args) {
        launch();
    }

}
