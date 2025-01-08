package com.example.edoc.Controllers;

import com.example.edoc.Controllers.etudiant.EtudiantChartController;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {
    @FXML
    private StackPane contentArea;

    @FXML
    private Label totalStudentsLabel;

    @FXML
    private Label totalProfessorsLabel;

    @FXML
    private Label totalModulesLabel;

    private Utilisateur utilisateur;

    private EtudiantService studentService = new EtudiantService();
    private ProfesseurService professorService = new ProfesseurService();
    private ModuleService moduleService = new ModuleService();

    @FXML
    public void initialize() {
        System.out.println("Initializing DashboardController...");
        if (contentArea == null) {
            System.err.println("contentArea is null!");
        } else {
            System.out.println("contentArea is initialized.");
        }
        showDashboard(); // Load the dashboard content by default
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        showDashboard(); // Refresh dashboard with user info
    }

    private void loadStatistics() {
        // Fetch and display the total number of students
        int totalStudents = studentService.getAll().size();
        totalStudentsLabel.setText(String.valueOf(totalStudents));

        // Fetch and display the total number of professors
        int totalProfessors = professorService.getAllProfesseur().size();
        totalProfessorsLabel.setText(String.valueOf(totalProfessors));

        // Fetch and display the total number of modules
        int totalModules = moduleService.GetAllModules().size();
        totalModulesLabel.setText(String.valueOf(totalModules));
    }

    @FXML
    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/dashboard-content.fxml"));
            Parent view = loader.load();

            // Get the DashboardContentController and set the utilisateur
            DashboardContentController contentController = loader.getController();
            contentController.setUtilisateur(utilisateur);
            EtudiantChartController etudiant_Graphe = new EtudiantChartController();
            // Load the view into the contentArea
            contentArea.getChildren().setAll(view);

            // Load statistics
            loadStatistics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showStudents() {
        loadView("etudiant/Students.fxml");
    }

    @FXML
    public void showStudentsGraphes() {
        loadView("etudiant/etudiantGraphes.fxml");
    }

    @FXML
    public void showProfessors() {
        loadView("professeur/Professeurs.fxml");
    }

    @FXML
    public void showModules() {
        loadView("module/Modules.fxml");
    }

    @FXML
    public void showEnrollments() {
        loadView("Enrollments.fxml");
    }

    @FXML
    public void showProfile() {
        loadView("Profile.fxml");
    }

    @FXML
    public void handleSignOut() {
        System.out.println("Signing out...");
        // Implement sign-out logic
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/" + fxmlFile));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}