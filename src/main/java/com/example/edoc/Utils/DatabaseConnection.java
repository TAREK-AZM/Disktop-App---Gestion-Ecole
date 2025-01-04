package com.example.edoc.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static DatabaseConnection instance ;
    private Connection connection ;
    private String url ="jdbc:postgresql://localhost:5432/edoc";
//    private String url ="jdbc:mysql://localhost:3306/edoc";
    private String username ="postgres";
//    private String username ="root";
    private final String password = "pgufc"  ;
//    private final String password = ""  ;




    private DatabaseConnection(){
        try{
            // charger le driver de la connection
            Class.forName("org.postgresql.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
            // creer la connection
            connection= DriverManager.getConnection(url, username, password) ;
            System.out.println("connection succeded");
        }
        catch(Exception e){
            System.out.println("exeption occured" + e.getMessage());
        }
    }
    public static DatabaseConnection getInstance(){
        if(instance==null){
            instance=new DatabaseConnection() ;
        }
        return instance ;
    }
    public Connection getConnection(){
        return connection ;
    }
}