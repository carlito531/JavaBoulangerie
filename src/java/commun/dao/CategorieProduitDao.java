/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import commun.model.CategorieProduit;

/**
 * Classe permettant la manipulation des catégories de produits dans la bdd
 *
 * @author INF-PORT-CR2
 */
public class CategorieProduitDao {

    /**
     * Retourne la liste des catégories de produit
     *
     * @return
     */
    public Collection<CategorieProduit> liste() {

        Collection<CategorieProduit> colcp = new ArrayList();
        BDDHelper bddh = new BDDHelper();
        Connection cnx = null;
        Statement stmt = null;

        try {
            cnx = bddh.open();
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_CAT, LIB_CAT FROM CATEGORIE");

            while (rs.next()) {

                int id = rs.getInt("ID_CAT");
                String libelle = rs.getString("LIB_CAT");

                CategorieProduit cp = new CategorieProduit(id, libelle);
                colcp.add(cp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        bddh.close(cnx);
        return colcp;
    }

    public CategorieProduit findCategorieByLibelle(String lib) {

        CategorieProduit c = null;
        Statement stmt = null;
        BDDHelper bddh = new BDDHelper();
        Connection cnx = null;

        try {
            cnx = bddh.open();
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_CAT FROM CATEGORIE WHERE LIB_CAT = '" + lib + "'");
            if (rs.next()) {
                int id = rs.getInt("ID_CAT");

                c = new CategorieProduit(id, lib);
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

        return c;
    }

}
