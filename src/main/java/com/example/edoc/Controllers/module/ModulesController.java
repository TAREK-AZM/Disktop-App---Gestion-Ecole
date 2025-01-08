package com.example.edoc.Controllers.module;

import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
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

public class ModulesController {

    @FXML
    private TableView<Module> modulesTable;

    @FXML
    private TableColumn<Module, Integer> idColumn;

    @FXML
    private TableColumn<Module, String> nomModuleColumn;

    @FXML
    private TableColumn<Module, String> codeModuleColumn;

    @FXML
    private TableColumn<Module, Integer> professeurIdColumn;

    @FXML
    private TextField searchNomField;

    @FXML
    private TextField searchCodeField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
   private Button cancelButton;


    private final ModuleService moduleService = new ModuleService();

    private ObservableList<Module> allModules = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomModuleColumn.setCellValueFactory(new PropertyValueFactory<>("nomModule"));
        codeModuleColumn.setCellValueFactory(new PropertyValueFactory<>("codeModule"));
        professeurIdColumn.setCellValueFactory(new PropertyValueFactory<>("professeurId"));

        loadModules();

        // Enable/Disable buttons based on selection
        modulesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean rowSelected = newSelection != null;
            addButton.setDisable(rowSelected);
            updateButton.setDisable(!rowSelected);
            deleteButton.setDisable(!rowSelected);
            cancelButton.setDisable(false); // Always enable cancel
        });
    }

    private void loadModules() {
        List<Module> modules = moduleService.GetAllModules();
        allModules = FXCollections.observableArrayList(modules);
        modulesTable.setItems(allModules);
    }
    private void filterModules() {
        String searchNom = searchNomField != null ? searchNomField.getText().toLowerCase().trim() : "";
        String searchCode = searchCodeField != null ? searchCodeField.getText().toLowerCase().trim() : "";

        ObservableList<Module> filteredList = allModules.filtered(module -> {
            boolean matchesNom = searchNom.isEmpty() || module.getNomModule().toLowerCase().contains(searchNom);
            boolean matchesCode = searchCode.isEmpty() || module.getCodeModule().toLowerCase().contains(searchCode);
            return matchesNom && matchesCode;
        });

        modulesTable.setItems(filteredList);
    }

    @FXML
    private void handleSearch() {
        filterModules();
    }


    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/module/AddModule.fxml"));
            Parent addView = loader.load();

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Module");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addView));
            stage.showAndWait();

            loadModules();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/module/UpdateModules.fxml"));
                Parent updateView = loader.load();

                ManageModuleController controller = loader.getController();
                controller.setModule(selectedModule);

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Update Module");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(updateView));
                stage.showAndWait();

                loadModules();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteConfirmation() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/module/ConfirmDelete.fxml"));
                Parent confirmView = loader.load();

                ConfirmDeleteController controller = loader.getController();
                controller.setModule(selectedModule);

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Confirm Deletion");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(confirmView));
                stage.showAndWait();

                loadModules();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void handleCancel() {
//        dynamicContent.getChildren().clear();
        modulesTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        cancelButton.setDisable(true);
    }
}
