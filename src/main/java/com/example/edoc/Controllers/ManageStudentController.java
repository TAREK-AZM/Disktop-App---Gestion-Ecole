package com.example.edoc.Controllers;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ManageStudentController {

    @FXML
    private TextField matriculeField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField promoField;

    @FXML
    private DatePicker dateNaissancePicker;

    @FXML
    private Label successMessage;
    @FXML
    private Button cancelButton;

    private Etudiant student;

    private final EtudiantService etudiantService;

    public ManageStudentController() {
        this.etudiantService = new EtudiantService(); // Initialize the service
    }

    public void setStudent(Etudiant student) {
        this.student = student;
        matriculeField.setText(student.getMatricule());
        nomField.setText(student.getNom());
        prenomField.setText(student.getPrenom());
        emailField.setText(student.getEmail());
        promoField.setText(student.getPromo());
        dateNaissancePicker.setValue(student.getDateNaissance().toLocalDate());
    }

    @FXML
    private void handleUpdateSave() {
        // Update student details
        student.setMatricule(matriculeField.getText());
        student.setNom(nomField.getText());
        student.setPrenom(prenomField.getText());
        student.setEmail(emailField.getText());
        student.setPromo(promoField.getText());
        student.setDateNaissance(java.sql.Date.valueOf(dateNaissancePicker.getValue()));

        // Use the service to save changes to the database
        boolean updated = etudiantService.update(student);

        if (updated) {
            showSuccessMessage();
        } else {
            System.out.println("Failed to update student: " + student);
        }
    }

    @FXML
    private void handleAddSave() {
        // Validate input
        if (matriculeField.getText().isEmpty() || nomField.getText().isEmpty() || prenomField.getText().isEmpty() || emailField.getText().isEmpty()) {
            System.out.println("Please fill all required fields!");
            return;
        }

        // Create a new student object
        Etudiant newStudent = new Etudiant();
        newStudent.setId(65);
        newStudent.setMatricule(matriculeField.getText());
        newStudent.setNom(nomField.getText());
        newStudent.setPrenom(prenomField.getText());
        newStudent.setEmail(emailField.getText());
        newStudent.setPromo(promoField.getText());
        newStudent.setDateNaissance(java.sql.Date.valueOf(dateNaissancePicker.getValue()));

        // Save the student
        boolean saved = etudiantService.create(newStudent);

        if (saved) {
            System.out.println("New student added successfully: " + newStudent);
        } else {
            System.out.println("Failed to add new student!");
        }

        // Close the add student form

    }



    private void closeWindow() {
        Stage stage = (Stage) matriculeField.getScene().getWindow();
        stage.close();
    }
    private void showSuccessMessage() {
        successMessage.setVisible(true);

        // Hide the message after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Wait for 3 seconds
                Platform.runLater(() -> successMessage.setVisible(false));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
