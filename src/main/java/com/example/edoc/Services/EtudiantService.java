package com.example.edoc.Services;

import com.example.edoc.DAO.EtudiantDAO;
import com.example.edoc.Entities.Etudiant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor

public class EtudiantService {
    private EtudiantDAO etudiantDAO;

    public EtudiantService(){
        etudiantDAO = new EtudiantDAO();
    }

    public boolean create(Etudiant etudiant) {
        return etudiantDAO.create(etudiant);
    }

    public boolean update(Etudiant etudiant) {
        return etudiantDAO.update(etudiant);
    }

    public boolean delete(Integer etudiantId) {
        return etudiantDAO.delete(etudiantId);
    }

    public Optional<Etudiant> findById(Integer etudiantId) {
        return etudiantDAO.findById(etudiantId);
    }

    public List<Etudiant> getAll() {
        return etudiantDAO.getAll();
    }

}
