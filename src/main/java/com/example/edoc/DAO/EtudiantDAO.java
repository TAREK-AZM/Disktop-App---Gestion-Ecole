package com.example.edoc.DAO;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Module;
import com.example.edoc.Utils.DatabaseConnection;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

public class EtudiantDAO implements CRUD <Etudiant, Integer> {

    private Connection connection;

    public EtudiantDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public boolean create(Etudiant etudiant) {
        try {
            String query = "INSERT INTO Etudiants ( matricule, nom, prenom, date_naissance, email, promo) VALUES ( ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
//                ps.setInt(1, etudiant.getId());
                ps.setString(1, etudiant.getMatricule());
                ps.setString(2, etudiant.getNom());
                ps.setString(3, etudiant.getPrenom());
                ps.setDate(4, new java.sql.Date(etudiant.getDateNaissance().getTime()));
                ps.setString(5, etudiant.getEmail());
                ps.setString(6, etudiant.getPromo());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'étudiant : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Etudiant etudiant) {
        try {
            String query = "UPDATE Etudiants SET nom = ?, prenom = ?, date_naissance = ?, email = ?, promo = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, etudiant.getNom());
                ps.setString(2, etudiant.getPrenom());
                ps.setDate(3, etudiant.getDateNaissance());
                ps.setString(4, etudiant.getEmail());
                ps.setString(5, etudiant.getPromo());
                ps.setInt(6, etudiant.getId());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'étudiant : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            String query = "DELETE FROM Etudiants WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Etudiant> findById(Integer id) {
        try {
            String query = "SELECT * FROM Etudiants WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Etudiant etudiant = new Etudiant();
                        etudiant.setId(rs.getInt("id"));
                        etudiant.setMatricule(rs.getString("matricule"));
                        etudiant.setNom(rs.getString("nom"));
                        etudiant.setPrenom(rs.getString("prenom"));
                        etudiant.setDateNaissance(rs.getDate("date_naissance"));
                        etudiant.setEmail(rs.getString("email"));
                        etudiant.setPromo(rs.getString("promo"));

                        return Optional.of(etudiant);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'étudiant : " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Etudiant> getAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            String query = "SELECT * FROM Etudiants";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Etudiant etudiant = new Etudiant();
                    etudiant.setId(rs.getInt("id"));
                    etudiant.setMatricule(rs.getString("matricule"));
                    etudiant.setNom(rs.getString("nom"));
                    etudiant.setPrenom(rs.getString("prenom"));
                    etudiant.setDateNaissance(rs.getDate("date_naissance"));
                    etudiant.setEmail(rs.getString("email"));
                    etudiant.setPromo(rs.getString("promo"));

                    etudiants.add(etudiant);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
        }
        return etudiants;
    }

    public int findByUserName(String nom) {
        String requete = "SELECT id FROM etudiants WHERE nom = ?";
        try { // Replace with your connection method
            PreparedStatement preparedStatement = connection.prepareStatement(requete) ;
            preparedStatement.setString(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Etudiant etudiant = new Etudiant() ;
               etudiant.setId(resultSet.getInt("id"));
                return resultSet.getInt("id") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1 ;
    }

}
