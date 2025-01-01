package com.example.edoc;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Services.InscriptionService;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Utils.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.edoc.Entities.Module;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ParseException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        //launch();
        String dateStr = "23-02-2002"; // Format de la date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date dateNaissanceUtil = formatter.parse(dateStr);
        java.sql.Date dateNaissanceSql = new java.sql.Date(dateNaissanceUtil.getTime());
        Etudiant etudiant1 = new Etudiant(5,"8465jrhf", "Bajji", "Amine", dateNaissanceSql, "amine.bajji@gmail.com", "2020");

        String dateStr2 = "22-05-2004"; // Format de la date
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date dateNaissanceUtil2 = formatter2.parse(dateStr2);
        java.sql.Date dateNaissanceSql2 = new java.sql.Date(dateNaissanceUtil2.getTime());
        Etudiant etudiant2 = new Etudiant(9,"3874utgf", "Ziane", "Imad", dateNaissanceSql, "imad.ziane@gmail.com.com", "2021");


        EtudiantService etudiantService = new EtudiantService();
        //etudiantService.create(etudiant1);
        //etudiantService.create(etudiant2);

        //etudiantService.update(new Etudiant(2,"304djf", "Al_Azami", "Tarek", dateNaissanceSql, "tarek.alazami@gmail.com", "2022"));

        //etudiantService.delete(etudiant2.getId());

        /*Optional<Etudiant> EtudiantGeted= etudiantService.findById(1);
        System.out.println(EtudiantGeted);

        List<Etudiant> Etudiants= etudiantService.getAll();
        System.out.println(Etudiants);*/
//----------------------------------------------------------------------------------------------------------

        Module m1 = new Module(1,"c++","M1234",1);
        Module m2 = new Module(2,"Java","M7464",1);
        Module m3 = new Module(3,"Database","M7363",2);

        ModuleService MService = new ModuleService();
        //MService.CreateModule(m1);
        //MService.CreateModule(m2);
        //MService.CreateModule(m3);
//-----------------------------------------------------------------------------------------------------

        InscriptionDAO inscriptionDAO = new InscriptionDAO();
        Inscription inscription1 = new Inscription(1,1,1);
        Inscription inscription2 = new Inscription(2,2,1);
        Inscription inscription3 = new Inscription(3,5,2);
        Inscription inscription4 = new Inscription(5,9,2);
        /*inscriptionDAO.create(inscription1);
        inscriptionDAO.create(inscription2);
        inscriptionDAO.create(inscription3);
        inscriptionDAO.create(inscription4);*/

        /*List<Etudiant> etudiants = inscriptionDAO.getEtudiantsByModule(m1);
        System.out.println(etudiants);*/

        //inscriptionDAO.delete(3);

        /*InscriptionService inscriptionService = new InscriptionService();
        List<Etudiant> etudiants = inscriptionService.getEtudiantsByModule(m2);
        System.out.println(etudiants);*/




    }
}