package mobile.model;

import java.io.Serializable;

/**
 * Obtient ou définit un produit du panier
 * Cette classe permet la manipulation
 * de produits dans le panier
 * @author INF-PORT-CR2
 */
public class ProduitDuPanier implements Serializable{
   
    private String libelleArticle;
    private int quantiteArticle;
    private String prixArticle;
    private String totalPrixArticle;
    private long idProduit;

    public ProduitDuPanier(String libelleArticle, int quantiteArticle, String prixArticle, String totalPrixArticle, long idProduit) {
        this.libelleArticle = libelleArticle;
        this.quantiteArticle = quantiteArticle;
        this.prixArticle = prixArticle;
        this.totalPrixArticle = totalPrixArticle;
        this.idProduit = idProduit;
    }
    
    public String getLibelleArticle() {
        return libelleArticle;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
    }

    public int getQuantiteArticle() {
        return quantiteArticle;
    }

    public void setQuantiteArticle(int quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
    }

    public String getPrixArticle() {
        return prixArticle;
    }

    public void setPrixArticle(String prixArticle) {
        this.prixArticle = prixArticle;
    }

    public String getTotalPrixArticle() {
        return totalPrixArticle;
    }

    public void setTotalPrixArticle(String totalPrixArticle) {
        this.totalPrixArticle = totalPrixArticle;
    }

    public long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(long idProduit) {
        this.idProduit = idProduit;
    }   
}
