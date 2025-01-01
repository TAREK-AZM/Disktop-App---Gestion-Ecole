package com.example.edoc.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    private int id;
    private String username;
    private String password;
    private String role;
}
