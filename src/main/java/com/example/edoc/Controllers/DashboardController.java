package com.example.edoc.Controllers;

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

    @FXML
    public void initialize() {
        // Initialize metrics (replace these with actual data retrieval logic)
        loadMetrics();
    }

    private void loadMetrics() {
        // Example: Fetch metrics from a service or database
        // Replace with actual service calls
        totalStudentsLabel.setText("120"); // Example value
        totalProfessorsLabel.setText("15"); // Example value
        totalModulesLabel.setText("8"); // Example value
        mostFollowedModuleLabel.setText("Mathematics"); // Example value
    }

    @FXML
    public void showDashboard() {
        loadView("admin-dashboard.fxml");
    }

    @FXML
    public void showStudents() {
        loadView("Students.fxml");
    }

    @FXML
    public void showProfessors() {
        loadView("Professors.fxml");
    }

    @FXML
    public void showModules() {
        loadView("Modules.fxml");
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
