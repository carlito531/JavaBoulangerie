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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import commun.model.Produit;

/**
 * Classe manipulant les produits de la bdd
 *
 * @author INF-PORT-CR2
 */
public class ProduitDao {

    /**
     * Retourne la disponibilitée d'un produit en fonction de son ID
     *
     * @param id
     * @return
     */
    public boolean estDisponible(long id) {

        boolean dispo = false;
        Produit p = null;

        try {
            p = trouve(id);

            if (p.getStatut().equalsIgnoreCase("Disponible")) {
                dispo = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dispo;
    }

    /**
     * Retourne un produit en fonction de son ID
     *
     * @param id
     * @return
     */
    public Produit trouve(Long id) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Produit p = null;

        try {

            pstmt = cnx.prepareStatement("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO, CAT_ID FROM PRODUIT WHERE ID_PRO = ?");
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Long idProduit = Long.parseLong(rs.getObject("ID_PRO").toString());
                String libelle = rs.getString("LIB_PRO");
                String statut = rs.getString("STATUT_PRO");
                Long stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                String prix = rs.getString("PRIX_PRO");
                String img = rs.getString("IMG_PRO");
                int categorieId = rs.getInt("CAT_ID");

                p = new Produit(idProduit, libelle, statut, stock, prix, img, categorieId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        bddh.close(cnx);
        return p;
    }

    /**
     * Retourne les produits dans la bdd
     *
     * @return
     */
    public Collection<Produit> liste() {

        Collection<Produit> colp = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;

        try {

            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO, CAT_ID FROM PRODUIT");

            while (rs.next()) {

                Long id = Long.parseLong(rs.getObject("ID_PRO").toString());
                String libelle = rs.getString("LIB_PRO");
                String statut = rs.getString("STATUT_PRO");
                Long stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                String prix = rs.getString("PRIX_PRO");
                String img = rs.getString("IMG_PRO");
                int categorieId = rs.getInt("CAT_ID");

                Produit p = new Produit(id, libelle, statut, stock, prix, img, categorieId);
                modifierStatut(stock, statut);
                colp.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    cnx.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return colp;
    }

    /**
     * Passe le statut d'un produit en disponible/indisponible en fonction de la
     * quantitée de celui-ci en stock
     *
     * @param stock
     * @param statut
     */
    public void modifierStatut(long stock, String statut) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmtDisponible = null;
        Statement stmtIndisponible = null;

        try {

            if (stock == 0) {
                stmtIndisponible = cnx.createStatement();
                stmtIndisponible.executeUpdate("UPDATE PRODUIT SET STATUT_PRO = 'Indisponible' WHERE STOCK_PRO = 0");
            } else if ((stock > 0) && (statut.equalsIgnoreCase("Indisponible"))) {
                stmtDisponible = cnx.createStatement();
                stmtDisponible.executeUpdate("UPDATE PRODUIT SET STATUT_PRO = 'Disponible' WHERE STOCK_PRO > 0 AND STATUT_PRO = 'Indisponible'");
            } else {
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Augmente la quantitée de produits en fonction de la quantitée donnée
     *
     * @param idProduit
     * @param quantitee
     */
    public void ajouteQuantitee(long idProduit, long quantitee) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("UPDATE PRODUIT SET STOCK_PRO = STOCK_PRO + ? WHERE ID_PRO = ?");

            pstmt.setLong(1, quantitee);
            pstmt.setLong(2, idProduit);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                    cnx.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Supprime la quantitée de produit en fonction de la quantitée donnée
     *
     * @param idProduit
     * @param quantitee
     */
    public void supprimeQuantitee(long idProduit, long quantitee) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("UPDATE PRODUIT SET STOCK_PRO = STOCK_PRO - ? WHERE ID_PRO = ?");

            pstmt.setLong(1, quantitee);
            pstmt.setLong(2, idProduit);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                    cnx.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Retourne tous les produits dans la categorie donnée
     *
     * @param idCategorie
     * @return
     */
    public Collection<Produit> listeParCategorie(int idCategorie) {

        Collection<Produit> colp = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO, CAT_ID FROM PRODUIT WHERE CAT_ID = ?");
            pstmt.setInt(1, idCategorie);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Long id = Long.parseLong(rs.getObject("ID_PRO").toString());
                String libelle = rs.getString("LIB_PRO");
                String statut = rs.getString("STATUT_PRO");
                Long stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                String prix = rs.getString("PRIX_PRO");
                String img = rs.getString("IMG_PRO");
                int categorieId = rs.getInt("CAT_ID");

                Produit p = new Produit(id, libelle, statut, stock, prix, img, categorieId);
                modifierStatut(stock, statut);
                colp.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        bddh.close(cnx);
        return colp;
    }
}
