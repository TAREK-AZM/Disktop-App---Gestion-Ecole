package com.example.edoc.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String promo;
}

