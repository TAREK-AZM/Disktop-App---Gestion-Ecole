

module com.example.edoc {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        requires static lombok;

    opens com.example.edoc.Entities to javafx.base;
    opens com.example.edoc.Controllers to javafx.fxml;
    opens com.example.edoc.Controllers.module to javafx.fxml;

    exports com.example.edoc;
    exports com.example.edoc.Controllers;
    exports com.example.edoc.Controllers.etudiant;
    opens com.example.edoc.Controllers.etudiant to javafx.fxml;
}
