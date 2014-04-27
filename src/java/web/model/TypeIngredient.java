package model;

public class TypeIngredient {
    private int identifiant;
    private String libelle;

    public TypeIngredient(int identifiant, String libelle) {
        this.identifiant = identifiant;
        this.libelle = libelle;
    }

    public int getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }   
}
