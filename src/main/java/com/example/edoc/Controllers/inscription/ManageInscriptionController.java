package com.example.edoc.Controllers.inscription;

import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Module;
import com.example.edoc.Services.InscriptionService;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ManageInscriptionController {
    @FXML
    private ComboBox<Etudiant> etudiantComboBox;

    @FXML
    private ComboBox<Module> moduleComboBox;

    private final InscriptionService inscriptionService = new InscriptionService();
    private final EtudiantService etudiantService = new EtudiantService();
    private final ModuleService moduleService = new ModuleService();

    @FXML
    public void initialize() {
        // Load students and modules into combo boxes
        etudiantComboBox.getItems().addAll(etudiantService.getAll());
        moduleComboBox.getItems().addAll(moduleService.GetAllModules());

        // Set up custom cell factories for better display
        etudiantComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Etudiant etudiant, boolean empty) {
                super.updateItem(etudiant, empty);
                if (empty || etudiant == null) {
                    setText(null);
                } else {
                    setText(etudiant.getNom() + " " + etudiant.getPrenom());
                }
            }
        });

        moduleComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Module module, boolean empty) {
                super.updateItem(module, empty);
                if (empty || module == null) {
                    setText(null);
                } else {
                    setText(module.getNomModule());
                }
            }
        });
    }

    @FXML
    private void handleSave() {
        if (validateInput()) {
            Inscription inscription = new Inscription();
            inscription.setEtudiantId(etudiantComboBox.getValue().getId());
            inscription.setModuleId(moduleComboBox.getValue().getId());
            inscription.setDateInscription(LocalDate.now());

            boolean saved = inscriptionService.createInscription(inscription);
            if (saved) {
                showAlert("Success", "Inscription added successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to add inscription.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateInput() {
        if (etudiantComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a student.", Alert.AlertType.ERROR);
            return false;
        }
        if (moduleComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a module.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) etudiantComboBox.getScene().getWindow();
        stage.close();
    }
}