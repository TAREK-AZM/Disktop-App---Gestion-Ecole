package com.example.edoc.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscription {
    private int id;
    private int etudiantId;
    private int moduleId;
    private LocalDate dateInscription;
}
