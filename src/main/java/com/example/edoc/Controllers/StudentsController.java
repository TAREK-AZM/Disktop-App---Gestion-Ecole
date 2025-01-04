package com.example.edoc.Controllers;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private StackPane dynamicContent;

    private final EtudiantService etudiantService = new EtudiantService();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        promoColumn.setCellValueFactory(new PropertyValueFactory<>("promo"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        // Load data
        loadStudents();

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
        List<Etudiant> students = etudiantService.getAll(); // Fetch data from service
        ObservableList<Etudiant> studentObservableList = FXCollections.observableArrayList(students);
        studentsTable.setItems(studentObservableList);
    }

    @FXML
    private void handleAdd() {
        try {
            // Load AddStudent.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/AddStudent.fxml"));
            Parent addView = loader.load();

            // Replace dynamic content with the add view
            dynamicContent.getChildren().setAll(addView);

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

                // Pass the selected student to the UpdateStudentController
                ManageStudentController updateController = loader.getController();
                updateController.setStudent(selectedStudent);

                // Create a new Stage for the pop-up
                Stage stage = new Stage();
                stage.setTitle("Update Student");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(updateView));
                stage.showAndWait(); // Wait until the pop-up is closed

                // Refresh the table after update
                loadStudents();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Etudiant selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            // Confirm delete action
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Are you sure?");
            confirmationAlert.setContentText("Delete: " + selectedStudent.getNom() + " " + selectedStudent.getPrenom());

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response.getText().equals("OK")) {
                    etudiantService.delete(selectedStudent.getId());
                    loadStudents(); // Refresh table after delete
                }
            });
        }
    }

    @FXML
    private void handleCancel() {
        // Clear dynamic content and reset buttons
        dynamicContent.getChildren().clear();
        studentsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        cancelButton.setDisable(true);
    }
}
