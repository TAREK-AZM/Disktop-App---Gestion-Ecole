package com.example.edoc.Controllers;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class StudentsController {

    @FXML
    private TableView<Etudiant> studentsTable;

    @FXML
    private TableColumn<Etudiant, Integer> idColumn;

    @FXML
    private TableColumn<Etudiant, String> matriculeColumn;

    @FXML
    private TableColumn<Etudiant, String> nomColumn;

    @FXML
    private TableColumn<Etudiant, String> prenomColumn;

    @FXML
    private TableColumn<Etudiant, String> emailColumn;

    @FXML
    private TableColumn<Etudiant, String> promoColumn;

    @FXML
    private TableColumn<Etudiant, String> dateNaissanceColumn;

    @FXML
    public Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField searchNomField;

    @FXML
    private TextField searchMatriculeField;

    @FXML
    private TextField searchPromoField;

    @FXML
    private Button searchButton;

    @FXML
    private StackPane dynamicContent;

    private final EtudiantService etudiantService = new EtudiantService();

    private ObservableList<Etudiant> allStudents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        promoColumn.setCellValueFactory(new PropertyValueFactory<>("promo"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        loadStudents();
//        setupSearchFilter();
        // Add listener to enable/disable buttons based on selection
        studentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean rowSelected = newSelection != null;
            addButton.setDisable(rowSelected);
            updateButton.setDisable(!rowSelected);
            deleteButton.setDisable(!rowSelected);
            cancelButton.setDisable(false); // Always enable cancel
        });
    }

    private void loadStudents() {
        List<Etudiant> students = etudiantService.getAll();
        allStudents = FXCollections.observableArrayList(students);
        studentsTable.setItems(allStudents);
    }

//    private void setupSearchFilter() {
//        if (searchNomField != null) {
//            searchNomField.textProperty().addListener((observable, oldValue, newValue) -> filterStudents());
//        }
//        if (searchMatriculeField != null) {
//            searchMatriculeField.textProperty().addListener((observable, oldValue, newValue) -> filterStudents());
//        }
//        if (searchPromoField != null) {
//            searchPromoField.textProperty().addListener((observable, oldValue, newValue) -> filterStudents());
//        }
//    }

    private void filterStudents() {
        String searchNom = searchNomField != null ? searchNomField.getText().toLowerCase().trim() : "";
        String searchMatricule = searchMatriculeField != null ? searchMatriculeField.getText().toLowerCase().trim() : "";
        String searchPromo = searchPromoField != null ? searchPromoField.getText().toLowerCase().trim() : "";

        ObservableList<Etudiant> filteredList = allStudents.filtered(student -> {
            boolean matchesNom = searchNom.isEmpty() || student.getNom().toLowerCase().contains(searchNom);
            boolean matchesMatricule = searchMatricule.isEmpty() || student.getMatricule().toLowerCase().contains(searchMatricule);
            boolean matchesPromo = searchPromo.isEmpty() || student.getPromo().toLowerCase().contains(searchPromo);
            return matchesNom && matchesMatricule && matchesPromo;
        });

        studentsTable.setItems(filteredList);
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/AddStudent.fxml"));
            Parent addView = loader.load();

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(addView));
            stage.showAndWait();

            loadStudents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpdate() {
        Etudiant selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                // Load the UpdateStudent.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/UpdateStudent.fxml"));
                Parent updateView = loader.load();

                // Set up the fields manually within the current StudentsController
                TextField matriculeField = (TextField) updateView.lookup("#matriculeField");
                TextField nomField = (TextField) updateView.lookup("#nomField");
                TextField prenomField = (TextField) updateView.lookup("#prenomField");
                TextField emailField = (TextField) updateView.lookup("#emailField");
                TextField promoField = (TextField) updateView.lookup("#promoField");
                DatePicker dateNaissancePicker = (DatePicker) updateView.lookup("#dateNaissancePicker");

                // Pre-fill the fields with the selected student's data
                matriculeField.setText(selectedStudent.getMatricule());
                nomField.setText(selectedStudent.getNom());
                prenomField.setText(selectedStudent.getPrenom());
                emailField.setText(selectedStudent.getEmail());
                promoField.setText(selectedStudent.getPromo());
                dateNaissancePicker.setValue(selectedStudent.getDateNaissance().toLocalDate());

                // Create a new Stage for the pop-up
                Stage stage = new Stage();
                stage.setTitle("Update Student");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(updateView));
                stage.showAndWait();

                // Update the student object and refresh the table after the pop-up is closed
                selectedStudent.setMatricule(matriculeField.getText());
                selectedStudent.setNom(nomField.getText());
                selectedStudent.setPrenom(prenomField.getText());
                selectedStudent.setEmail(emailField.getText());
                selectedStudent.setPromo(promoField.getText());
                selectedStudent.setDateNaissance(java.sql.Date.valueOf(dateNaissancePicker.getValue()));

                // Save the changes to the database
                etudiantService.update(selectedStudent);
                loadStudents(); // Refresh the table after update

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Etudiant selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Are you sure?");
            confirmationAlert.setContentText("Delete: " + selectedStudent.getNom() + " " + selectedStudent.getPrenom());

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    etudiantService.delete(selectedStudent.getId());
                    loadStudents();
                }
            });
        }
    }

    @FXML
    private void handleCancel() {
        dynamicContent.getChildren().clear();
        studentsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        cancelButton.setDisable(true);
    }
    @FXML
    private void handleSearch() {
        filterStudents();
    }

}
