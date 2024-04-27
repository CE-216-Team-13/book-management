package app.bookmanagementapp;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class addBookScreen extends Application {
    private String imagePath;
    private String location;
    private TextField TFtitle = new TextField();
    private TextField TFsubtitle = new TextField();
    private TextField TFauthors = new TextField();
    private TextField TFtranslator = new TextField();
    private TextField TFpublisher = new TextField();
    private TextField TFedition = new TextField();
    private TextField TFlanguage = new TextField();
    private TextField TFcover = new TextField();
    private TextField TFisbn = new TextField();
    private TextField TFtags = new TextField();
    private TextField TFimage = new TextField();
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
    Label Limage = new Label("Image");
    private Book book = new Book();

    @Override
    public void start(Stage stage) throws IOException {
        DPdate.setEditable(false);
        stage.setMinHeight(510);
        stage.setMinWidth(310);
        stage.setResizable(true);
        VBox main = new VBox();
        Scene scene = new Scene(main, 400, 400);
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
        GPdetails.addRow(13, Limage, TFimage);
        TFimage.setEditable(false);
        GPdetails.setHgap(10);
        GPdetails.setVgap(8);
        GPdetails.setAlignment(Pos.CENTER);
        ColumnConstraints left = new ColumnConstraints();
        ColumnConstraints right = new ColumnConstraints();
        left.setHgrow(Priority.ALWAYS);
        left.setFillWidth(true);
        left.setPercentWidth(15);
        right.setHgrow(Priority.ALWAYS);
        right.setFillWidth(true);
        right.setPercentWidth(85);
        left.setFillWidth(true);
        right.setFillWidth(true);
        GPdetails.getColumnConstraints().addAll(left, right);
        ArrayList<TextField> textfields = new ArrayList<>(Arrays.asList(TFtitle, TFsubtitle, TFauthors, TFtranslator, TFpublisher, TFedition, TFlanguage, TFpublisher, TFcover, TFisbn, TFtags, TFimage));
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(Ltitle, Lsubtitle, Lauthors, Ltranslator, Lpusblisher, Ledition, Llanguage, Ldate, Lcover, Lisbn, Lrating, Ltags, Limage));
        for (TextField tf : textfields) {
            GridPane.setHgrow(tf, Priority.ALWAYS);
            GridPane.setVgrow(tf, Priority.ALWAYS);

        }
        for (Label l : labels) {
            GridPane.setHgrow(l, Priority.ALWAYS);
            GridPane.setVgrow(l, Priority.ALWAYS);
        }
        GridPane.setHgrow(DPdate, Priority.ALWAYS);
        GridPane.setVgrow(DPdate, Priority.ALWAYS);
        GridPane.setHgrow(Srating, Priority.ALWAYS);
        GridPane.setVgrow(Srating, Priority.ALWAYS);
        GridPane.setHgrow(GPdetails, Priority.ALWAYS);
        GridPane.setVgrow(GPdetails, Priority.ALWAYS);
        HBdetails.getChildren().addAll(GPdetails);
        HBdetails.setFillHeight(true);
        //    HBbuttons.setFillHeight(true);
        main.setFillWidth(true);
        HBox.setHgrow(GPdetails, Priority.ALWAYS);
        Button Bcancel = new Button("Cancel");
        Bcancel.setOnAction(e -> stage.close());
        Button Bok = new Button("OK");
        Bok.setOnAction(e -> addBook(stage));
        Button Bimage = new Button("Image ");
        Bimage.setOnAction(e -> addImage(stage));
        Bcancel.minWidthProperty().bind(stage.widthProperty().divide(3));
        Bok.minWidthProperty().bind(stage.widthProperty().divide(3));
        Bimage.minWidthProperty().bind(stage.widthProperty().divide(3));
        Bcancel.minHeightProperty().bind(HBbuttons.heightProperty());
        Bok.minHeightProperty().bind(HBbuttons.heightProperty());
        Bimage.minHeightProperty().bind(HBbuttons.heightProperty());
        HBbuttons.getChildren().addAll(Bcancel, Bok, Bimage);
        //   HBox.setHgrow(HBbuttons, Priority.ALWAYS);
        HBox.setHgrow(main, Priority.ALWAYS);
        HBbuttons.setAlignment(Pos.BOTTOM_CENTER);
        HBbuttons.maxHeightProperty().bind(scene.heightProperty().divide(20));
        VBox.setVgrow(main, Priority.ALWAYS);
        VBox.setVgrow(HBdetails, Priority.ALWAYS);
        VBox.setVgrow(GPdetails, Priority.ALWAYS);
        VBox.setVgrow(HBbuttons, Priority.ALWAYS);
        main.getChildren().addAll(HBdetails, HBbuttons);
        stage.setScene(scene);
        stage.show();
    }

    public String generateName() {
        location = "book_" + System.currentTimeMillis() + ".json";
        return location;
    }

    public void addImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Path path = file.toPath();
                int index = file.getName().lastIndexOf('.');
                if (index == -1) {
                    return;
                }
                String fileName = System.currentTimeMillis() + file.getName().substring(index);
                Path destPath = Path.of("BookManagementApp/images", fileName);
                System.out.println("CURSOR WAITS");
                stage.setOnCloseRequest(event -> event.consume());
                Task<Void> copyImage = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        imagePath = "file:BookManagementApp/images/" + fileName;
                        Files.copy(path, destPath, StandardCopyOption.REPLACE_EXISTING);
                        return null;
                    }
                };
                System.out.println("CURSOR DEFAULT");
                copyImage.setOnSucceeded(e -> {
                    stage.setOnCloseRequest(null);
                });
                copyImage.setOnFailed(e -> {
                    stage.setOnCloseRequest(null);
                    Throwable throwable = copyImage.getException();
                    throwable.printStackTrace();
                });
                Thread thread = new Thread(copyImage);
                thread.start();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void addBook(Stage stage) {

        book.setTitle(String.valueOf(TFtitle.getText()));


        book.setSubtitle(String.valueOf(TFsubtitle.getText()));


        String text = TFauthors.getText();
        ArrayList<String> a1 = new ArrayList<>(List.of(text.split(";")));
        for (int i = 0; i < a1.size(); i++) {
            a1.set(i, a1.get(i).trim());
        }
        if (a1.equals(new ArrayList<String>(List.of("")))) {
            book.setAuthors(new ArrayList<String>());
        }
        else {
            book.setAuthors(a1);
        }



        text = TFtranslator.getText();
        a1 = new ArrayList<>(List.of(text.split(";")));
        for (int i = 0; i < a1.size(); i++) {
            a1.set(i, a1.get(i).trim());
        }
        if (a1.equals(new ArrayList<String>(List.of("")))) {
            book.setTranslators(new ArrayList<String>());
        }
        else {
            book.setTranslators(a1);
        }

        book.setPublisher(String.valueOf(TFpublisher.getText()));


        book.setEdition(String.valueOf(TFedition.getText()));


        book.setLanguage(String.valueOf(TFlanguage.getText()));


        if (DPdate.getValue() != null) {
            try {
                book.setDate(DPdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeParseException d) {
                book.setDate("");
            }
        } else {
            book.setDate("");
        }

        book.setCover(String.valueOf(TFcover.getText()));


        book.setIsbn(String.valueOf(TFisbn.getText()));


        book.setRating(Float.parseFloat(Srating.getValue().toString()));

        text = TFtags.getText();
        a1 = new ArrayList<>(List.of(text.split(";")));
        for (int i = 0; i < a1.size(); i++) {
            a1.set(i, a1.get(i).trim());
        }
        if (a1.equals(new ArrayList<String>(List.of("")))) {
            book.setTags(new ArrayList<String>());
        }
        else {
            book.setTags(a1);
        }


        book.setImage(imagePath);

        try {
            String filename = generateName();
            book.setLocation(location);
            File d = new File("BookManagementApp\\books");
            if (!d.exists()) {
                d.mkdir();
            }
            Path directory = Paths.get("BookManagementApp\\books", filename);
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
            json.put("image", book.getImage());

            json.put("authors", book.getAuthors());


            json.put("translators", book.getTranslators());


            json.put("tags", book.getTags());

            FileWriter fw = new FileWriter(directory.toString());
            fw.write(json.toString());
            fw.close();
        } catch (Exception a) {
            a.printStackTrace();
        }

        stage.close();

    }

    public static void main(String[] args) {
        launch();
    }

}