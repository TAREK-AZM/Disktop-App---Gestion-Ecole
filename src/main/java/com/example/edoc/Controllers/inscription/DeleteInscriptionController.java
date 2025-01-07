package com.example.edoc.Controllers.inscription;

import com.example.edoc.Services.InscriptionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteInscriptionController {

    @FXML
    private TextField inscriptionIdField;

    private final InscriptionService inscriptionService = new InscriptionService();

    @FXML
    private void handleDelete() {
        try {
            int inscriptionId = Integer.parseInt(inscriptionIdField.getText());

            boolean deleted = inscriptionService.deleteInscription(inscriptionId);

            if (deleted) {
                showAlert("Success", "Inscription deleted successfully.", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Error", "Failed to delete the inscription.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid Inscription ID.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
        Stage stage = (Stage) inscriptionIdField.getScene().getWindow();
        stage.close();
    }
}