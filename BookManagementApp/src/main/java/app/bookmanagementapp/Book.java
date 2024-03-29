package app.bookmanagementapp;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;

public class Book {
    public Book(JSONObject object) {
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.trim().equalsIgnoreCase("title")) {
                this.title = object.getString(key);
                //     System.out.println(title);
            } else if (key.trim().equalsIgnoreCase("subtitle")) {
                this.subtitle = object.getString(key);
                //     System.out.println(subtitle);
            } else if (key.trim().equalsIgnoreCase("isbn")) {
                this.isbn = object.getString(key);
                //    System.out.println(isbn);
            } else if (key.trim().equalsIgnoreCase("publisher")) {
                this.publisher = object.getString(key);
                //    System.out.println(publisher);
            } else if (key.trim().equalsIgnoreCase("date")) {
                this.date = object.getString(key);
                //   System.out.println(date);
            } else if (key.trim().equalsIgnoreCase("cover")) {
                this.cover = object.getString(key);
                //    System.out.println(cover);
            } else if (key.trim().equalsIgnoreCase("language")) {
                this.language = object.getString(key);
                //    System.out.println(language);
            } else if (key.trim().equalsIgnoreCase("image")) {
                this.image = object.getString(key);
                //    System.out.println(image);
            } else if (key.trim().equalsIgnoreCase("authors")) {
                ArrayList<String> authors = new ArrayList<>();
                JSONArray authorsJson = object.getJSONArray("authors");
                for (Object s : authorsJson) {
                    authors.add(s.toString());
                    //     System.out.println(s.toString());
                }
                this.authors = authors;
            } else if (key.trim().equalsIgnoreCase("translators")) {
                ArrayList<String> translators = new ArrayList<>();
                JSONArray translatorsJson = object.getJSONArray("translators");
                for (Object s : translatorsJson) {
                    translators.add(s.toString());
                    //     System.out.println(s.toString());
                }
                this.translators = translators;
            } else if (key.trim().equalsIgnoreCase("tags")) {
                ArrayList<String> tags = new ArrayList<>();
                JSONArray tagsJson = object.getJSONArray("tags");
                for (Object s : tagsJson) {
                    tags.add(s.toString());
                    //   System.out.println(s.toString());
                }
                this.tags = tags;
            } else if (key.trim().equalsIgnoreCase("rating")) {
                this.rating = Float.parseFloat(object.get(key).toString());
                //   System.out.println(rating);
            } else if (key.trim().equalsIgnoreCase("edition")) {
                this.edition = object.get(key).toString();
                //  System.out.println(edition);
            }
        }
    }
    public Book() {

    }

    private String title, subtitle, isbn, publisher, date, cover, language, image, edition;
    private float rating;
    private ArrayList<String> authors, translators, tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public static void main(String args[]) {
        try {

            System.out.println(System.getProperty("user.dir"));
            FileReader reader = new FileReader("BookManagementApp\\books\\test.json");
            JSONObject j = new JSONObject((new JSONTokener(reader)));
            Book b = new Book(j);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

