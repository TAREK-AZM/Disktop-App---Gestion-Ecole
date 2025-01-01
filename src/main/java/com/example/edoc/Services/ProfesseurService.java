package com.example.edoc.Services;

import com.example.edoc.DAO.ProfesseurDAO;
import com.example.edoc.Entities.Professeur;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;


@Data
public class ProfesseurService {
    private final ProfesseurDAO professeurDAO = new ProfesseurDAO();


    // create
    public boolean createProfesseur(Professeur professeur) {
        return  professeurDAO.create(professeur);
    }
    // delete
    public boolean deleteProfesseur(int id) {
        return professeurDAO.delete(id);
    }
    // update
    public boolean updateProfesseur(Professeur professeur) {
        return professeurDAO.update(professeur);
    }
    // get all professeurs
    public List<Professeur> getAllProfesseur() {
        return  professeurDAO.getAll() ;
    }
    // find prof by username
    public Optional<Professeur> getProfesseurByUsername(String nom) {
        return professeurDAO.findByUserName(nom) ;
    }
    //find by id
    public Optional<Professeur> getProfesseurById(int id) {
        return professeurDAO.findById(id);
    }
}
