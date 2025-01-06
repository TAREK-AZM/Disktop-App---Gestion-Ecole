package com.example.edoc.Controllers.professeur;

import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeleteController {

    @FXML
    private Label deletionMessage;

    private Professeur professeur;

    private final ProfesseurService professeurService = new ProfesseurService();

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
        if (professeur != null) {
            deletionMessage.setText("Are you sure you want to delete the professor: " + professeur.getNom() + " " + professeur.getPrenom() + "?");
        }
    }

    @FXML
    private void confirmDeletion() {
        if (professeur != null) {
            boolean deleted = professeurService.deleteProfesseur(professeur.getId());
            if (deleted) {
                showAlert("Success", "Professor deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete the professor.", Alert.AlertType.ERROR);
            }
        }
        closeWindow();
    }

    @FXML
    private void cancelDeletion() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) deletionMessage.getScene().getWindow();
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
