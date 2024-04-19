package app.bookmanagementapp;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportBookScreen {
    private Book book;
    private Stage stage;

    public ImportBookScreen(Stage stage) {
        this.stage = stage;
    }

    public ImportBookScreen() {
    }

    public Stage getStage() {
        return stage;
    }

    public void start(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        String a = selectedFile.getAbsolutePath();
        //System.out.println(a);
        if (selectedFile != null) {
            try {
                FileReader reader = new FileReader(selectedFile);
                JSONObject j = new JSONObject((new JSONTokener(reader)));
                this.book = new Book(j);
                try {
                    String filename = selectedFile.getName();
                    book.setLocation(a);
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
                    json.put("authors", book.getAuthors());
                    json.put("translators", book.getTranslators());
                    json.put("tags", book.getTags());
                    FileWriter fw = new FileWriter(directory.toString());
                    fw.write(json.toString());
                    fw.close();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stage.close();
    }
}
