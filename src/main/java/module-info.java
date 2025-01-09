module com.example.edoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires org.apache.poi.ooxml;


    exports com.example.edoc;
    exports com.example.edoc.Controllers;
    exports com.example.edoc.Controllers.etudiant;
    exports com.example.edoc.Controllers.professeur;
    opens com.example.edoc to javafx.fxml; // Open the package to javafx.fxml

    opens com.example.edoc.Entities to javafx.base;
    opens com.example.edoc.Controllers to javafx.fxml;
    opens com.example.edoc.Controllers.etudiant to javafx.fxml;
    opens com.example.edoc.Controllers.module to javafx.fxml;
    opens com.example.edoc.Controllers.professeur to javafx.fxml;

}