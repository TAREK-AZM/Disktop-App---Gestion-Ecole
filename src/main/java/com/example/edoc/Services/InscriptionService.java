package com.example.edoc.Services;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;

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
}
