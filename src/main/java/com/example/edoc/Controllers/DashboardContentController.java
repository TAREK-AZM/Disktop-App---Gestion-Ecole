package com.example.edoc.Controllers;

import com.example.edoc.Controllers.etudiant.EtudiantChartController;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardContentController {

    @FXML
    private VBox chartContainer;

    private Utilisateur utilisateur;
    private EtudiantService etudiantService;
    private ModuleService moduleService;
    private ProfesseurService professeurService;

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setServices(EtudiantService etudiantService,
                            ModuleService moduleService,
                            ProfesseurService professeurService) {
        this.etudiantService = etudiantService;
        this.moduleService = moduleService;
        this.professeurService = professeurService;
        loadCharts();
    }



    private void loadCharts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/etudiantGraphes.fxml"));
            VBox chartsView = loader.load();

            EtudiantChartController chartController = loader.getController();
            chartController.setEtudiantService(etudiantService);
            chartController.setModuleService(moduleService);

            // Initialize the charts
            chartController.initialize();

            // Add the charts to the container
            chartContainer.getChildren().clear();
            chartContainer.getChildren().add(chartsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}