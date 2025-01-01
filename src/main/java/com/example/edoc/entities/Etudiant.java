package com.example.edoc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String promo;
}

