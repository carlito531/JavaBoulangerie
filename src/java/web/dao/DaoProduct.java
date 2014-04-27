package web.dao;

import commun.model.Produit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class DaoProduct {
    
    private Connection cnx;

    public DaoProduct(Connection cnx) {
        this.cnx = cnx;
    }
    
    public Produit findProductById(long id){
        Produit p = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO FROM PRODUIT WHERE ID_PRO = '" + id + "'");
            if (rs.next()){
                String lib = rs.getString("LIB_PRO");
                String stat = rs.getString("STATUT_PRO");
                long stock = 0;
                if (rs.getObject("STOCK_PRO") == null){
                    stock = 0;
                }else{
                    stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                }                
                String prix = "";
                if (rs.getString("PRIX_PRO") == null){
                    prix = "0,00";
                }else{
                    prix = rs.getString("PRIX_PRO");
                }
                String img = "";
                if (rs.getString("IMG_PRO") == null){
                    img = "";
                }else{
                    img = rs.getString("IMG_PRO");
                }
                p = new Produit(id, lib, stat, stock, prix, img);
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
        
        return p;       
    }
    
    public Produit findProductByLibelle(String lib){
        Produit p = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO FROM PRODUIT WHERE LIB_PRO = '" + lib + "'");
            if (rs.next()){
                long id = Long.parseLong(rs.getObject("ID_PRO").toString());
                String stat = rs.getString("STATUT_PRO");
                long stock = 0;
                if (rs.getObject("STOCK_PRO") == null){
                    stock = 0;
                }else{
                    stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                }                
                String prix = "";
                if (rs.getString("PRIX_PRO") == null){
                    prix = "";
                }else{
                    prix = rs.getString("PRIX_PRO");
                }
                String img = "";
                if (rs.getString("IMG_PRO") == null){
                    img = "";
                }else{
                    img = rs.getString("IMG_PRO");
                }
                p = new Produit(id, lib, stat, stock, prix, img);
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
        
        return p;       
    }
    
    public Collection<Produit> getListProductByIdCategorie(int idCat){
        Collection<Produit> productList = new ArrayList();
        
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO FROM PRODUIT WHERE CAT_ID = '" + idCat + "'");
            while(rs.next()){
                long id = Long.parseLong(rs.getObject("ID_PRO").toString());
                String lib = rs.getString("LIB_PRO");
                String stat = rs.getString("STATUT_PRO");
                long stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                String prix = rs.getString("PRIX_PRO");
                String img = rs.getString("IMG_PRO");
                
                Produit p = new Produit(id, lib, stat, stock, prix, img);
                productList.add(p);                
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
         
         return productList;       
    }
    
    public void updateStockProduit(Produit p){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("UPDATE PRODUIT SET STOCK_PRO = '" + p.getStock() + "' WHERE ID_PRO = '" + p.getId() + "'");
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
