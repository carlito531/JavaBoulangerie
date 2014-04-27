/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import commun.model.Client;

/**
 * Classe permettant la manipulation des Clients dans la bdd
 *
 * @author INF-PORT-CR2
 */
public class ClientDao {

    public ClientDao() {

    }

    /**
     * Retourne un Client en fonction de l'Id de la commande
     *
     * @param id
     * @return
     */
    public Client trouveAvecIdCommande(long id) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = null;
        Client c = null;

        try {

            cnx = bddh.open();
            PreparedStatement pstmt = cnx.prepareStatement("SELECT ID_CLI, NOM_CLI, PRENOM_CLI, ADR_CLI, CP_CLI, VILLE_CLI, MAIL_CLI, EST_CUISI, EST_BOUL FROM COMMANDE INNER JOIN CLIENT ON ID_CLI = CLI_ID WHERE ID_COM = ?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Long idcli = Long.parseLong(rs.getObject("ID_CLI").toString());
                String nom = rs.getString("NOM_CLI");
                String prenom = rs.getString("PRENOM_CLI");
                String adresse = rs.getString("ADR_CLI");
                int cp = rs.getInt("CP_CLI");
                String ville = rs.getString("VILLE_CLI");
                String mail = rs.getString("MAIL_CLI");
                Boolean estCuisi = rs.getBoolean("EST_CUISI");
                Boolean estBoul = rs.getBoolean("EST_BOUL");

                c = new Client(idcli, mail, adresse, nom, prenom, adresse, cp, ville, mail, estCuisi, estBoul);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return c;
    }

    /**
     * Permet d'ajouter un client dans la bdd
     *
     * @param c
     */
    public void ajoute(Client c) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        try {

            PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO CLIENT (LOGIN_CLI, MDP_CLI, NOM_CLI, PRENOM_CLI, ADR_CLI, CP_CLI, VILLE_CLI, MAIL_CLI, EST_CUISI, EST_BOUL) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ?) ");

            pstmt.setString(1, c.getLogin());
            pstmt.setString(2, c.getMotDePasse());
            pstmt.setString(3, c.getNom());
            pstmt.setString(4, c.getPrenom());
            pstmt.setString(5, c.getAdresse());
            pstmt.setInt(6, c.getCodePostal());
            pstmt.setString(7, c.getVille());
            pstmt.setString(8, c.getMail());
            pstmt.setBoolean(9, c.isCuisinier());
            pstmt.setBoolean(10, c.isCuisinier());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        bddh.close(cnx);
    }

    /**
     * Permet de supprimer un client de la bdd selon son ID
     *
     * @param id
     */
    public void supprime(Long id) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        PreparedStatement pstmt = null;
        try {

            pstmt = cnx.prepareStatement("DELETE FROM CLIENT WHERE ID_CLI = ?");
            pstmt.setLong(1, id);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        bddh.close(cnx);
    }

    /**
     * Retourne la liste des clients de la bdd
     *
     * @return
     */
    public Collection<Client> liste() {

        Collection<Client> colc = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        try {

            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_CLI, LOGIN_CLI, MDP_CLI, NOM_CLI, PRENOM_CLI, ADR_CLI, CP_CLI, VILLE_CLI, MAIL_CLI, EST_CUISI, EST_BOUL FROM CLIENT");

            while (rs.next()) {
                Long id = Long.parseLong(rs.getObject("ID_CLI").toString());
                String login = rs.getString("LOGIN_CLI");
                String motdepasse = rs.getString("MDP_CLI");
                String nom = rs.getString("NOM_CLI");
                String prenom = rs.getString("PRENOM_CLI");
                String adresse = rs.getString("ADR_CLI");
                int codePostal = rs.getInt("CP_CLI");
                String ville = rs.getString("VILLE_CLI");
                String mail = rs.getString("MAIL_CLI");
                Boolean estCuisi = rs.getBoolean("EST_CUISI");
                Boolean estBoul = rs.getBoolean("EST_BOUL");

                Client c = new Client(id, login, motdepasse, nom, prenom, adresse, codePostal, ville, mail, estCuisi, estBoul);
                colc.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        bddh.close(cnx);
        return colc;
    }

    /**
     * Retourne un client en fonction de son login
     *
     * @param login
     * @return
     */
    public Client trouve(String login) {

        Client p = null;
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("SELECT ID_CLI, LOGIN_CLI, MDP_CLI, NOM_CLI, PRENOM_CLI, ADR_CLI, CP_CLI, VILLE_CLI, MAIL_CLI, EST_CUISI, EST_BOUL FROM CLIENT WHERE LOGIN_CLI = ?");
            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Long id = Long.parseLong(rs.getObject("ID_CLI").toString());
                String logincli = rs.getString("LOGIN_CLI");
                String motdepasse = rs.getString("MDP_CLI");
                String nom = rs.getString("NOM_CLI");
                String prenom = rs.getString("PRENOM_CLI");
                String adresse = rs.getString("ADR_CLI");
                int codePostal = rs.getInt("CP_CLI");
                String ville = rs.getString("VILLE_CLI");
                String mail = rs.getString("MAIL_CLI");
                Boolean estCuisinier = rs.getBoolean("EST_CUISI");
                Boolean estBoulanger = rs.getBoolean("EST_BOUL");

                p = new Client(id, logincli, motdepasse, nom, prenom, adresse, codePostal, ville, mail, estCuisinier, estBoulanger);

            }

            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            if (pstmt != null) {
                try {

                    pstmt.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        bddh.close(cnx);
        return p;
    }
    
    public Client trouveAvecId(long idcli) {

        Client p = null;
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("SELECT ID_CLI, LOGIN_CLI, MDP_CLI, NOM_CLI, PRENOM_CLI, ADR_CLI, CP_CLI, VILLE_CLI, MAIL_CLI, EST_CUISI, EST_BOUL FROM CLIENT WHERE ID_CLI = ?");
            pstmt.setLong(1, idcli);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Long id = Long.parseLong(rs.getObject("ID_CLI").toString());
                String logincli = rs.getString("LOGIN_CLI");
                String motdepasse = rs.getString("MDP_CLI");
                String nom = rs.getString("NOM_CLI");
                String prenom = rs.getString("PRENOM_CLI");
                String adresse = rs.getString("ADR_CLI");
                int codePostal = rs.getInt("CP_CLI");
                String ville = rs.getString("VILLE_CLI");
                String mail = rs.getString("MAIL_CLI");
                Boolean estCuisinier = rs.getBoolean("EST_CUISI");
                Boolean estBoulanger = rs.getBoolean("EST_BOUL");

                p = new Client(id, logincli, motdepasse, nom, prenom, adresse, codePostal, ville, mail, estCuisinier, estBoulanger);

            }

            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            if (pstmt != null) {
                try {

                    pstmt.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        bddh.close(cnx);
        return p;
    }
}
