package com.example.edoc;

import com.example.edoc.DAO.EtudiantDAO;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Services.ProfesseurService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/edoc/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("edoc-application");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args)  {
       launch(args);
        //EtudiantDAO etudiantDAO = new EtudiantDAO();
        //System.out.println(etudiantDAO.getEtudiantsByProfesseur(1));

        //EtudiantService etudiantService = new EtudiantService();
        //System.out.println(etudiantService.getEtudiantsByProf(1));

        /*ModuleService moduleService = new ModuleService();
        ProfesseurService professeurService = new ProfesseurService();
        Professeur professeur = new Professeur();
        professeur = professeurService.getProfesseurById(8).get();
        System.out.println(moduleService.GetAllModulesOfProfesseur(professeur));*/

    }
}