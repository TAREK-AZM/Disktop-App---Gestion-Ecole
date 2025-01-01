package com.example.edoc;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Services.EtudiantService;
import com.example.edoc.Utils.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.edoc.Entities.Module;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        String dateStr = "01-01-2003"; // Format de la date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        // Conversion de la chaîne en java.util.Date
        java.util.Date dateNaissanceUtil = formatter.parse(dateStr);

        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date dateNaissanceSql = new java.sql.Date(dateNaissanceUtil.getTime());

        Etudiant etudiant1 = new Etudiant(1,"764hru", "Louah", "Mohammed", dateNaissanceSql, "mohammed.louah@gmail.com", "2021");

        String dateStr2 = "29-06-2003"; // Format de la date
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");

        // Conversion de la chaîne en java.util.Date
        java.util.Date dateNaissanceUtil2 = formatter2.parse(dateStr2);

        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date dateNaissanceSql2 = new java.sql.Date(dateNaissanceUtil2.getTime());

        Etudiant etudiant2 = new Etudiant(3,"304djf", "Al Azami", "Tarek", dateNaissanceSql, "tarek.alazami@gmail.com", "2021");


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
        //launch2();
//        ModuleDAO mdao = new ModuleDAO();
        //Module m = new Module(3,"c++","M1234",1);
//        mdao.create(m);
//        mdao.update(m);
//        mdao.delete(1);
//        System.out.println(mdao.getAllModulesOfProfessor(1));
//        System.out.println(mdao.getAll());

        //////// test Service///////////////////
//        ModuleService MService = new ModuleService();
//        MService.CreateModule(m);
//        MService.DeleteModule(m);
//        MService.UpdateModule(m);
//        System.out.println((MService.GetModuleById(m)));
//        System.out.println((MService.GetAllModules()));



    }
}