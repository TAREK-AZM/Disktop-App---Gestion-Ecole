package com.example.edoc.Controllers.module;

import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ManageModuleController {

    @FXML
    private TextField nomModuleField;

    @FXML
    private TextField codeModuleField;

    @FXML
    private TextField professeurIdField;

    @FXML
    private ComboBox<Professeur> professeurComboBox;

    private Module module;


    private final ModuleService moduleService = new ModuleService();
    private final ProfesseurService professeurService = new ProfesseurService();

    public void initialize() {
        loadProfessors();

        // Set cell factory to display only professor names
        professeurComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Professeur prof, boolean empty) {
                super.updateItem(prof, empty);
                setText(empty || prof == null ? null : prof.getNom()+", "+prof.getPrenom());
            }
        });

        // Set the displayed value in the ComboBox
        professeurComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Professeur prof, boolean empty) {
                super.updateItem(prof, empty);
                setText(empty || prof == null ? null : prof.getNom());
            }
        });
    }

    public void setModule(Module module) {
        this.module = module;
        if (module != null) {
            nomModuleField.setText(module.getNomModule());
            codeModuleField.setText(module.getCodeModule());
            // Set selected professor in the ComboBox
            professeurComboBox.getSelectionModel().select(findProfessorById(module.getProfesseurId()));
        }
    }

    private void loadProfessors() {
        List<Professeur> professors = professeurService.getAllProfesseur(); // Fetch all professors from the database
        professeurComboBox.setItems(FXCollections.observableArrayList(professors));
    }


    private Professeur findProfessorById(int id) {
        for (Professeur prof : professeurComboBox.getItems()) {
            if (prof.getId() == id) {
                return prof;
            }
        }
        return null;
    }


    @FXML
    private void handleAddSave() {
        try {
            // Validate input fields
            if (nomModuleField.getText().isEmpty() || codeModuleField.getText().isEmpty() ||
                    professeurComboBox.getSelectionModel().isEmpty()) {
                showAlert("Validation Error", "Please fill all fields and select a professor.", Alert.AlertType.ERROR);
                return;
            }

            // Initialize module if null
            if (module == null) {
                module = new Module();
            }

            // Set module details
            module.setNomModule(nomModuleField.getText());
            module.setCodeModule(codeModuleField.getText());

            // Get selected professor and set its ID
            Professeur selectedProf = professeurComboBox.getSelectionModel().getSelectedItem();
            module.setProfesseurId(selectedProf.getId());

            // Save or update module based on its ID
            boolean saved;
            if (module.getId() == 0) {
                saved = moduleService.CreateModule(module);
            } else {
                saved = moduleService.UpdateModule(module);
            }

            // Show feedback to the user
            if (saved) {
                showAlert("Success", "Module saved successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to save the module.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateSave() {
        if (module == null) {
            showAlert("Error", "No module selected for update!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Update module fields
            module.setNomModule(nomModuleField.getText());
            module.setCodeModule(codeModuleField.getText());
            // Get selected professor and set its ID
            Professeur selectedProf = professeurComboBox.getSelectionModel().getSelectedItem();
            module.setProfesseurId(selectedProf.getId());

            boolean updated = moduleService.UpdateModule(module);

            if (updated) {
                showAlert("Success", "Module updated successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to update the module.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nomModuleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
}
