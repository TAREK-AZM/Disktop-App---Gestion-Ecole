// ProfesseurController.java
package com.example.edoc.Controllers.professeur;

import com.example.edoc.Controllers.module.ManageModuleController;
import com.example.edoc.Controllers.module.ModulesController;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import com.example.edoc.Entities.Module;

public class ProfesseurController {

    @FXML
    private TableColumn<Professeur, String> modulesColumn;

    @FXML
    private TableView<Professeur> professorsTable;

    @FXML
    private TableColumn<Professeur, Integer> idColumn;

    @FXML
    private TableColumn<Professeur, String> nomColumn;

    @FXML
    private TableColumn<Professeur, String> prenomColumn;

    @FXML
    private TableColumn<Professeur, String> specialiteColumn;


    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button moduleButton;

    @FXML
    private TextField searchNomField;

    @FXML
    private TextField searchSpecialiteField;

    private final ProfesseurService professeurService = new ProfesseurService();
    private final ModuleService moduleService = new ModuleService();

    private ObservableList<Professeur> allProfessors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        // Set the modules column dynamically
        modulesColumn.setCellValueFactory(cellData -> {
            Professeur prof = cellData.getValue();
            List<Module> modules = moduleService.GetAllModulesOfProfesseur(prof);
            String moduleNames = modules.stream()
                    .map(Module::getNomModule)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("No modules assigned");
            return new SimpleStringProperty(moduleNames);
        });

        loadProfessors();

        professorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean rowSelected = newSelection != null;
            addButton.setDisable(rowSelected);
            updateButton.setDisable(!rowSelected);
            deleteButton.setDisable(!rowSelected);
            moduleButton.setDisable(!rowSelected);
            cancelButton.setDisable(false);
        });
    }


    private void loadProfessors() {
        List<Professeur> professors = professeurService.getAllProfesseur();
        allProfessors = FXCollections.observableArrayList(professors);
        professorsTable.setItems(allProfessors);
    }

    private void filterProfessors() {
        String searchNom = searchNomField != null ? searchNomField.getText().toLowerCase().trim() : "";
        String searchSpecialite = searchSpecialiteField != null ? searchSpecialiteField.getText().toLowerCase().trim() : "";

        ObservableList<Professeur> filteredList = allProfessors.filtered(prof -> {
            boolean matchesNom = searchNom.isEmpty() || prof.getNom().toLowerCase().contains(searchNom);
            boolean matchesSpecialite = searchSpecialite.isEmpty() || prof.getSpecialite().toLowerCase().contains(searchSpecialite);
            return matchesNom && matchesSpecialite;
        });

        professorsTable.setItems(filteredList);
    }

    @FXML
    private void handleSearch() {

        filterProfessors();
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/professeur/AddProfesseur.fxml"));
            Parent addView = loader.load();

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Professor");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addView));
            stage.showAndWait();

            loadProfessors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Professeur selectedProf = professorsTable.getSelectionModel().getSelectedItem();
        if (selectedProf != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/professeur/UpdateProfesseur.fxml"));
                Parent updateView = loader.load();

                ManageProfesseurController controller = loader.getController();
                controller.setProfesseur(selectedProf);

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Update Professor");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(updateView));
                stage.showAndWait();

                loadProfessors();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteConfirmation() {
        Professeur selectedProf = professorsTable.getSelectionModel().getSelectedItem();
        if (selectedProf != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/professeur/ConfirmDelete.fxml"));
                Parent confirmView = loader.load();

                ConfirmDeletionController controller = loader.getController();
                controller.setProfesseur(selectedProf);

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Confirm Deletion");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(confirmView));
                stage.showAndWait();

                loadProfessors();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCancel() {
        professorsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        moduleButton.setDisable(true);
        cancelButton.setDisable(true);

    }


    @FXML
    private void handleProfesseurModules() {
        Professeur selectedProf = professorsTable.getSelectionModel().getSelectedItem();
        if (selectedProf != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/professeur/ManageProfesseurModules.fxml"));
                Parent manageModuleView = loader.load();

                ManageProfesseurModulesController controller = loader.getController();
                controller.setProfesseur(selectedProf);

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Manage Modules for Professor");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(manageModuleView));
                stage.showAndWait();

                // Reload professors after managing modules if necessary
                loadProfessors();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a professor first.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
