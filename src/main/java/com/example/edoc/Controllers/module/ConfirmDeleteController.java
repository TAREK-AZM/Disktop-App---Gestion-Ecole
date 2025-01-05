package com.example.edoc.Controllers.module;

import com.example.edoc.Entities.Module;
import com.example.edoc.Services.ModuleService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeleteController {

    @FXML
    private Label deletionMessage;

    private Module module;

    private final ModuleService moduleService = new ModuleService();

    public void setModule(Module module) {
        this.module = module;
        if (module != null) {
            deletionMessage.setText("Are you sure you want to delete the module: " + module.getNomModule() + "?");
        }
    }

    @FXML
    private void confirmDeletion() {
        if (module != null) {
            moduleService.DeleteModule(module);
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
