module library.management {
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    exports com.example to javafx.graphics;
    opens com.example.controller to javafx.fxml; // Ouvrir le package pour JavaFX
    exports com.example.controller;
}