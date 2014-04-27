package model;

public class Statut {
    private short identifiant;
    private String libelle;

    public Statut(short identifiant, String libelle) {
        this.identifiant = identifiant;
        this.libelle = libelle;
    }   

    public short getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(short identifiant) {
        this.identifiant = identifiant;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }    
}
