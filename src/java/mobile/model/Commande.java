/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.model;

import commun.model.Client;

/**
 * Obtient ou définit une commande
 *
 * @author INF-PORT-CR2
 */
public class Commande {

    private long id;
    private String chgStatut;
    private String dateLivraison;
    private String prixTotale;
    private Client client;
    private String statut;

    public Commande(String chgStatut, String dateLivraison, String prixTotale, Client client, String statut) {
        this.chgStatut = chgStatut;
        this.dateLivraison = dateLivraison;
        this.prixTotale = prixTotale;
        this.client = client;
        this.statut = statut;
    }

    public Commande(long id, String chgStatut, String dateLivraison, String prixTotale, Client client, String statut) {
        this.id = id;
        this.chgStatut = chgStatut;
        this.dateLivraison = dateLivraison;
        this.prixTotale = prixTotale;
        this.client = client;
        this.statut = statut;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChgStatut() {
        return chgStatut;
    }

    public void setChgStatut(String chgStatut) {
        this.chgStatut = chgStatut;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getPrixTotale() {
        return prixTotale;
    }

    public void setPrixTotale(String prixTotale) {
        this.prixTotale = prixTotale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatut() {
        return statut;
    }

    public void setIdStatut(String statut) {
        this.statut = statut;
    }

}
