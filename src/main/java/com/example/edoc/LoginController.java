package com.example.edoc;

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

        if (isAuthenticated != null && isAuthenticated.getRole().equals("admin")) {
            errorMessage.setVisible(false);
            navigateToAdminDashboard();
        } else if (isAuthenticated != null && isAuthenticated.getRole().equals("secretaire")) {
            errorMessage.setVisible(false);
            navigateToSecretaireDashboard();
        } else if (isAuthenticated != null && isAuthenticated.getRole().equals("professeur")) {
            errorMessage.setVisible(false);
            navigateToProfDashboard();
        } else {
            errorMessage.setText("Invalid email or password.");
            errorMessage.setVisible(true);
        }
    }

    private void navigateToProfDashboard() {
        System.out.println("Navigating to Prof Dashboard...");
    }

    private void navigateToSecretaireDashboard() {
        System.out.println("Navigating to Secretaire Dashboard...");
    }

    private void navigateToAdminDashboard() {
        System.out.println("navigating to admin dashboard...");
    }

    @FXML
    public void handleSignUp() {
        System.out.println("Navigating to Sign-Up page...");
        // You can implement sign-up navigation logic here.
    }
}
