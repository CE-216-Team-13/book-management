package app.bookmanagementapp;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private MenuButton filterbytags;

    @FXML
    private TextField searchBar;
    @FXML
    private VBox vBox;
    @FXML
    private MenuItem importMenu;
    private ImportBookScreen importscreen;
    private addBookScreen addbook;
    private GridPane gridPane;
    private ArrayList<Book> results;

    private ArrayList<String> selectedTags = new ArrayList<>();

    private ScrollPane scrollPane;

    private ListView<String> tagsList;
    private EditController editController;


    @FXML
    private MenuItem closemenuitem;



    @FXML
    void clickclose(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void clickfilterbytags(ActionEvent event) {
        ArrayList<String> allTags = Library.getInstance().getTags();

        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Filter by Tags");
        dialog.setHeaderText("Select tags to filter books");

        VBox vbox = new VBox();

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        for (String tag : allTags) {
            CheckBox checkBox = new CheckBox(tag);
            checkBoxes.add(checkBox);
            vbox.getChildren().add(checkBox);
        }

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ArrayList<String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            ArrayList<String> selectedTags = new ArrayList<>();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    selectedTags.add(checkBox.getText());
                }
            }

            ArrayList<Book> filteredBooks = Library.getInstance().searchByTags(selectedTags);
            displayBooks(filteredBooks, 4);
        }
    }


    @FXML
    protected void onSearchButtonClick() {
        String searchText = searchBar.getText();
        if (!searchText.isBlank()) {
            results = Library.getInstance().search(searchText);
            displayBooks(results, 4);
        } else {
            // Handle empty search case
        }
    }

    @FXML
    protected void onImportButtonClick() throws Exception {
        importscreen = new ImportBookScreen();
        importscreen.start(new Stage());
    }
    @FXML
    protected void onCreateButtonClick() throws IOException {
        addbook = new addBookScreen();
        addbook.start(new Stage());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane = new GridPane();
        scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFocusTraversable(false);
        gridPane.setFocusTraversable(false);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: transparent;");
        vBox.getChildren().add(scrollPane);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.widthProperty().addListener((observable, newWidth, oldWidth) -> {
            int width = newWidth.intValue();
            int maxColumns = 4;
            if (results != null) {
                displayBooks(results, maxColumns);
            }


        });



        populateFilterByTagsMenu();

    }

    private void populateFilterByTagsMenu() {
        ArrayList<String> allTags = Library.getInstance().getTags();
        for (String tag : allTags) {
            CheckMenuItem menuItem = new CheckMenuItem(tag);
            menuItem.setOnAction(event -> {
                if (menuItem.isSelected()) {
                    selectedTags.add(tag);
                } else {
                    selectedTags.remove(tag);
                }
                updateBookDisplay();
            });
            filterbytags.getItems().add(menuItem);
        }
    }

    private void updateBookDisplay() {
        if (!searchBar.getText().isBlank()) {
            setResults();
            if (results != null) {
                // Filter books based on selected tags
                ArrayList<Book> filteredBooks = Library.getInstance().searchByTags(selectedTags);
                displayBooks(filteredBooks, 4);
            }
        }
    }




    public void displayBooks(ArrayList<Book> results, int maxColumns) {
        gridPane.getChildren().clear();
        int row = 0;
        int column = 0;

        for (Book book: results) {
            VBox bookBox = createBookBox(book);
            gridPane.add(bookBox, column, row);
            column++;
            if (column >= maxColumns) {
                column = 0;
                row++;
            }
        }
    }
    public void setResults() {
        String searchText = searchBar.getText();
        if (!searchText.isBlank()) {
            results = Library.getInstance().search(searchText);
        } else {
            results = new ArrayList<>(); // clear the results if the search text is blank
        }
    }
    public VBox createBookBox(Book book) {
        HBox buttonBox = new HBox();
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button detailsButton = new Button("Details");
        buttonBox.getChildren().addAll(editButton, deleteButton, detailsButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        editButton.setPrefWidth(80);
        deleteButton.setPrefWidth(80);
        detailsButton.setPrefWidth(80);

        editButton.setOnAction(event -> {
            System.out.println(book.getTitle());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit_Window.fxml"));
                loader.setControllerFactory(cls -> new EditController(book.getLocation(), book));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete_Window.fxml"));
                loader.setControllerFactory(cls -> new DeleteController(book.getLocation(), book));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox.setHgrow(editButton, Priority.ALWAYS);
        HBox.setHgrow(detailsButton, Priority.ALWAYS);
        HBox.setHgrow(deleteButton, Priority.ALWAYS);

        VBox container = new VBox();
        container.setMinSize(250, 350);
        container.setMaxSize(250, 350);
        container.setPrefSize(250, 350);
        container.setAlignment(Pos.CENTER);
        Region fillerNode = new Region();
        VBox.setVgrow(fillerNode, Priority.ALWAYS);

        container.getChildren().add(fillerNode);
        if (book.getImage() != null) {
            if (!book.getImage().isBlank()) {
                System.out.println(book.getImage());
                ImageView imageView = new ImageView(new Image(book.getImage()));
                imageView.setFitHeight(200);
                imageView.setFitWidth(150);
                container.getChildren().add(imageView);
                imageView.setPreserveRatio(true);
            }
        }
        container.getChildren().add(new Label("Title: " + book.getTitle()));
        container.getChildren().add(new Label("Subtitle: " + book.getSubtitle()));
        container.getChildren().add(new Label("Publisher: " + book.getPublisher()));
        container.getChildren().add(new Label("Edition: " + book.getEdition()));
        container.getChildren().add(new Label("Rating: " + book.getRating()));
        container.getChildren().add(buttonBox);
        container.setFocusTraversable(true);
        container.setOnMouseClicked(event -> {container.requestFocus();
            event.consume();});
        container.setStyle("-fx-border-color: gray; -fx-padding: 15; -fx-border-width: 2;");
        container.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                container.setStyle("-fx-border-color: cornflowerblue; -fx-padding: 15; -fx-border-width: 4;");
            }
            else {
                container.setStyle("-fx-border-color: gray; -fx-padding: 15; -fx-border-width: 2;");
            }
        });
        return container;
    }
}
