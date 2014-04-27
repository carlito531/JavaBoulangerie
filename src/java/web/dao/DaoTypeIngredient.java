package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.TypeIngredient;

public class DaoTypeIngredient {
    
    private Connection cnx;

    public DaoTypeIngredient(Connection cnx) {
        this.cnx = cnx;
    }
    
    public TypeIngredient findTypeIngredientByLibelle(String lib){
        TypeIngredient typeIng = null;
        Statement stmt = null;
        
        try {
            stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT [ID_TYPE] FROM [TYPE] WHERE LIB_TYPE = '" + lib + "'");
            if (rs.next()){
                int id = rs.getInt("ID_TYPE");
                
                typeIng = new TypeIngredient(id, lib);
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
        
        return typeIng;       
    }   
}
