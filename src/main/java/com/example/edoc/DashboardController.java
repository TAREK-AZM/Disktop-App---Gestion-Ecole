package com.example.edoc;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    public void navigateToStudents() {
        System.out.println("Navigating to Student Management...");
        // Load and display the Student Management FXML
    }

    @FXML
    public void navigateToProfessors() {
        System.out.println("Navigating to Professor Management...");
        // Load and display the Professor Management FXML
    }

    @FXML
    public void navigateToModules() {
        System.out.println("Navigating to Module Management...");
        // Load and display the Module Management FXML
    }

    @FXML
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have been logged out.");
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/login.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = new Stage() ;
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
