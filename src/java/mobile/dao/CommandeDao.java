/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.dao;

import commun.dao.BDDHelper;
import commun.dao.ClientDao;
import commun.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import mobile.model.Commande;

/**
 * Classe permettant la manipulation des commandes dans la bdd
 *
 * @author INF-PORT-CR2
 */
public class CommandeDao {

    public CommandeDao() {

    }

    /**
     * Retourne une commande en fonction de son ID
     *
     * @param id
     * @return
     */
    public Commande trouve(long id) {

        Commande p = null;
        Client c = null;
        ClientDao cdao = new ClientDao();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;

        try {

            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_COM, CHGT_STATUT_COM, DT_LIVR_COM, PRIX_TOT_COM, CLI_ID, LIB_STA FROM COMMANDE INNER JOIN STATUT ON ID_STA = STA_ID WHERE ID_COM = '" + id + "'");

            while (rs.next()) {

                id = Long.parseLong(rs.getObject("ID_COM").toString());
                String dateChgtStat = rs.getTimestamp("CHGT_STATUT_COM").toString();
                String dateLivraison = "";
                if (rs.getTimestamp("DT_LIVR_COM") == null) {

                    dateLivraison = "null";
                } else {
                    dateLivraison = rs.getTimestamp("DT_LIVR_COM").toString();
                }
                String prix = rs.getString("PRIX_TOT_COM");
                long idcli = Long.parseLong(rs.getObject("CLI_ID").toString());
                String statut = rs.getString("LIB_STA");

                c = cdao.trouveAvecId(idcli);
                p = new Commande(id, dateChgtStat, dateLivraison, prix, c, statut);
            }

            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            if (stmt != null) {
                try {

                    stmt.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        bddh.close(cnx);
        return p;
    }

    /**
     * Retourne le dernier ID de la table Commande Cette fonction sert pour
     * l'ajout de commande en base et pour préciser les Lignes de commandes qui
     * correspondent
     *
     * @return
     */
    public long retourneDernierId() {

        Commande p = null;
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;
        long id = 0;

        try {

            // permet de dire que le curseur du ResultSet n'a pas qu'un seul odre de passage et que les données
            // incorporées seront en lecture seule
            stmt = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT ID_COM FROM COMMANDE");

            // Tant que le curseur du resultSet n'est pas à la dernière valeur => suivant
            while (!rs.isLast()) {
                rs.next();
                id = rs.getLong("ID_COM");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    /**
     * Méthode permettant de changer le statut d'une commande ainsi que sa date
     * de changement de statut
     *
     * @param id
     * @param statut
     */
    public void changeStatut(long id, String statut) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        // Formatage de la date de changement de statut
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date utilDate = new java.util.Date();
        Timestamp ts = new Timestamp(utilDate.getTime());
        sdf.format(ts);

        try {

            bddh.open();
            PreparedStatement pstmt = cnx.prepareStatement("UPDATE COMMANDE SET STA_ID = ? WHERE ID_COM = ?");
            PreparedStatement pstmtDateLivraison = cnx.prepareStatement("UPDATE COMMANDE SET DT_LIVR_COM = ? WHERE ID_COM = ?");
            PreparedStatement pstmtDateChgStatut = cnx.prepareStatement("UPDATE COMMANDE SET CHGT_STATUT_COM = ? WHERE ID_COM = ?");

            switch (statut) {
                case "En attente":
                    pstmt.setInt(1, 1);
                    break;
                case "En préparation":
                    pstmt.setInt(1, 2);
                    break;
                case "Terminée":
                    pstmt.setInt(1, 3);
                    break;
                case "Livrée":
                    pstmt.setInt(1, 4);

                    pstmtDateLivraison.setTimestamp(1, ts);
                    pstmtDateLivraison.setLong(2, id);

                    pstmtDateLivraison.executeUpdate();
                    supprime(trouve(id));
                    break;
                default:
                    System.out.println("Erreur");
            }

            pstmt.setLong(2, id);
            pstmtDateChgStatut.setTimestamp(1, ts);
            pstmtDateChgStatut.setLong(2, id);

            pstmt.executeUpdate();
            pstmtDateChgStatut.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Retourne les commandes de la bdd
     *
     * @return
     */
    public Collection<Commande> liste() {

        Client c = null;
        ClientDao cdao = new ClientDao();
        Collection<Commande> colP = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;

        try {

            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_COM, CHGT_STATUT_COM, DT_LIVR_COM, PRIX_TOT_COM, CLI_ID, LIB_STA FROM COMMANDE INNER JOIN STATUT ON ID_STA = STA_ID ORDER BY CHGT_STATUT_COM ASC");

            while (rs.next()) {

                long id = Long.parseLong(rs.getObject("ID_COM").toString());
                String dateChgtStat = rs.getTimestamp("CHGT_STATUT_COM").toString();
                String dateLivraison = "";
                if (rs.getTimestamp("DT_LIVR_COM") == null) {
                    dateLivraison = "Pas encore livré";
                } else {
                    dateLivraison = rs.getTimestamp("DT_LIVR_COM").toString();
                }
                String prix = rs.getString("PRIX_TOT_COM");
                long idcli = Long.parseLong(rs.getObject("CLI_ID").toString());
                String statut = rs.getString("LIB_STA");

                c = cdao.trouveAvecId(idcli);
                Commande p = new Commande(id, dateChgtStat, dateLivraison, prix, c, statut);
                colP.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        bddh.close(cnx);
        return colP;
    }

    /**
     * Ajoute une commande dans la bdd
     *
     * @param c
     */
    public void ajoute(Commande c) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        try {
            PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO COMMANDE (ID_COM, DT_CREA_COM, CHGT_STATUT_COM, DT_LIVR_COM, PRIX_TOT_COM, CLI_ID, STA_ID) VALUES (?, ?, ?, ?, ?, ?, ?) ");

            pstmt.setLong(1, c.getId());
            pstmt.setString(2, c.getChgStatut());
            pstmt.setString(3, c.getChgStatut());
            pstmt.setString(4, c.getDateLivraison());
            pstmt.setString(5, c.getPrixTotale());
            pstmt.setLong(6, c.getClient().getId());
            pstmt.setLong(7, Long.parseLong(c.getStatut()));
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            bddh.close(cnx);
        }
    }

    /**
     * Supprime la commande passé en paramètre
     * @param c 
     */
    public void supprime(Commande c) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;

        try {

            stmt = cnx.createStatement();
            stmt.executeQuery("DELETE FROM COMMANDE WHERE ID_COM = '" + c.getId() + "'");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            bddh.close(cnx);
        }

    }

}
