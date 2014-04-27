package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import commun.model.Ingredient;

public class DaoIngredient {
    private Connection cnx;

    public DaoIngredient(Connection cnx) {
        this.cnx = cnx;
    }
    
    public Ingredient findIngredientById(long id){
        Ingredient i = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LIB_ING, STATUT_ING, STOCK_ING, PRIX_ING, IMG_ING, TYPE_ID FROM INGREDIENT WHERE ID_ING = '" + id + "'");
            if (rs.next()){
                String lib = rs.getString("LIB_ING");
                String stat = rs.getString("STATUT_ING");
                long stock = Long.parseLong(rs.getObject("STOCK_ING").toString());
                String prix = rs.getString("PRIX_ING");
                String img = "";
                if (rs.getString("IMG_ING") == null){
                    img = "";
                }else{
                    img = rs.getString("IMG_ING");
                }
                int idType = Integer.parseInt(rs.getObject("TYPE_ID").toString());
                
                i = new Ingredient(id, lib, stat, stock, prix, img, idType);
            }            
        } catch(SQLException ex) {
            ex.printStackTrace();
        }finally {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return i;       
    }
    
    public Collection<Ingredient> getListOfIngredientsByIdCategorieAndIdType(int idCat, int idType){
        Collection<Ingredient> ingredientList = new ArrayList<Ingredient>();
        
        Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_ING, LIB_ING, STATUT_ING, STOCK_ING, PRIX_ING, IMG_ING, [TYPE_ID] FROM INGREDIENT " +
                                             "WHERE [TYPE_ID] = '" + idType + "' AND INGREDIENT.ID_ING IN (SELECT ID_ING FROM CATEGORIEINGREDIENT " +
                                             "INNER JOIN CATEGORIE ON CATEGORIEINGREDIENT.ID_CAT = CATEGORIE.ID_CAT " +
                                             "WHERE CATEGORIE.ID_CAT = '" + idCat + "')");
            while(rs.next()){
                long id = Long.parseLong(rs.getObject("ID_ING").toString());
                String libelle = rs.getString("LIB_ING");
                String statut = rs.getString("STATUT_ING");
                long stock = Long.parseLong(rs.getObject("STOCK_ING").toString());
                String prix = rs.getString("PRIX_ING");
                String urlImg = "";
                if (rs.getString("IMG_ING") == null)
                {
                    urlImg = "NULL";
                }else
                {
                    urlImg = rs.getString("IMG_ING");
                }
                                                
                Ingredient ing = new Ingredient(id, libelle, statut, stock, prix, urlImg, idType);
                ingredientList.add(ing);                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            if(stmt != null) {
                   try {
                       stmt.close();
                   } catch(SQLException ex) {
                       ex.printStackTrace();
                   }
               }
         }
         
         return ingredientList;
    }
    
    public void updateStockIngredient(Ingredient i){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("UPDATE INGREDIENT SET STOCK_ING = '" + i.getStock() + "' WHERE ID_ING = '" + i.getIdentifiant() + "'");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
           if(stmt != null) {
               try {
                   stmt.close();
               } catch(SQLException ex) {
                   ex.printStackTrace();
               }
           }
         }
    }
}
