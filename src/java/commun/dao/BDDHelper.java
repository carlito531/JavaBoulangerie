/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe permettant la connexion � la base de donn�es
 *
 * @author INF-PORT-CR2
 */
public class BDDHelper {

    /**
     * Constructeur: Test d'acc�s au driver de connexion
     */
    public BDDHelper() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Impossible de trouver le driver");
            System.exit(-1);
        }
    }

    /**
     * Retourne la connexion � la base
     *
     * @return
     */
    public Connection open() {

        Connection cnx = null;
        try {

            String url = "jdbc:jtds:sqlserver://192.168.20.1:1433/BDD_BOULANGERIE";
            cnx = DriverManager.getConnection(url, "sa", "azerty/123");

        } catch (SQLException ex) {
            System.err.println("Impossible d'établir la connexion" + ex.getMessage());
            return cnx;
        }

        System.out.println("Connexion établie !");
        return cnx;
    }

    /**
     * Ferme la connexion � la base
     *
     * @param c
     */
    public void close(Connection c) {

        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
