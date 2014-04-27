package web.model;

import commun.model.Client;
import model.Statut;

public class Commande {
    private long identifiant;
    private String dateCreation;
    private String dateChgtStatut;
    private String dateLivraison;
    private String prixTotal;
    private Client client;
    private Statut statut;
    
    public Commande(long idCom, String dateCrea, String dateChgtStat, String dateLiv, String prixTot, Client u, Statut s)
    {
        this.identifiant = idCom;
        this.dateCreation = dateCrea;
        this.dateChgtStatut = dateChgtStat;
        this.dateLivraison = dateLiv;
        this.prixTotal = prixTot;       
        this.client = u;
        this.statut = s;
    }

    public long getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(long identifiant) {
        this.identifiant = identifiant;
    }

    public String getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }    
    
    public String getDateChgtStatut() {
        return this.dateChgtStatut;
    }

    public void setDateChgtStatut(String dateChgtStatut) {
        this.dateChgtStatut = dateChgtStatut;
    }

    public String getDateLivraison() {
        return this.dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getPrixTotal() {
        return this.prixTotal;
    }

    public void setPrixTotal(String prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }    
}
