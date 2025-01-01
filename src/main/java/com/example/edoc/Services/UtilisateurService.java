package com.example.edoc.Services;

import com.example.edoc.DAO.UtilisateurDAO;
import com.example.edoc.Entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public class UtilisateurService {
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurDAO.getAll();
    }
    public boolean deleteUtilisateur(Integer id) {
        return utilisateurDAO.delete(id)  ;
    }
    public Optional<Utilisateur> getUtilisateurById(Integer id) {
        return utilisateurDAO.findById(id) ;
    }
    public boolean updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.update(utilisateur);
    }
    public boolean addSecretaire(Utilisateur utilisateur) {
        return utilisateurDAO.create(utilisateur);
    }
}
