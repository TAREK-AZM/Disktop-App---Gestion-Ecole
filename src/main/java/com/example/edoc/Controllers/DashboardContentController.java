package com.example.edoc.Controllers;

import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardContentController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label totalStudentsLabel;

    @FXML
    private Label totalProfessorsLabel;

    @FXML
    private Label totalModulesLabel;

    private EtudiantService studentService;
    private ProfesseurService professorService;
    private ModuleService moduleService;

    public void setServices(EtudiantService studentService, ProfesseurService professorService, ModuleService moduleService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.moduleService = moduleService;
        loadStatistics(); // Load statistics after services are set
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            usernameLabel.setText(utilisateur.getUsername());
            roleLabel.setText(utilisateur.getRole());
        }
    }

    private void loadStatistics() {
        if (studentService != null && professorService != null && moduleService != null) {
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
    }
}