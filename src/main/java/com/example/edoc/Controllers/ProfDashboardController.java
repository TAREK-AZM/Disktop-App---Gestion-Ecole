package com.example.edoc.Controllers;

import com.example.edoc.DAO.EtudiantDAO;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfDashboardController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Button signOutBtn ;
    @FXML
    private Label prenomLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    private Utilisateur utilisateur;

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

    private void loadStatistics() {
        // Fetch and display the total number of students
        EtudiantService studentService = new EtudiantService();
        ProfesseurService professorService = new ProfesseurService();
        ModuleService moduleService = new ModuleService();
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
    public void initialize() {
        loadStatistics();
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        updateUI();
    }

    private void updateUI() {
        // Update the UI with the utilisateur data
        if (utilisateur != null) {
            usernameLabel.setText(utilisateur.getUsername());
            roleLabel.setText(utilisateur.getRole());
        }
    }

    @FXML
    public void showStudentsforProfessores() {
        loadView("etudiant/StudentsforProfessores.fxml");
    }
    @FXML
    public  void showModulesforProfessors(){
        loadView("etudiant/ModulesforProfessors.fxml");
    }


//    @FXML
//    public void showDashboard(ActionEvent actionEvent) {
//        loadView("admin-dashboard.fxml");
//    }
private void loadView(String fxmlFile) {
    try {
        Parent view = FXMLLoader.load(getClass().getResource("/com/example/edoc/" + fxmlFile));
        contentArea.getChildren().setAll(view);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @FXML
    public void handleSignOut() {
        System.out.println("Signing out...");

        try {
            // Load the login page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/login.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) signOutBtn.getScene().getWindow();

            // Set the login page as the current scene
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login page.");
        }
    }


}
