module com.example.edoc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;

    opens com.example.edoc to javafx.fxml;
    exports com.example.edoc;
}