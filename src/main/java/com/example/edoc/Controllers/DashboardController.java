package com.example.edoc.Controllers;

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

    @FXML
    private Label mostFollowedModuleLabel;


    private EtudiantService studentService = new EtudiantService();
    private ProfesseurService professorService = new ProfesseurService();
    private ModuleService moduleService = new ModuleService();

    @FXML
    public void initialize() {
        showDashboard();
      // Load the dashboard content by default
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
        loadView("dashboard-content.fxml"); // Load only the dashboard content
        loadStatistics(); // Update the statistics
    }
    @FXML
    public void retunshowDashboard() {
        loadView("admin-dashboard.fxml");
    }

    @FXML
    public void showStudents() {
        loadView("etudiant/Students.fxml");
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
            Parent view = FXMLLoader.load(getClass().getResource("/com/example/edoc/" + fxmlFile));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
