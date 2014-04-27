/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commun.model;

import java.io.Serializable;

/**
 * Obtient ou définit un produit
 * @author INF-PORT-CR2
 */
public class Produit implements Serializable{
 
    private long id;
    private String libelle;
    private String statut;
    private long stock;
    private String prix;
    private String image;
    private int catId;

    public Produit(long id, String libelle, String statut, long stock, String prix, String image, int catId) {
        this.id = id;
        this.libelle = libelle;
        this.statut = statut;
        this.stock = stock;
        this.prix = prix;
        this.image = image;
        this.catId = catId;
    }

    public Produit(long id, String lib, String statut, long stock, String prixUnit, String img) {
       this.id = id;
       this.libelle = lib;
       this.statut = statut;
       this.stock = stock;
       this.prix = prixUnit;
       this.image = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }  
}
