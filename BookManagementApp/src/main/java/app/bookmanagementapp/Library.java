package app.bookmanagementapp;

import org.json.JSONObject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Library {
    private static Library library;
    private ArrayList<Book> books;
    private ArrayList<String> tags;
    private Path directory;


    private Library() {}

    public static synchronized Library getInstance() {
        if (library == null) {
            library = new Library();
        }
        library.setDirectory(Paths.get("BookManagementApp/books"));
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        library.books = new ArrayList<Book>();
        library.tags = new ArrayList<String>();

        try (Stream<Path> stream = Files.walk(library.directory)){
            List<Path> files = stream.filter(Files::isRegularFile).toList();
            for (Path path: files) {
                JSONObject jsonObject = new JSONObject(Files.readString(path));
                Book book = new Book(jsonObject);
                book.setLocation(path.toString());
                library.books.add(book);
                for (String tag: book.getTags()) {
                    if (!library.tags.contains(tag.toLowerCase())) {
                        library.tags.add(tag.toLowerCase());
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return library;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Path getDirectory() {
        return directory;
    }

    public void setDirectory(Path directory) {
        this.directory = directory;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Book> search(String str) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book: books) {
            if (book.getTitle().toLowerCase().contains(str.toLowerCase()) ||
                book.getSubtitle().toLowerCase().contains(str.toLowerCase()) ||
                    book.getIsbn().toLowerCase().contains(str.toLowerCase()) ||
                    book.getPublisher().toLowerCase().contains(str.toLowerCase()) ||
                    book.getDate().toLowerCase().contains(str.toLowerCase()) ||
                    book.getCover().toLowerCase().contains(str.toLowerCase()) ||
                    book.getLanguage().toLowerCase().contains(str.toLowerCase()) ||
                    book.getEdition().toLowerCase().contains(str.toLowerCase()) ||
                    Float.toString(book.getRating()).toLowerCase().contains(str.toLowerCase()) ||
                    book.getAuthors().stream().anyMatch(author -> author.toLowerCase().contains(str.toLowerCase())) ||
                    book.getTranslators().stream().anyMatch(translator -> translator.toLowerCase().contains(str.toLowerCase())) ||
                    book.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(str.toLowerCase()))
            ) {
                results.add(book);
            }
        }
        return results;
    }

    public ArrayList<Book> searchByTags(ArrayList<String> tags) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book: books) {
            if (book.getTags().stream().anyMatch(tags::contains)) {
                results.add(book);
            }
        }
        return results;
    }
}

