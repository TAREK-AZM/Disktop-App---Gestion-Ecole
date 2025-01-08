package com.example.edoc.Services;

import com.example.edoc.DAO.ModuleDAO;
import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Professeur;

import java.util.List;
import java.util.Optional;

public class ModuleService {
    private final ModuleDAO Module_Dao=new ModuleDAO();

    public boolean CreateModule(Module module) {return Module_Dao.create(module);}
    public boolean UpdateModule( Module module) {return Module_Dao.update(module);}
    public boolean DeleteModule(Module module) {return Module_Dao.delete(module.getId());};
    public Optional<Module> GetModuleById(int id) {return Module_Dao.findById(id);}
    public List<Module> GetAllModules(){ return Module_Dao.getAll();}
    public List<Module> GetAllModulesOfProfesseur(Professeur professeur){ return Module_Dao.getAllModulesOfProfessor(professeur.getId());}

    // find by name of the module ?
    public int findByName(String nomModule){return Module_Dao.findByUserName(nomModule);}
}
