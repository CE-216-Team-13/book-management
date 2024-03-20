package app.bookmanagementapp;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Library {
    private ArrayList<String> books;
    private Paths directory;
    private JsonController controller;

    public ArrayList<String> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }

    public Paths getDirectory() {
        return directory;
    }

    public void setDirectory(Paths directory) {
        this.directory = directory;
    }

    public JsonController getController() {
        return controller;
    }

    public void setController(JsonController controller) {
        this.controller = controller;
    }
}

