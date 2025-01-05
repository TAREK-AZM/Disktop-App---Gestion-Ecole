package com.example.edoc.DAO;

import com.example.edoc.Utils.DatabaseConnection;
import com.example.edoc.Entities.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleDAO implements CRUD<Module, Integer> {
    private Connection myConnection = DatabaseConnection.getInstance().getConnection();


    @Override
    public boolean create(Module module) {
        String req = "INSERT INTO modules ( nom_Module, code_Module, professeur_Id) VALUES ( ?, ?, ?)";
        try {
            PreparedStatement ps = myConnection.prepareStatement(req);
//            ps.setInt(1, module.getId());             // Parameter 1: id
            ps.setString(1, module.getNomModule());   // Parameter 2: nomModule
            ps.setString(2, module.getCodeModule());  // Parameter 3: codeModule
            ps.setInt(3, module.getProfesseurId());   // Parameter 4: professeurId
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(Module module) {
        String req = "UPDATE modules SET nom_Module=?, code_Module=?, professeur_Id=? WHERE id=?";
        try {
            PreparedStatement ps = myConnection.prepareStatement(req);
            ps.setString(1, module.getNomModule());  // Set the nomModule parameter
            ps.setString(2, module.getCodeModule()); // Set the codeModule parameter
            ps.setInt(3, module.getProfesseurId());  // Set the professeurId parameter
            ps.setInt(4, module.getId());           // Set the id parameter
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Integer id) {
        String req = "DELETE FROM modules WHERE id=?";
        try {
            PreparedStatement ps = myConnection.prepareStatement(req);
            ps.setInt(1, id); // Set the id parameter
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public Optional<Module> findById(Integer id) {
        String req = "SELECT * FROM modules WHERE id=?";
        try {
            PreparedStatement ps = myConnection.prepareStatement(req);
            ps.setInt(1, id); // Set the id parameter
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Module module = new Module(
                        rs.getInt("id"),
                        rs.getString("nom_Module"),
                        rs.getString("code_Module"),
                        rs.getInt("professeur_Id")
                );
                return Optional.of(module);
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

//////////////// Admin Method ////////////////
    @Override
    public List<Module> getAll() {
        String req = "select * from modules";
        List<Module> modules = new ArrayList<>();
        try {
            Statement ps = myConnection.createStatement();
            ResultSet rs = ps.executeQuery(req);
            while (rs.next()) {
                Module module = new Module(
                        rs.getInt("id"),
                        rs.getString("nom_Module"),
                        rs.getString("code_Module"),
                        rs.getInt("professeur_Id")
                );
                modules.add(module);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return modules;
    }


    ////////////// Profisseur Method /////////////////////////
    public List<Module> getAllModulesOfProfessor(Integer professeurId) {
        String req = "SELECT * FROM modules WHERE professeur_Id = ?";
        List<Module> modules = new ArrayList<>();
        try (PreparedStatement ps = myConnection.prepareStatement(req)) {
            ps.setInt(1, professeurId); // Set the professeurId parameter
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Module module = new Module(
                        rs.getInt("id"),
                        rs.getString("nom_Module"),
                        rs.getString("code_Module"),
                        rs.getInt("professeur_Id")

                );
                modules.add(module);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return modules;
    }


}
