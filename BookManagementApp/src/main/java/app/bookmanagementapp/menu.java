package com.example.menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(menu.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 240);
        stage.setTitle("Book Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {



        launch();
    }


}
