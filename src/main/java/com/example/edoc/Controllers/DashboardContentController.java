package com.example.edoc.Controllers;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardContentController {


    @FXML
    private PieChart studentsPieChart; // PieChart for student distribution by promotion

    @FXML
    private BarChart<String, Number> studentsBarChart; // BarChart for student distribution by module

    @FXML
    private CategoryAxis xAxis; // X-axis for BarChart

    @FXML
    private NumberAxis yAxis; // Y-axis for BarChart

    private Utilisateur utilisateur;
    private EtudiantService etudiantService;
    private ModuleService moduleService;
    private ProfesseurService professeurService;

    // Setter for Utilisateur
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    // Setter for services
    public void setServices(EtudiantService etudiantService,
                            ModuleService moduleService,
                            ProfesseurService professeurService) {
        this.etudiantService = etudiantService;
        this.moduleService = moduleService;
        this.professeurService = professeurService;
        initializeCharts();
    }

    // Method to initialize the charts
    private void initializeCharts() {
        // Fetch all students and modules from the database
        List<Etudiant> etudiants = etudiantService.getAll();
        List<Module> modules = moduleService.GetAllModules();

        // Create a PieChart for student distribution by promotion
        createPromotionPieChart(etudiants);

        // Create a BarChart for student distribution by module
        createModuleBarChart(modules);
    }

    // Method to create a PieChart for student distribution by promotion
    private void createPromotionPieChart(List<Etudiant> etudiants) {
        // Group students by their promotion and count them
        Map<String, Long> etudiantsParPromo = etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::getPromo, Collectors.counting()));

        // Create data for the PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        etudiantsParPromo.forEach((promo, count) -> {
            pieChartData.add(new PieChart.Data(promo + " (" + count + ")", count));
        });

        // Set data to the PieChart
        studentsPieChart.setData(pieChartData);
    }

    // Method to create a BarChart for student distribution by module
    private void createModuleBarChart(List<Module> modules) {
        // Initialize the InscriptionDAO
        InscriptionDAO inscriptionDAO = new InscriptionDAO();

        // Create a series for the BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre d'étudiants par module");

        // Populate the series with data
        for (Module module : modules) {
            // Fetch inscriptions for the current module
            List<Inscription> inscriptions = inscriptionDAO.getInscriptionsByModule(module);
            // Add the module and student count to the series
            series.getData().add(new XYChart.Data<>(module.getNomModule(), inscriptions.size()));
        }

        // Add the series to the BarChart
        studentsBarChart.getData().add(series);
    }
}