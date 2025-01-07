package com.example.edoc.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscription {
    private int id;
    private int etudiantId;
    private int moduleId;
    private Date dateInscription;

    public Inscription( int id ,int etudiantId, int moduleId) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.moduleId = moduleId;
        this.dateInscription = Date.valueOf(LocalDate.now());
    }
}
