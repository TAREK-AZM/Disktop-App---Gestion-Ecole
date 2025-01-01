package com.example.edoc;

import com.example.edoc.DAO.ModuleDAO;
import com.example.edoc.Services.ModuleService;
import com.example.edoc.Utils.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.edoc.Entities.Module;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
//        ModuleDAO mdao = new ModuleDAO();
        Module m = new Module(3,"c++","M1234",1);
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