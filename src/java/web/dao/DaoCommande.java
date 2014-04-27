package web.dao;

import commun.model.Client;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import web.model.Commande;
import web.model.IngredientOfProduct;
import commun.model.Produit;
import commun.model.Ingredient;
import web.model.ProductOfPanier;

public class DaoCommande {
    private Connection cnx;

    public DaoCommande(Connection cnx) {
        this.cnx = cnx;
    }
    
    public long getIdCommandeAvailable(){
        Statement stmt = null;
        long id = 0;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 1 ID_COM FROM COMMANDE ORDER BY ID_COM DESC");   
            if(rs.next()){
                id = Long.parseLong(rs.getObject("ID_COM").toString());
                id = id + 1;
            }else{
                // Si la méthode ResultSet.next() renvoie faux c'est qu'il n'existe aucune commande en base on attribue donc l'identifiant 1 à la commande
                id = 1;
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
         return id;
    }
    
    public long getIdRecetteAvailable(){
        Statement stmt = null;
        long id = 0;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 1 ID_RECETTE FROM RECETTE ORDER BY ID_RECETTE DESC");   
            if(rs.next()){
                id = Long.parseLong(rs.getObject("ID_RECETTE").toString());
                id = id + 1;
            }else{
                // Si la méthode ResultSet.next() renvoie faux c'est qu'il n'existe aucune commande en base on attribue donc l'identifiant 1 à la commande
                id = 1;
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
         return id;
    }
    
    public Collection<ProductOfPanier> getListOfProductByIdCommande(long idCom)
    {
        Collection<ProductOfPanier> myList = new ArrayList<ProductOfPanier>();
        Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_PRO, LIB_PRO, STATUT_PRO, STOCK_PRO, PRIX_PRO, IMG_PRO, LIGNE_COMMANDE.QUANT_PRODUIT, " +
                    "LIGNE_COMMANDE.TOTAL_PRIX_PRODUIT, LIGNE_COMMANDE.RECETTE_ID FROM PRODUIT INNER JOIN LIGNE_COMMANDE ON PRODUIT.ID_PRO = LIGNE_COMMANDE.PRO_ID " +
                    "WHERE LIGNE_COMMANDE.COM_ID = '" + idCom + "'");
            while(rs.next()){
                long id = Long.parseLong(rs.getObject("ID_PRO").toString());
                String lib = rs.getString("LIB_PRO");
                String statut = rs.getString("STATUT_PRO");
                long stock = 0;                
                if (rs.getObject("STOCK_PRO") == null){
                    stock = 0;
                }else{
                    stock = Long.parseLong(rs.getObject("STOCK_PRO").toString());
                }                
                String prixUnit = "";
                if (rs.getString("PRIX_PRO") == null){
                    prixUnit = "";
                }else{
                    prixUnit = rs.getString("PRIX_PRO");
                }                
                String img = "";
                if (rs.getString("IMG_PRO") == null){
                    img = "";
                }else{
                    img = rs.getString("IMG_PRO");
                }
                int qteProduit = Integer.parseInt(rs.getObject("QUANT_PRODUIT").toString());
                String prixTotal = rs.getString("TOTAL_PRIX_PRODUIT");
                long idRecette = 0;
                if (rs.getString("RECETTE_ID") == null){
                    idRecette = 0;
                }else{
                    idRecette = Long.parseLong(rs.getObject("RECETTE_ID").toString());
                }
                
                Produit p = new Produit(id, lib, statut, stock, prixUnit, img);
                ProductOfPanier pop = new ProductOfPanier(p, qteProduit, prixTotal, null, idRecette);
                myList.add(pop);
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
         
         return myList;
    }
    
    public Collection<IngredientOfProduct> getListOfIngredientByIdRecette(long idRecette)
    {
        Collection<IngredientOfProduct> myList = new ArrayList<IngredientOfProduct>();
        Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_ING, LIB_ING, STATUT_ING, STOCK_ING, PRIX_ING, IMG_ING, [TYPE_ID], LIGNE_RECETTE.QUANTITE_ING, LIGNE_RECETTE.TOTAL_PRIX_ING " +
                                                "FROM INGREDIENT INNER JOIN LIGNE_RECETTE ON INGREDIENT.ID_ING = LIGNE_RECETTE.ING_ID " +
                                                "INNER JOIN RECETTE ON LIGNE_RECETTE.RECETTE_ID = RECETTE.ID_RECETTE " +
                                                "INNER JOIN LIGNE_COMMANDE ON RECETTE.ID_RECETTE = LIGNE_COMMANDE.RECETTE_ID " +
                                                 "WHERE LIGNE_COMMANDE.RECETTE_ID = '" + idRecette + "'");
            while(rs.next()){
                long id = Long.parseLong(rs.getObject("ID_ING").toString());
                String lib = rs.getString("LIB_ING");
                String statut = rs.getString("STATUT_ING");
                long stock = Long.parseLong(rs.getObject("STOCK_ING").toString());             
                String prixUnit = rs.getString("PRIX_ING");  
                String img = "";
                if (rs.getString("IMG_ING") == null){
                    img = "";
                }else{
                    img = rs.getString("IMG_ING");
                }
                int typeId = Integer.parseInt(rs.getObject("TYPE_ID").toString());
                int qteIngredient = Integer.parseInt(rs.getObject("QUANTITE_ING").toString());
                String prixTotal = rs.getString("TOTAL_PRIX_ING");
                                                
                Ingredient i = new Ingredient(id, lib, statut, stock, prixUnit, img, typeId);
                IngredientOfProduct iop = new IngredientOfProduct(i, qteIngredient, qteIngredient, prixTotal);
                myList.add(iop);
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
         
         return myList;
    }
    
    public Collection<Commande> getListOfCommandesByIdClient(Client u)
    {
        Collection<Commande> commandeList = new ArrayList();
        Statement stmt = null;
         try {
            DaoStatut daoStatut = new DaoStatut(cnx);
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_COM, DT_CREA_COM, CHGT_STATUT_COM, DT_LIVR_COM, PRIX_TOT_COM, STA_ID FROM COMMANDE WHERE CLI_ID = '" +
                                                u.getId() + "' ORDER BY STA_ID ASC, CHGT_STATUT_COM DESC");
            while(rs.next()){
                long id = Long.parseLong(rs.getObject("ID_COM").toString());
                Timestamp dateCrea = rs.getTimestamp("DT_CREA_COM");
                Timestamp dateChgtStat = rs.getTimestamp("CHGT_STATUT_COM");
                Timestamp dateLivr;
                if (rs.getTimestamp("DT_LIVR_COM") == null)
                {
                    dateLivr = null;
                }else
                {
                    dateLivr = rs.getTimestamp("DT_LIVR_COM");
                }
                String prixTotal = rs.getString("PRIX_TOT_COM");               
                short idStatut = Short.parseShort(rs.getObject("STA_ID").toString());
                              
                Date dateCreation = new Date(dateCrea.getTime()); 
                Date dateChangementStat = new Date(dateChgtStat.getTime());
                Date dateLivraison = null;
                SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
                
                if (dateLivr != null){
                    dateLivraison = new Date(dateLivr.getTime());
                }                          
                                                                
                String strDateCrea = dfm.format(dateCreation);
                String strDateChgtStat = dfm.format(dateChangementStat);
                String strDateLivr = "";
                
                if (dateLivraison != null){
                    strDateLivr = dfm.format(dateLivraison);
                }else{
                    strDateLivr = "";
                }
                
                Commande c = new Commande(id, strDateCrea, strDateChgtStat, strDateLivr, prixTotal, u, daoStatut.findStatutById(idStatut));
                commandeList.add(c);                
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
         
         return commandeList;
    }
    
    public void addCommande(Commande c){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            // Dans la requete SQL on convertit la chaîne de caractère DateChgtStatut en datetime 
            // avec le patern 121 qui correspond au format de datetime yyyy-mm-dd hh:mi:ss.mmm (24h)
            stmt.executeUpdate("INSERT INTO COMMANDE (ID_COM, DT_CREA_COM, CHGT_STATUT_COM, PRIX_TOT_COM, CLI_ID, STA_ID) VALUES('" +
                    c.getIdentifiant() + "', CONVERT(datetime, '" + c.getDateCreation()+ "', 121), CONVERT(datetime, '" + 
                    c.getDateChgtStatut() + "', 121), '" + c.getPrixTotal() + "', '" + c.getClient().getId() +
                    "', '" + c.getStatut().getIdentifiant() + "')");            
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
    
    public void addLigneCommande(Commande c, ProductOfPanier pop){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("INSERT INTO LIGNE_COMMANDE (QUANT_PRODUIT, PRIX_UNIT_PRODUIT, TOTAL_PRIX_PRODUIT, COM_ID, PRO_ID, RECETTE_ID) VALUES('" +
                    pop.getQuantiteArticle() + "', '" + pop.getArticle().getPrix() + "', '" + pop.getTotalPrixArticle() + "', '" + c.getIdentifiant() + "', '" +
                    pop.getArticle().getId() + "', NULL)");            
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
    
    public void addLigneCommandeRecette(Commande c, ProductOfPanier pop){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("INSERT INTO LIGNE_COMMANDE (QUANT_PRODUIT, PRIX_UNIT_PRODUIT, TOTAL_PRIX_PRODUIT, COM_ID, PRO_ID, RECETTE_ID) VALUES('" +
                    pop.getQuantiteArticle() + "', '" + pop.getArticle().getPrix() + "', '" + pop.getTotalPrixArticle() + "', '" + c.getIdentifiant() + "', '" +
                    pop.getArticle().getId() + "', '" + pop.getIdentifiantRecette() + "')");            
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
    
    public void addRecette(ProductOfPanier pop){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("INSERT INTO RECETTE (ID_RECETTE) VALUES('" + pop.getIdentifiantRecette() + "')");            
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
    
    public void addLigneRecette(ProductOfPanier pop, IngredientOfProduct iop){
         Statement stmt = null;
         try {
            stmt = cnx.createStatement();
            stmt.executeUpdate("INSERT INTO LIGNE_RECETTE (QUANTITE_ING, PRIX_UNIT_ING, TOTAL_PRIX_ING, RECETTE_ID, ING_ID) VALUES('" + 
                    iop.getQuantiteIngredient() + "', '" + iop.getIngredient().getPrix() + "', '" + iop.getTotalPrixIngredient() + "', '" +
                    pop.getIdentifiantRecette() + "', '" + iop.getIngredient().getIdentifiant() + "')");            
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
