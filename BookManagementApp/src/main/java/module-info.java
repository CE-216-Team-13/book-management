module app.bookmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens app.bookmanagementapp to javafx.fxml;
    exports app.bookmanagementapp;
}