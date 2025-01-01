package com.example.edoc.DAO;

import com.example.edoc.Entities.Professeur;
import com.example.edoc.Entities.Utilisateur;
import com.example.edoc.Utils.DatabaseConnection;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurDAO implements CRUD<Utilisateur , Integer>{
    private  PreparedStatement preparedStatement;
    private Connection connection = DatabaseConnection.getInstance().getConnection() ;

    public Utilisateur login(String username, String password) {
        String requete = "select *from utilisateurs where username = ? and password = ?";
        try {
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setUsername(resultSet.getString("username"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setRole(resultSet.getString("role"));
                return utilisateur;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Utilisateur utilisateur) {
        String requete = "insert into utilisateurs values(?, ?, ?, ?)" ;
        try {
            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, utilisateur.getId());
            preparedStatement.setString(2, utilisateur.getUsername());
            preparedStatement.setString(3, "1234");
            preparedStatement.setString(4, "secretaire");
            preparedStatement.executeUpdate();
            System.out.println("secretaire ajouté ");
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Utilisateur utilisateur) {

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        ProfesseurDAO professeurDAO = new ProfesseurDAO();
        Optional<Utilisateur> utilisateur = this.findById(id);
        if(utilisateur.isPresent()){
            if(utilisateur.get().getRole().equals("utilisateur")){
                return professeurDAO.delete(utilisateur.get().getId());
            }
            else{
                String requete="delete from utilisateurs where id = ? " ;
                try {
                    PreparedStatement ps = connection.prepareStatement(requete);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    System.out.println("utilisateur supprimer avec succes ");
                    return true;
                }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
            }
        }
        return false;
    }

    @Override
    public Optional<Utilisateur> findById(Integer id) {
        String requete = "SELECT * FROM utilisateurs WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(requete) ;
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur() ;
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setUsername(resultSet.getString("username"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setRole(resultSet.getString("role"));
                return Optional.of(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Utilisateur> getAll() {
        String requette = "select *from utilisateurs" ;
        List<Utilisateur> fetchedUtilisateurs= new ArrayList<Utilisateur>() ;
        ResultSet resultSet = null ;
        try{
            Statement stm = connection.createStatement() ;
            resultSet=stm.executeQuery(requette) ;
            while(resultSet.next()){
                Utilisateur utilisateur = new Utilisateur() ;
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setUsername(resultSet.getString("username"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setRole(resultSet.getString("role"));
                fetchedUtilisateurs.add(utilisateur) ;
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        return fetchedUtilisateurs ;
    }
}
