package com.example.edoc.Controllers.professeur;

import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.ModuleService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class ManageProfesseurModulesController {

    @FXML
    private TableView<Module> modulesTable;

    @FXML
    private TableColumn<Module, Integer> idColumn;

    @FXML
    private TableColumn<Module, String> nameColumn;

    @FXML
    private TableColumn<Module, String> codeColumn;

    @FXML
    private Button addModuleButton;

    @FXML
    private Button removeModuleButton;

    private Professeur selectedProfesseur;
    private final ModuleService moduleService = new ModuleService();
    private ObservableList<Module> professorModules = FXCollections.observableArrayList();

    public void setProfesseur(Professeur professeur) {
        this.selectedProfesseur = professeur;
        loadModulesForProfessor();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomModule()));
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeModule()));
    }


    private void loadModulesForProfessor() {
        List<Module> modules = moduleService.GetAllModulesOfProfesseur(selectedProfesseur);
        professorModules = FXCollections.observableArrayList(modules);
        modulesTable.setItems(professorModules);
    }

    @FXML
    private void handleAddModule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/module/AddModule.fxml"));
            Parent addView = loader.load();

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addView));
            stage.showAndWait();

            loadModulesForProfessor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Logic to add a module (e.g., open another pop-up for module selection)
    }

    @FXML
    private void handleRemoveModule() {
        Module selectedModule = modulesTable.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            boolean success = moduleService.DeleteModule(selectedModule);
            if (success) {
                professorModules.remove(selectedModule);
                showAlert("Success", "Module removed successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to remove module.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a module to remove.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) modulesTable.getScene().getWindow();
        loadModulesForProfessor();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
