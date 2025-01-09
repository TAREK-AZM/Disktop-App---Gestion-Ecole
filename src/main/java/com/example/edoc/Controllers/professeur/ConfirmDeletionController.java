package com.example.edoc.Controllers.professeur;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeletionController {

    @FXML
    private Label deletionMessage;

    private Professeur professeur;

    private final ProfesseurService professeurService = new ProfesseurService();

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
        if (professeur != null) {
            deletionMessage.setText("Are you sure you want to delete the student: " + professeur.getNom() + " " + professeur.getPrenom() + "?");
        }
    }

    @FXML
    private void confirmDeletion() {
        if (professeur != null) {
            professeurService.deleteProfesseur(professeur.getId());
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
