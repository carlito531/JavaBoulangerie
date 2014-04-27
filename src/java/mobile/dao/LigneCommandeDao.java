/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.dao;

import commun.dao.BDDHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.LigneCommande;

/**
 * Classe manipulant les lignes de commandes de la bdd
 *
 * @author INF-PORT-CR2
 */
public class LigneCommandeDao {

    public LigneCommandeDao() {

    }

    /**
     * Retourne les lignes de commandes de la bdd
     *
     * @param id
     * @return
     */
    public Collection<LigneCommande> trouveAvecIdCommande(long id) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = null;
        Collection<LigneCommande> collc = new ArrayList();
        LigneCommande lc = null;

        try {

            cnx = bddh.open();
            PreparedStatement pstmt = cnx.prepareStatement("SELECT ID_LIGNE_COM, QUANT_PRODUIT, PRIX_UNIT_PRODUIT, TOTAL_PRIX_PRODUIT, COM_ID, PRO_ID FROM LIGNE_COMMANDE WHERE COM_ID = ?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                long idLigneCommande = rs.getLong("ID_LIGNE_COM");
                int quantite = rs.getInt("QUANT_PRODUIT");
                String prixUnitaire = rs.getString("PRIX_UNIT_PRODUIT");
                String prixTotale = rs.getString("TOTAL_PRIX_PRODUIT");
                long comId = rs.getLong("COM_ID");
                long proId = rs.getLong("PRO_ID");

                lc = new LigneCommande(idLigneCommande, quantite, prixUnitaire, prixTotale, comId, proId);
                collc.add(lc);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return collc;
    }

    public Boolean verifierDisponibilitee(long idCommande, long idProduit) {

        BDDHelper bddh = null;
        Connection cnx = null;
        PreparedStatement pstmt = null;
        Boolean estDisponible = false;

        try {

            cnx = bddh.open();
            pstmt = cnx.prepareStatement("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO FROM LIGNE_COMMANDE INNER JOIN PRODUIT ON ID_PRO = PRO_ID WHERE PRO_ID = ? AND COM_ID = ?");
            pstmt.setLong(1, idProduit);
            pstmt.setLong(2, idCommande);

        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return estDisponible;
    }

    /**
     * Ajoute une ligne de commande à une commande
     *
     * @param lc
     */
    public void ajouteLigneACommande(LigneCommande lc) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();

        try {
            PreparedStatement pstmt = cnx.prepareStatement("INSERT INTO LIGNE_COMMANDE (QUANT_PRODUIT, PRIX_UNIT_PRODUIT, TOTAL_PRIX_PRODUIT, COM_ID, PRO_ID) VALUES (?, ?, ?, ?, ?)");

            pstmt.setInt(1, lc.getQuantite());
            pstmt.setString(2, lc.getPrixUnitaire());
            pstmt.setString(3, lc.getPrixTotale());
            pstmt.setLong(4, lc.getComId());
            pstmt.setLong(5, lc.getProduitId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        bddh.close(cnx);
    }
}
