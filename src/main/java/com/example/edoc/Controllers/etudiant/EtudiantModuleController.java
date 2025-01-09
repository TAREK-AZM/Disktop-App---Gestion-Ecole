package com.example.edoc.Controllers.etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class EtudiantModuleController {

    @FXML
    private ListView<String> moduleListView; // Assuming you have a ListView to display the module names

    public void setModuleNames(List<String> moduleNames) {
        moduleListView.getItems().setAll(moduleNames);
    }

    @FXML
    private void handleCloseButton() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) moduleListView.getScene().getWindow();
        stage.close();
    }
}