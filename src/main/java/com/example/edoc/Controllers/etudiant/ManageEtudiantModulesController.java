package com.example.edoc.Controllers.etudiant;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.InscriptionService;
import com.example.edoc.Services.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class ManageEtudiantModulesController {

    @FXML
    private ComboBox<String> studentDropdown;

    @FXML
    private ComboBox<String> moduleDropdown;

    @FXML
    private Button addModuleButton;

    @FXML
    private Button closeButton;

    private final EtudiantService etudiantService = new EtudiantService();
    private final ModuleService moduleService = new ModuleService();
    private final InscriptionService inscriptionService =new InscriptionService();
    @FXML
    public void initialize() {
        loadDropdowns();

        // Add action listener for validation
        studentDropdown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            validateForm();
        });

        moduleDropdown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            validateForm();
        });

        addModuleButton.setDisable(true); // Disable button until a selection is made
    }

    private void loadDropdowns() {
        // Load students
        ObservableList<String> studentNames = FXCollections.observableArrayList(
                etudiantService.getAll().stream()
                        .map(Etudiant::getNom ) // Assuming Etudiant has a getName() method
                        .toList()
        );
        studentDropdown.setItems(studentNames);

        // Load modules
        ObservableList<String> moduleNames = FXCollections.observableArrayList(
                moduleService.GetAllModules().stream()
                        .map(Module::getNomModule) // Assuming Module has a getName() method
                        .toList()
        );
        moduleDropdown.setItems(moduleNames);
    }

    private void validateForm() {
        // Enable button only if both dropdowns have a selection
        boolean valid = studentDropdown.getSelectionModel().getSelectedItem() != null
                && moduleDropdown.getSelectionModel().getSelectedItem() != null;
        addModuleButton.setDisable(!valid);
    }

    @FXML
    private void handleAssignModule() {
        String selectedEtudiantName = studentDropdown.getSelectionModel().getSelectedItem();
        String selectedModuleName = moduleDropdown.getSelectionModel().getSelectedItem();

        if (selectedEtudiantName != null && selectedModuleName != null) {
            int idEtudiant = etudiantService.findByNom(selectedEtudiantName);
            int idModule = moduleService.findByName(selectedModuleName);
            Inscription inscription = new Inscription();
            inscription.setEtudiantId(idEtudiant);
            inscription.setModuleId(idModule);
            inscription.setDateInscription(Date.valueOf(LocalDate.now()));

            if (inscriptionService.create(inscription)) {

                showAlert("Success", "Module assigned successfully!", Alert.AlertType.INFORMATION);
            } else {
                // Show an error message
                showAlert("Error", "this student already have this module!.", Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
