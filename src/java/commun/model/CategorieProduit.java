/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun.model;

/**
 * Obtiens ou définit une catégorie de produit
 *
 * @author INF-PORT-CR2
 */
public class CategorieProduit {

    private int id;
    private String libelle;

    public CategorieProduit(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
