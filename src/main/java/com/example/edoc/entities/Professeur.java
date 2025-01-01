package com.example.edoc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professeur {
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
}
