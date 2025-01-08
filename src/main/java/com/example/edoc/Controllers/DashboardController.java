package com.example.edoc.Controllers;

import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    @FXML
    public Button dashboardBtn;

    @FXML
    public Button studentsBtn;

    @FXML
    public Button professorsBtn;

    @FXML
    public Button modulesBtn;

    @FXML
    public Button signOutBtn;

    @FXML
    private StackPane contentArea;

    @FXML
    private Label totalStudentsLabel;

    @FXML
    private Label totalProfessorsLabel;

    @FXML
    private Label totalModulesLabel;

    private Utilisateur utilisateur;
    private Button activeButton;
    private final List<Button> navigationButtons = new ArrayList<>();

    private final EtudiantService studentService = new EtudiantService();
    private final ProfesseurService professorService = new ProfesseurService();
    private final ModuleService moduleService = new ModuleService();

    @FXML
    public void initialize() {
        System.out.println("Initializing DashboardController...");

        // Add all navigation buttons to the list
        navigationButtons.add(dashboardBtn);
        navigationButtons.add(studentsBtn);
        navigationButtons.add(professorsBtn);
        navigationButtons.add(modulesBtn);
        navigationButtons.add(signOutBtn);

        // Set the default active button
        setActiveButton(dashboardBtn);
        showDashboard(); // Load the dashboard content by default
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        showDashboard(); // Refresh dashboard with user info
    }

    private void loadStatistics() {
        int totalStudents = studentService.getAll().size();
        totalStudentsLabel.setText(String.valueOf(totalStudents));

        int totalProfessors = professorService.getAllProfesseur().size();
        totalProfessorsLabel.setText(String.valueOf(totalProfessors));

        int totalModules = moduleService.GetAllModules().size();
        totalModulesLabel.setText(String.valueOf(totalModules));
    }

    @FXML
    public void showDashboard() {
        dashboardBtn.requestFocus();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/dashboard-content.fxml"));
            Parent view = loader.load();

            // Get the DashboardContentController and set the services
            DashboardContentController contentController = loader.getController();
            contentController.setUtilisateur(utilisateur);
            contentController.setServices(studentService, moduleService, professorService);

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
        setActiveButton(studentsBtn);
        loadView("etudiant/Students.fxml");
    }

    @FXML
    public void showProfessors() {
        setActiveButton(professorsBtn);
        loadView("professeur/Professeurs.fxml");
    }

    @FXML
    public void showModules() {
        setActiveButton(modulesBtn);
        loadView("module/Modules.fxml");
    }

    @FXML
    public void handleSignOut() {
        setActiveButton(signOutBtn);
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

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            resetButtonStyle(activeButton);
        }
        applyActiveStyle(button);
        activeButton = button;
    }

    private void resetButtonStyle(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14px;");
    }

    private void applyActiveStyle(Button button) {
        button.setStyle("-fx-background-color: #20c997; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
    }
}
