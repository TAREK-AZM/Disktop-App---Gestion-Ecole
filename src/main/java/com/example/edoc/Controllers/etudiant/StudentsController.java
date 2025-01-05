package com.example.edoc.Controllers.etudiant;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/AddStudent.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/UpdateStudent.fxml"));
            Parent updateView = loader.load();

            ManageStudentController controller = loader.getController();
            controller.setStudent(selectedStudent); // Pass the selected student

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Add New Student");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(updateView));
            stage.showAndWait();


            loadStudents(); // Refresh the table after update
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    } else {
        System.out.println("No student selected for update!");
    }
}

    @FXML
    private void handleDeleteConfirmation() {
        Etudiant selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/ConfirmDelete.fxml"));
                Parent confirmView = loader.load();

                ConfirmDeletionController controller = loader.getController();
                controller.setStudent(selectedStudent); // Pass the selected student

                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setTitle("Add New Student");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(confirmView));
                stage.showAndWait();


                loadStudents(); // Refresh the table after deletion
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No student selected for deletion!");
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
//        dynamicContent.getChildren().clear();
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
