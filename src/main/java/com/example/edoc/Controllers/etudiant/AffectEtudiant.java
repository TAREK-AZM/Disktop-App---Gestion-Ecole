package com.example.edoc.Controllers.etudiant;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Services.ModuleService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import com.example.edoc.Entities.Module;

public class AffectEtudiant {

    @FXML
    private TextField nomEtudiantField;

    @FXML
    private TextField prenomEtudiantField;

    @FXML
    private TextField promoField;

    @FXML
    private TableView<Module> modulesTable;

    @FXML
    private TableColumn<Module, Integer> idColumn;

    @FXML
    private TableColumn<Module, String> moduleNameColumn;

    @FXML
    private Button assignModuleButton;

    private Etudiant etudiant;
    private final ModuleService moduleService = new ModuleService();


    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;

        // Charger les informations de l'étudiant
        nomEtudiantField.setText(etudiant.getNom());
        prenomEtudiantField.setText(etudiant.getPrenom());
        promoField.setText(etudiant.getPromo());

        // Charger les modules disponibles
        loadModules();
    }

    private void loadModules() {
        // Charger les modules disponibles depuis la base de données ou une autre source
        List<Module> modules = moduleService.GetAllModules(); // Exemple d'appel de service
        modulesTable.getItems().setAll(modules);
    }

    @FXML
    private void handleAssignModule() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            int idModule = selectedModule.getId();
            // Assigner le module à l'étudiant
            boolean success;
            // Exemple de service

        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) assignModuleButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
