package BD;
import MainClassesPackage.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTablesToObjects {
	
        // URL et informations de connexion

    

    // Charger les institutions
    public  ArrayList<Institution> loadInstitutions(Connection connection) throws SQLException {
        ArrayList<Institution> institutions = new ArrayList<>();
        String query = "SELECT * FROM institution";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nom = rs.getString("nom");
                String ville = rs.getString("ville");
                String type = rs.getString("type");
                int capacite = rs.getInt("capacitee");
                boolean priveeOuPublique = rs.getBoolean("priveeOuPublique");

                // Créer un objet Institution
                Institution institution = new Institution(nom, ville, type, priveeOuPublique ? "Privée" : "Publique", capacite) {};
                institution.setFilieres(loadFilieres(connection, nom, ville));
                institutions.add(institution);
            }
        }
        return institutions;
    }

    // Charger les filières pour une institution donnée
    public  ArrayList<Filiere> loadFilieres(Connection connection, String institutionNom, String institutionVille) throws SQLException {
        ArrayList<Filiere> filieres = new ArrayList<>();
        String query = "SELECT * FROM Filiere WHERE nominstitution = ? AND villeinstitution = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, institutionNom);
            stmt.setString(2, institutionVille);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("nom");
                    int duree = rs.getInt("duree");
                    String typeDiplome = rs.getString("typeDiplome");
                    String domaineNom = rs.getString("domaine");

                    // Charger les conditions d'entrée pour la filière
                    ConditionEntree conditionEntree = loadConditions(connection, nom, institutionNom, institutionVille);

                    // Créer un objet Domaine
                    Domaine domaine = new Domaine();
                    domaine.setNom(domaineNom);

                    // Créer un objet Filiere
                    Filiere filiere = new Filiere(nom, duree, typeDiplome, domaine);
                    filiere.setCE(conditionEntree);

                    // Charger les matières pour la filière
                    filiere.setMatieres(loadMatieres(connection, nom, institutionNom, institutionVille));

                    filieres.add(filiere);
                }
            }
        }
        return filieres;
    }

    // Charger les conditions d'entrée pour une filière donnée
    public ConditionEntree loadConditions(Connection connection, String filiereNom, String institutionNom, String institutionVille) throws SQLException {
        String query = "SELECT * FROM ConditionsDentrees WHERE filierenom = ? AND filierenominstitution = ? AND filierevilleinstitution = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, filiereNom);
            stmt.setString(2, institutionNom);
            stmt.setString(3, institutionVille);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int ageMax = rs.getInt("ageMax");
                    float noteMinPara = rs.getFloat("noteMinPara");
                    int classementUnivMin = rs.getInt("classementUnivMin");
                    int classementMinMp = rs.getInt("classementMinMp");
                    int classementMinPsi = rs.getInt("classementMinPsi");
                    int classementMinTsi = rs.getInt("classementMinTsi");
                    int classementMinEc = rs.getInt("classementMinEc");

                    return new ConditionEntree(ageMax, noteMinPara, classementUnivMin, classementMinMp, classementMinPsi, classementMinTsi, classementMinEc);
                }
            }
        }
        return null;
    }

    // Charger les matières pour une filière donnée
    public  ArrayList<Matiere> loadMatieres(Connection connection, String filiereNom, String institutionNom, String institutionVille) throws SQLException {
        ArrayList<Matiere> matieres = new ArrayList<>();
        String query = "SELECT * FROM Matiere WHERE filierenom = ? AND filierenominstitution = ? AND filierevilleinstitution = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, filiereNom);
            stmt.setString(2, institutionNom);
            stmt.setString(3, institutionVille);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("nom");
                    int coefficient = rs.getInt("coefficient");
                    String enseignant = rs.getString("enseignant");

                    // Créer un objet Matiere
                    Matiere matiere = new Matiere(nom, coefficient, enseignant);
                    matieres.add(matiere);
                }
            }
        }
        return matieres;
    }
}

