package com.example.edoc.Controllers;

import com.example.edoc.Entities.Module;
import com.example.edoc.Services.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private final ModuleService moduleService = new ModuleService();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomModuleColumn.setCellValueFactory(new PropertyValueFactory<>("nomModule"));
        codeModuleColumn.setCellValueFactory(new PropertyValueFactory<>("codeModule"));
        professeurIdColumn.setCellValueFactory(new PropertyValueFactory<>("professeurId"));

        // Load data
        loadModules();
    }

    private void loadModules() {
        List<Module> modules = moduleService.GetAllModules(); // Fetch data from service
        ObservableList<Module> moduleObservableList = FXCollections.observableArrayList(modules);
        modulesTable.setItems(moduleObservableList);
    }
}
