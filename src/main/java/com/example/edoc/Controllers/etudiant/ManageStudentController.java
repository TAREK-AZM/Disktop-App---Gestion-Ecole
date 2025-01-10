package com.example.edoc.Controllers.etudiant;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Calendar;

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
    private Button cancelButton;

    private Etudiant student;

    private final EtudiantService etudiantService;

    public ManageStudentController() {
        this.etudiantService = new EtudiantService();
    }

    public void setStudent(Etudiant student) {
        this.student = student;
        if (student != null) {
            matriculeField.setText(student.getMatricule());
            nomField.setText(student.getNom());
            prenomField.setText(student.getPrenom());
            emailField.setText(student.getEmail());
            promoField.setText(student.getPromo());
            dateNaissancePicker.setValue(student.getDateNaissance().toLocalDate());
        }
    }

    @FXML
    private void handleUpdateSave() {
        if (student == null) {
            showErrorMessage("No student selected for update!");
            return;
        }

        try {
            student.setMatricule(matriculeField.getText());
            student.setNom(nomField.getText());
            student.setPrenom(prenomField.getText());
            student.setEmail(emailField.getText());
            student.setPromo(promoField.getText());
            student.setDateNaissance(java.sql.Date.valueOf(dateNaissancePicker.getValue()));

            boolean updated = etudiantService.update(student);

            if (updated) {
                showSuccessMessage("Student updated successfully!");
                closeWindow();
            } else {
                showErrorMessage("Failed to update student!");
            }
        } catch (Exception e) {
            showErrorMessage("Error while updating student: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddSave() {
        try {
            // Validate required fields
            if (matriculeField.getText().isEmpty() || nomField.getText().isEmpty() ||
                    prenomField.getText().isEmpty() || emailField.getText().isEmpty() ||
                    dateNaissancePicker.getValue() == null) {
                showErrorMessage("Please fill all required fields!");
                return;
            }
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            // jib la date de naissance
            java.sql.Date dateNaissance = java.sql.Date.valueOf(dateNaissancePicker.getValue());

            // Get the year of birth from the date of birth
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(dateNaissance);
            int birthYear = birthCalendar.get(Calendar.YEAR);

            // Check if the student is at least 17 years old
            if (currentYear - birthYear < 17) {
                showErrorMessage("The student must be at least 17 years old.");
                return;
            }

            // Parse the promo year from the text field
            int promoYear;
            try {
                promoYear = Integer.parseInt(promoField.getText());
            } catch (NumberFormatException e) {
                showErrorMessage("The promo year is not a valid number.");
                return;
            }

            // Check if the promo year is not greater than the current year
            if (promoYear > currentYear || promoYear <currentYear-7) {
                showErrorMessage("The promo year is not valid");
                return;
            }

            // Create a new student object
            Etudiant newStudent = new Etudiant();
            newStudent.setMatricule(matriculeField.getText());
            newStudent.setNom(nomField.getText());
            newStudent.setPrenom(prenomField.getText());
            newStudent.setEmail(emailField.getText());
            newStudent.setPromo(promoField.getText());
            newStudent.setDateNaissance(dateNaissance);

            // Save the student to the database
            boolean saved = etudiantService.create(newStudent);

            if (saved) {
                showSuccessMessage("New student added successfully!");
                closeWindow();
            } else {
                showErrorMessage("Failed to add new student!");
            }
        } catch (Exception e) {
            showErrorMessage("Error while adding new student: " + e.getMessage());
        }
    }


    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
}
