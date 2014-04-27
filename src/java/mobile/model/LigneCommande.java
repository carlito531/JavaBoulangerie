/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import commun.model.Produit;

/**
 * Obtient ou définit une ligne de commande en rapport avec une commande
 * @author INF-PORT-CR2
 */
public class LigneCommande {
    
    private long id;
    private int quantite;
    private String prixUnitaire;
    private String prixTotale;
    private long comId;
    private long produitId;
    private Produit produit;

    public LigneCommande(long id, int quantite, String prixUnitaire, String prixTotale, long comId, Produit produit) {
        this.id = id;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotale = prixTotale;
        this.comId = comId;
        this.produit = produit;
    }

    public LigneCommande(int quantite, String prixUnitaire, String prixTotale, long comId, long produitId) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotale = prixTotale;
        this.comId = comId;
        this.produitId = produitId;
    }
    
    public LigneCommande(long id, int quantite, String prixUnitaire, String prixTotale, long comId, long produitId) {
        this.id = id;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotale = prixTotale;
        this.comId = comId;
        this.produitId = produitId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(String prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getPrixTotale() {
        return prixTotale;
    }

    public void setPrixTotale(String prixTotale) {
        this.prixTotale = prixTotale;
    }

    public long getComId() {
        return comId;
    }

    public void setComId(long comId) {
        this.comId = comId;
    }

    public long getProduitId() {
        return produitId;
    }

    public void setProduitId(long produitId) {
        this.produitId = produitId;
    }
    
     public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

}
