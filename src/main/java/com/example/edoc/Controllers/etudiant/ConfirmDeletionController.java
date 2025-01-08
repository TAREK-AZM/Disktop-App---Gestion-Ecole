package com.example.edoc.Controllers.etudiant;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeletionController {

    @FXML
    private Label deletionMessage;

    private Etudiant student;

    private final EtudiantService etudiantService = new EtudiantService();

    public void setStudent(Etudiant student) {
        this.student = student;
        if (student != null) {
            deletionMessage.setText("Are you sure you want to delete the student: " + student.getNom() + " " + student.getPrenom() + "?");
        }
    }

    @FXML
    private void confirmDeletion() {
        if (student != null) {
            etudiantService.delete(student.getId());
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
}
