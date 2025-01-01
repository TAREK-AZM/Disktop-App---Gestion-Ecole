package com.example.edoc.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Module {
    private int id;
    private String nomModule;
    private String codeModule;
    private int professeurId;
}
