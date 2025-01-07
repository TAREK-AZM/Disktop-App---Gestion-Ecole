package com.example.edoc.Services;

import com.example.edoc.DAO.InscriptionDAO;
import com.example.edoc.Entities.Etudiant;
import com.example.edoc.Entities.Inscription;
import com.example.edoc.Entities.Module;
import com.example.edoc.Entities.Professeur;
import com.example.edoc.Utils.DatabaseConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionService {
    private PreparedStatement preparedStatement ;
    private final InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private Connection connection = DatabaseConnection.getInstance().getConnection() ;

    public boolean create(Inscription inscription) {
        String requete = "insert into inscriptions (etudiant_id , module_id, date_inscription ) values( ? , ? ,?)";
        try {
            preparedStatement = connection.prepareStatement(requete);
            // the id is autoincrement in the db

            preparedStatement.setInt(1, inscription.getEtudiantId());
            preparedStatement.setInt(2, inscription.getModuleId());
            preparedStatement.setDate(3 , inscription.getDateInscription());
            preparedStatement.executeUpdate();
            System.out.println("etudiant inscription created");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Integer> getAllModulesIds(int etudiantId ) {
        String req = "SELECT * FROM inscriptions WHERE etudiant_Id = ?";
        List<Integer> moduleIds = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, etudiantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moduleIds.add(  rs.getInt("module_id")) ;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return moduleIds;
    }

    public List<Etudiant> getEtudiantsByModule(Module module){
        return this.inscriptionDAO.getEtudiantsByModule(module);
    }


}
