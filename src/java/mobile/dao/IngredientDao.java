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
import commun.model.Ingredient;

/**
 * Classe manipulant les ingrédients de la bdd
 *
 * @author INF-PORT-CR2
 */
public class IngredientDao {

    public IngredientDao() {

    }

    /**
     * Retourne la liste des ingrédients de la bdd
     *
     * @return
     */
    public Collection<Ingredient> liste() {

        Collection<Ingredient> colP = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        Statement stmt = null;

        try {

            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_ING, LIB_ING, STATUT_ING, STOCK_ING, PRIX_ING, IMG_ING FROM INGREDIENT");

            while (rs.next()) {

                long id = Long.parseLong(rs.getObject("ID_ING").toString());
                String libelle = rs.getString("LIB_ING");
                String statut = rs.getString("STATUT_ING");
                long quantitee = Long.parseLong(rs.getObject("STOCK_ING").toString());
                String prix = rs.getString("PRIX_ING");
                String image = rs.getString("IMG_ING");

                Ingredient p = new Ingredient(id, libelle, statut, quantitee, prix, image);
                modifierStatut(quantitee, statut);
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
     * Permet de modifier le statut d'un ingredient en fonction de sa quantitée
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
                stmtIndisponible.executeUpdate("UPDATE INGREDIENT SET STATUT_ING = 'Indisponible' WHERE STOCK_ING = 0");
            } else if ((stock > 0) && (statut.equalsIgnoreCase("Indisponible"))) {
                stmtDisponible = cnx.createStatement();
                stmtDisponible.executeUpdate("UPDATE INGREDIENT SET STATUT_ING = 'Disponible' WHERE STOCK_ING > 0 AND STATUT_ING = 'Indisponible'");
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
     * Ajoute la quantitée donnée pour un ingrédient en fonction de son ID
     *
     * @param idIngredient
     * @param quantitee
     */
    public void ajouteQuantitee(long idIngredient, long quantitee) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("UPDATE INGREDIENT SET STOCK_ING = STOCK_ING + ? WHERE ID_ING = ?");

            pstmt.setLong(1, quantitee);
            pstmt.setLong(2, idIngredient);

            pstmt.executeUpdate();

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
    }

    /**
     * Supprime la quantitée donnée pour un ingrédient en fonction de son ID
     *
     * @param idIngredient
     * @param quantitee
     */
    public void supprimeQuantitee(long idIngredient, long quantitee) {

        BDDHelper bddh = new BDDHelper();
        Connection cnx = bddh.open();
        PreparedStatement pstmt = null;

        try {

            pstmt = cnx.prepareStatement("UPDATE INGREDIENT SET STOCK_ING = STOCK_ING - ? WHERE ID_ING = ?");

            pstmt.setLong(1, quantitee);
            pstmt.setLong(2, idIngredient);

            pstmt.executeUpdate();

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
    }

}
