package com.example.edoc.DAO;

import com.example.edoc.Entities.Professeur;
import com.example.edoc.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesseurDAO implements CRUD<Professeur , Integer>{
    private PreparedStatement preparedStatement ;
    private Connection connection = DatabaseConnection.getInstance().getConnection() ;

    @Override
    public boolean create(Professeur professeur) {
        String requete = "insert into professeurs(nom , prenom , specialite) values( ? , ? , ?)" ;
        try {
            preparedStatement = connection.prepareStatement(requete);
            // the id is autoincrement in the db

            preparedStatement.setString(1,professeur.getNom());
            preparedStatement.setString(2,professeur.getPrenom());
            preparedStatement.setString(3,professeur.getSpecialite());
            preparedStatement.executeUpdate();
            System.out.println("professeur created");
            // insert it then in the utilisateurs table
            String requete2 = "insert into utilisateurs values(?, ?, ?, ?)" ;
            // concatenate nom et prenom => username
           Optional<Professeur> userProf =  this.findByUserName(professeur.getNom());
            System.out.println(userProf);
            String userName = professeur.getNom() + "." + professeur.getPrenom();
            preparedStatement = connection.prepareStatement(requete2);
            preparedStatement.setInt(1, userProf.get().getId());
            preparedStatement.setString(2,userName);
            preparedStatement.setString(3,"1234");
            preparedStatement.setString(4,"professeur");
            preparedStatement.executeUpdate();
            System.out.println("utilisateur & prof created");
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(Professeur professeur) {
        String requete = "update professeurs set nom=? , prenom=?, specialite=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(4, professeur.getId());
            preparedStatement.setString(1, professeur.getNom());
            preparedStatement.setString(2, professeur.getPrenom());
            preparedStatement.setString(3, professeur.getSpecialite());
            preparedStatement.executeUpdate();
            System.out.println("professeur modifie avec succes ");
            String requete2="update utilisateurs set username=? where id=?" ;
            preparedStatement = connection.prepareStatement(requete2);
            preparedStatement.setString(1, professeur.getNom()+ "."+ professeur.getPrenom());
            preparedStatement.setInt(2, professeur.getId());
            preparedStatement.executeUpdate();
            System.out.println("utilisateur & prof modifie avec succes ");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String requete1="delete from professeurs where id = ? " ;
        String requete2="delete from utilisateurs where id = ? " ;
        try{
            PreparedStatement preparedStatement2 =  connection.prepareStatement(requete1);
            PreparedStatement preparedStatement1 =  connection.prepareStatement(requete2);
            preparedStatement2.setInt(1, id);
            preparedStatement1.setInt(1 , id);
             preparedStatement2.executeUpdate() ;
             preparedStatement1.executeUpdate();
             System.out.println(" prof et utilisateur supprimer avec succes ");
             return true ;

        }
        catch(SQLException e){
            System.out.println("exception raised  : " + e.getMessage());
        }
        return false ;
    }


    @Override
    public Optional<Professeur> findById(Integer id) {
        String requete = "SELECT * FROM professeurs WHERE id = ?";
     try{
         PreparedStatement preparedStatement = connection.prepareStatement(requete) ;
         preparedStatement.setInt(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(resultSet.getInt("id"));
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setSpecialite(resultSet.getString("specialite"));
                return Optional.of(professeur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Professeur> getAll() {
        String requette = "select *from professeurs" ;
        List<Professeur> fetchedProffeseurs= new ArrayList<Professeur>() ;
        ResultSet resultSet = null ;
        try{
            Statement stm = connection.createStatement() ;
            resultSet=stm.executeQuery(requette) ;
            while(resultSet.next()){
                Professeur professeur = new Professeur() ;
                professeur.setId(resultSet.getInt("id"));
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setSpecialite(resultSet.getString("specialite"));
                fetchedProffeseurs.add(professeur) ;
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        return fetchedProffeseurs ;
    }


    public Optional<Professeur> findByUserName(String username) {
        String requete = "SELECT * FROM professeurs WHERE nom = ?";
        try { // Replace with your connection method
             PreparedStatement preparedStatement = connection.prepareStatement(requete) ;
             preparedStatement.setString(1, username);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(resultSet.getInt("id"));
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setSpecialite(resultSet.getString("specialite"));
                return Optional.of(professeur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Professeur findByNomAndPrenom(String nom, String prenom) {
        String sql = "SELECT * FROM professeurs WHERE nom = ? AND prenom = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(resultSet.getInt("id"));
                professeur.setNom(resultSet.getString("nom"));
                professeur.setPrenom(resultSet.getString("prenom"));
                professeur.setSpecialite(resultSet.getString("specialite"));

                return professeur;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retourne null si le professeur n'existe pas
    }
}
