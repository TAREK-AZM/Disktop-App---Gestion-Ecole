package com.example.edoc.Services;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
/*<<<<<<< HEAD

import java.util.List;

public class InscriptionService {
    private InscriptionDAO inscriptionDAO;

    // Constructeur qui initialise l'InscriptionDAO
    public InscriptionService() {
        this.inscriptionDAO = new InscriptionDAO();
    }

    // Méthode pour créer une inscription
    public boolean createInscription(Inscription inscription) {
        return inscriptionDAO.create(inscription);  // Appel de la méthode create du DAO
    }

    // Méthode pour supprimer une inscription
    public boolean deleteInscription(int inscriptionId) {
        return inscriptionDAO.delete(inscriptionId);  // Appel de la méthode delete du DAO
    }

    // Méthode pour obtenir les inscriptions par module
    public List<Inscription> getInscriptionsByModule(com.example.edoc.Entities.Module module) {
        return inscriptionDAO.getInscriptionsByModule(module);  // Appel de la méthode du DAO pour récupérer les inscriptions
    }

    // Méthode pour afficher les étudiants inscrits dans un module
    public List<Etudiant> getEtudiantsByModule(Module module) {
        return inscriptionDAO.getEtudiantsByModule(module);  // Appel de la méthode du DAO pour obtenir les étudiants
    }
=======*/
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Utils.DatabaseConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionService {
    private PreparedStatement preparedStatement ;
    private final InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private Connection connection = DatabaseConnection.getInstance().getConnection() ;

    public boolean create(Inscription inscription) {
        String requete = "insert into inscriptions (etudiant_id , module_id, date_inscription ) values( ? , ? ,?)";
        try {
            // check si un etudiant est deja inscrit pour le meme module
            if(this.checkInscription(inscription.getEtudiantId() , inscription.getModuleId()).isPresent()){
                System.out.println("inscription already exist");
                return false;
            }
            preparedStatement = connection.prepareStatement(requete);
            // the id is autoincrement in the db

            preparedStatement.setInt(1, inscription.getEtudiantId());
            preparedStatement.setInt(2, inscription.getModuleId());
            preparedStatement.setDate(3 , inscription.getDateInscription());
            preparedStatement.executeUpdate();
            System.out.println("etudiant inscription created");
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Integer> getAllModulesIds(int etudiantId ) {
        String req = "SELECT * FROM inscriptions WHERE etudiant_Id = ?";
        List<Integer> moduleIds = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, etudiantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moduleIds.add(  rs.getInt("module_id")) ;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return moduleIds;
    }

    public List<Etudiant> getEtudiantsByModule(Module module){
        return this.inscriptionDAO.getEtudiantsByModule(module);
    }
    public Optional<Inscription> checkInscription(int etudiantId , int moduleId){
        return this.inscriptionDAO.checkInscription(etudiantId, moduleId);
    }


}
