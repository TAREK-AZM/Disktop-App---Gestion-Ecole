package com.example.edoc.Controllers.professeur;

import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageProfesseurController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField specialiteField;

    private Professeur professeur;

    private final ProfesseurService professeurService = new ProfesseurService();

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
        if (professeur != null) {
            nomField.setText(professeur.getNom());
            prenomField.setText(professeur.getPrenom());
            specialiteField.setText(professeur.getSpecialite());
        }
    }

    @FXML
    private void handleAddSave() {
        try {
            // Validate fields
            if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || specialiteField.getText().isEmpty()) {
                showAlert("Validation Error", "Please fill all fields.", Alert.AlertType.ERROR);
                return;
            }

            // Create new Professeur
            Professeur newProfesseur = new Professeur();
            newProfesseur.setNom(nomField.getText());
            newProfesseur.setPrenom(prenomField.getText());
            newProfesseur.setSpecialite(specialiteField.getText());

            boolean saved = professeurService.createProfesseur(newProfesseur);

            if (saved) {
                showAlert("Success", "Professeur added successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to add the professeur.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateSave() {
        if (professeur == null) {
            showAlert("Error", "No professeur selected for update!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Update professeur details
            professeur.setNom(nomField.getText());
            professeur.setPrenom(prenomField.getText());
            professeur.setSpecialite(specialiteField.getText());

            boolean updated = professeurService.updateProfesseur(professeur);

            if (updated) {
                showAlert("Success", "Professeur updated successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to update the professeur.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
}
