package app.bookmanagementapp;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.File;
import java.io.FileReader;

public class importBookScreen extends Application {
    private Book book;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        String a = selectedFile.getAbsolutePath();
        System.out.println(a);
        if (selectedFile != null) {
            try {
                FileReader reader = new FileReader(selectedFile);
                JSONObject j = new JSONObject((new JSONTokener(reader)));
                book = new Book(j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stage.close();
    }
}
