package com.example.edoc.Controllers.etudiant;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EtudiantChartController {

    @FXML
    private PieChart studentsPieChart; // PieChart to display student distribution

    @FXML
    private BarChart<String, Number> studentsBarChart; // BarChart to display student distribution

    @FXML
    private CategoryAxis xAxis; // X-axis for BarChart

    @FXML
    private NumberAxis yAxis; // Y-axis for BarChart

    @FXML
    private Label totalStudentsLabel; // Label to display total number of students

    @FXML
    private VBox chartContainer; // Container to hold multiple charts

    private EtudiantService etudiantService; // Service to fetch student data
    private ModuleService moduleService; // Service to fetch module data

    public EtudiantChartController() {
        this.etudiantService = new EtudiantService(); // Initialize the service
        this.moduleService = new ModuleService(); // Initialize the module service
    }

    // Method to initialize the chart
    @FXML
    public void initialize() {
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

        // Create and customize the PieChart
        PieChart promotionPieChart = new PieChart(pieChartData);
        promotionPieChart.setTitle("Répartition des étudiants par promotion");
        promotionPieChart.setClockwise(true);
        promotionPieChart.setLabelLineLength(10);
        promotionPieChart.setLabelsVisible(true);

        // Add the PieChart to the container
        chartContainer.getChildren().add(promotionPieChart);
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

        // Customize the BarChart
        xAxis.setLabel("Modules");
        yAxis.setLabel("Nombre d'étudiants");
        studentsBarChart.setTitle("Répartition des étudiants par module");
    }
}