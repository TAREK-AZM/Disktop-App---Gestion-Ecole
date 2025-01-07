package com.example.edoc.Controllers.etudiant;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Module;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.InscriptionService;
import com.example.edoc.Services.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Button moduleButton;

    @FXML
    private Button showButton;

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

    @FXML
    private ComboBox<String> moduleComboBox;

    private final EtudiantService etudiantService = new EtudiantService();
    private final ModuleService moduleService = new ModuleService();
    private final InscriptionService inscriptionService = new InscriptionService();

    private ObservableList<Etudiant> allStudents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        promoColumn.setCellValueFactory(new PropertyValueFactory<>("promo"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        // Load modules into the ComboBox
        loadModules1();

        // Load all students initially
        loadStudents();

        // Add listener to enable/disable buttons based on selection
        studentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean rowSelected = newSelection != null;
            addButton.setDisable(rowSelected);
            updateButton.setDisable(!rowSelected);
            deleteButton.setDisable(!rowSelected);
            moduleButton.setDisable(!rowSelected);
            showButton.setDisable(!rowSelected);
            cancelButton.setDisable(false); // Always enable cancel
        });

        // Add listener to the ComboBox to fetch students by module
        moduleComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Fetch students enrolled in the selected module
                fetchStudentsByModule(newSelection);
            }
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
                stage.setTitle("Update Student");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(updateView));
                stage.showAndWait();

                loadStudents(); // Refresh the table after update
            } catch (IOException e) {
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
                stage.setTitle("Confirm Deletion");
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
                if (response == ButtonType.OK) {
                    etudiantService.delete(selectedStudent.getId());
                    loadStudents();
                }
            });
        }
    }

    @FXML
    private void handleCancel() {
        studentsTable.getSelectionModel().clearSelection();
        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        cancelButton.setDisable(true);
        moduleButton.setDisable(true);
        showButton.setDisable(true);
    }

    @FXML
    private void handleSearch() {
        filterStudents();
    }

    private void loadModules1() {
        // Fetch all modules from the service
        List<Module> modules = moduleService.GetAllModules();

        // Create a list to store module names
        List<String> moduleNames = new ArrayList<>();

        // Extract module names using a loop
        for (Module module : modules) {
            moduleNames.add(module.getNomModule());
        }

        // Convert the list to an ObservableList
        ObservableList<String> observableModuleNames = FXCollections.observableArrayList(moduleNames);

        // Set the items in the ComboBox
        moduleComboBox.setItems(observableModuleNames);
    }

    private void fetchStudentsByModule(String moduleName) {
        // Fetch the module ID by name
        int moduleId = moduleService.findByName(moduleName);
        if (moduleId !=0) {
            Optional<Module> module = moduleService.GetModuleById(moduleId);

            // Fetch students enrolled in the module using InscriptionService
            List<Etudiant> students = inscriptionService.getEtudiantsByModule(module.get());

            // Update the TableView with the fetched students
            ObservableList<Etudiant> studentList = FXCollections.observableArrayList(students);
            studentsTable.setItems(studentList);
        } else {
            System.out.println("Module not found: " + moduleName);
        }
    }

    @FXML
    private void handleAssignModule() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/affect-etudiant.fxml"));
        Parent manageModuleView = loader.load();

        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setTitle("Manage Modules for Professor");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(manageModuleView));
        stage.showAndWait();
    }

    @FXML
    public void showModules(ActionEvent actionEvent) throws IOException {
        // Get the selected student
        Etudiant selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            System.out.println("No student selected.");
            return;
        }

        // Get the module IDs for the selected student
        List<Integer> listIds = inscriptionService.getAllModulesIds(selectedStudent.getId());

        // Retrieve the module names
        List<String> moduleNames = new ArrayList<>();
        for (Integer idModule : listIds) {
            Optional<Module> module = moduleService.GetModuleById(idModule);
            if (module.isPresent()) {
                moduleNames.add(module.get().getNomModule());
            }
        }

        // Load the new view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/edoc/etudiant/etudiant-module.fxml"));
        Parent manageModuleView = loader.load();

        // Get the controller of the new view and set the module names
        EtudiantModuleController etudiantModuleController = loader.getController();
        etudiantModuleController.setModuleNames(moduleNames);

        // Display the new view
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setTitle("Manage Modules for Student");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(manageModuleView));
        stage.showAndWait();
    }
}