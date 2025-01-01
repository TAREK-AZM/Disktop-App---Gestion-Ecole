module com.example.edoc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.edoc to javafx.fxml;
    exports com.example.edoc;
}