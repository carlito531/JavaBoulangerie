package commun.model;

public class Ingredient {
    
    private long identifiant;
    private String libelle;
    private String statut;
    private long stock;
    private String prix;
    private String urlImage;
    private int identifiantType;

    public Ingredient(long identifiant, String libelle, String statut, long stock, String prix, String urlImage, int identifiantType) {
        this.identifiant = identifiant;
        this.libelle = libelle;
        this.statut = statut;
        this.stock = stock;
        this.prix = prix;
        this.urlImage = urlImage;
        this.identifiantType = identifiantType;
    }

    public Ingredient(long id, String libelle, String statut, long quantitee, String prix, String image) {
        this.identifiant = id;
        this.libelle = libelle;
        this.statut = statut;
        this.stock = quantitee;
        this.prix = prix;
        this.urlImage = image;
    }

    public long getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(long identifiant) {
        this.identifiant = identifiant;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public long getStock() {
        return this.stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getPrix() {
        return this.prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getUrlImage() {
        return this.urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getIdentifiantType() {
        return this.identifiantType;
    }

    public void setIdentifiantType(int identifiantType) {
        this.identifiantType = identifiantType;
    }  
}
