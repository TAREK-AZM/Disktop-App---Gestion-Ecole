package com.example.edoc.Controllers.inscription;

import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Module;
import com.example.edoc.Services.InscriptionService;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class InscriptionController {
    @FXML
    private TableView<Inscription> inscriptionsTable;

    @FXML
    private TableColumn<Inscription, Integer> idColumn;

    @FXML
    private TableColumn<Inscription, String> etudiantColumn;

    @FXML
    private TableColumn<Inscription, String> moduleColumn;

    @FXML
    private TableColumn<Inscription, String> dateColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField searchEtudiantField;

    @FXML
    private TextField searchModuleField;

    private final InscriptionService inscriptionService = new InscriptionService();
    private final EtudiantService etudiantService = new EtudiantService();
    private final ModuleService moduleService = new ModuleService();

    private ObservableList<Inscription> allInscriptions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Set up the etudiant column to display student name
        etudiantColumn.setCellValueFactory(cellData -> {
            Inscription inscription = cellData.getValue();
            Etudiant etudiant = etudiantService.findById(inscription.getEtudiantId()).orElse(null);
            String etudiantName = etudiant != null ?
                    etudiant.getNom() + " " + etudiant.getPrenom() : "Unknown";
            return new SimpleStringProperty(etudiantName);
        });

        // Set up the module column to display module name
        moduleColumn.setCellValueFactory(cellData -> {
            Inscription inscription = cellData.getValue();
            Module module = moduleService.GetModuleById(new Module(inscription.getModuleId())).orElse(null);
            String moduleName = module != null ? module.getNomModule() : "Unknown";
            return new SimpleStringProperty(moduleName);
        });

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        loadInscriptions();

        // Set up selection listener
        inscriptionsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean rowSelected = newSelection != null;
            addButton.setDisable(rowSelected);
            deleteButton.setDisable(!rowSelected);
            cancelButton.setDisable(!rowSelected);
        });

        // Set up search listeners
        searchEtudiantField.textProperty().addListener((observable, oldValue, newValue) -> filterInscriptions());
        searchModuleField.textProperty().addListener((observable, oldValue, newValue) -> filterInscriptions());
    }

    private void loadInscriptions() {
        List<Inscription> inscriptions = inscriptionService.getAllInscriptions();
        allInscriptions = FXCollections.observableArrayList(inscriptions);
        inscriptionsTable.setItems(allInscriptions);
    }

    private void filterInscriptions() {
        String searchEtudiant = searchEtudiantField.getText().toLowerCase().trim();
        String searchModule = searchModuleField.getText().toLowerCase().trim();

        ObservableList<Inscription> filteredList = allInscriptions.filtered(inscription -> {
            Etudiant etudiant = etudiantService.findById(inscription.getEtudiantId()).orElse(null);
            Module module = moduleService.GetModuleById(new Module(inscription.getModuleId())).orElse(null);

            String etudiantName = etudiant != null ?
                    (etudiant.getNom() + " " + etudiant.getPrenom()).toLowerCase() : "";
            String moduleName = module != null ? module.getNomModule().toLowerCase() : "";

            boolean matchesEtudiant = searchEtudiant.isEmpty() || etudiantName.contains(searchEtudiant);
            boolean matchesModule = searchModule.isEmpty() || moduleName.contains(searchModule);

            return matchesEtudiant && matchesModule;
        });

        inscriptionsTable.setItems(filteredList);
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/inscription/ManageInscription.fxml"));
            Parent addView = loader.load();

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Inscription");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addView));
            stage.showAndWait();

            loadInscriptions();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open add inscription window.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete() {
        Inscription selectedInscription = inscriptionsTable.getSelectionModel().getSelectedItem();
        if (selectedInscription != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this inscription?");

            if (alert.showAndWait().orElse(null) == ButtonType.OK) {
                boolean deleted = inscriptionService.deleteInscription(selectedInscription.getId());
                if (deleted) {
                    loadInscriptions();
                    showAlert("Success", "Inscription deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete inscription.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void handleCancel() {
        inscriptionsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        deleteButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
