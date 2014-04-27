package web.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Statut;

public class DaoStatut {
    private Connection cnx;

    public DaoStatut(Connection cnx) {
        this.cnx = cnx;
    }
    
    public Statut findStatutById(short id){
        Statut s = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LIB_STA FROM [STATUT] WHERE ID_STA = '" + id + "'");
            if (rs.next()){
                String lib = rs.getString("LIB_STA");
                s = new Statut(id, lib);
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
        
        return s;       
    }
    
    public Statut findStatutByLibelle(String lib){
        Statut s = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_STA FROM [STATUT] WHERE LIB_STA = '" + lib + "'");
            if (rs.next()){
                short id = Short.parseShort(rs.getObject("ID_STA").toString());
                s = new Statut(id, lib);
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
        
        return s;       
    }
}
