package com.example.edoc;

import com.example.edoc.Controllers.DashboardController;
import com.example.edoc.Controllers.ProfDashboardController;
import com.example.edoc.Controllers.SecretaireDashboardController;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.UtilisateurService; // Replace with your service package
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Label errorMessage;

    private final UtilisateurService userService = new UtilisateurService();

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Please enter both email and password.");
            errorMessage.setVisible(true);
            return;
        }

        Utilisateur isAuthenticated = userService.login(email, password);

        if (isAuthenticated != null && isAuthenticated.getRole().equalsIgnoreCase("admin")) {
            errorMessage.setVisible(false);
            navigateToAdminDashboard(isAuthenticated); // Pass the authenticated user
        } else if (isAuthenticated != null && isAuthenticated.getRole().equalsIgnoreCase("secretaire")) {
            errorMessage.setVisible(false);
            navigateToSecretaireDashboard(isAuthenticated); // Pass the authenticated user
        } else if (isAuthenticated != null && isAuthenticated.getRole().equalsIgnoreCase("professeur")) {
            errorMessage.setVisible(false);
            navigateToProfDashboard(isAuthenticated); // Pass the authenticated user
        } else {
            errorMessage.setText("Invalid email or password.");
            errorMessage.setVisible(true);
        }
    }

    private void navigateToProfDashboard(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/prof-dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            // Get the controller and set the utilisateur
            ProfDashboardController controller = loader.getController();
            controller.setUtilisateur(utilisateur);

            // Get the current stage
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Professor Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToSecretaireDashboard(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/secretaire-dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            // Get the controller and set the utilisateur
            SecretaireDashboardController controller = loader.getController();
            controller.setUtilisateur(utilisateur);

            // Get the current stage
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Secretaire Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToAdminDashboard(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/admin-dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            // Get the controller and set the utilisateur
            DashboardController controller = loader.getController();
            controller.setUtilisateur(utilisateur);

            // Get the current stage
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignUp() {
        System.out.println("Navigating to Sign-Up page...");
        // You can implement sign-up navigation logic here.
    }
}
