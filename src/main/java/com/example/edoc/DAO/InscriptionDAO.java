package com.example.edoc.DAO;


import com.example.edoc.Entities.Inscription;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
import com.example.edoc.Utils.DatabaseConnection;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InscriptionDAO implements CRUD <Inscription, Integer> {
    private Connection connection;

    // Constructeur qui prend une connexion comme paramètre
    public InscriptionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public boolean create(Inscription inscription) {
        String query = "INSERT INTO Inscriptions (etudiant_id, module_id, date_inscription) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, inscription.getEtudiantId());
            ps.setInt(2, inscription.getModuleId());
            ps.setDate(3, Date.valueOf(inscription.getDateInscription().toLocalDate()));  // Convertir LocalDate en java.sql.Date

            return ps.executeUpdate() > 0;  // Retourne vrai si une ligne a été insérée
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'inscription : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Inscription inscription) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM Inscriptions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;  // Retourne vrai si une ligne a été supprimée
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'inscription : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour récupérer les inscriptions par module
    public List<Inscription> getInscriptionsByModule(Module module) {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM Inscriptions WHERE module_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Assurez-vous que le module a un id valide
            if (module != null) {
                ps.setInt(1, module.getId());  // On passe l'ID du module
            } else {
                System.err.println("Module fourni est null");
                return inscriptions;  // Si le module est null, on retourne une liste vide
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int etudiantId = rs.getInt("etudiant_id");

                // Créer l'objet Inscription
                inscriptions.add(new Inscription(id, etudiantId, module.getId()));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des inscriptions par module : " + e.getMessage());
        }

        return inscriptions;  // Retourne la liste des inscriptions associées au module
    }


    // Méthode pour afficher les étudiants inscrits dans un module
    public List<Etudiant> getEtudiantsByModule(Module module) {
        List<Inscription> inscriptions = getInscriptionsByModule(module);

        // Liste des étudiants inscrits
        List<Etudiant> etudiants = new ArrayList<>();  // Initialisation de la liste des étudiants

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);  // Créez une instance d'EtudiantDAO avec la connexion

        for (Inscription inscription : inscriptions) {
            Optional<Etudiant> etudiant = etudiantDAO.findById(inscription.getEtudiantId());  // Récupérez l'étudiant par ID
            if (etudiant.isPresent()) {
                etudiants.add(etudiant.get());  // Ajoutez l'étudiant à la liste
            }
        }

        return etudiants;
    }


    @Override
    public Optional<Inscription> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Inscription> getAll() {
        return List.of();
    }
}
