package mobile.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Classe panier
 * @author INF-PORT-CR2
 */
public class Panier implements Serializable{
   
    private Collection<ProduitDuPanier> produits;
    private String prixTotal;
    private String qteArticle;

    public Panier(Collection<ProduitDuPanier> produits, String prixTotal, String qteArticle) {
        this.produits = produits;
        this.prixTotal = prixTotal;
        this.qteArticle = qteArticle;
    }
    
    public Collection<ProduitDuPanier> getProduits() {
        return produits;
    }

    public void setProduits(Collection<ProduitDuPanier> produits) {
        this.produits = produits;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(String prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getQteArticle() {
        return qteArticle;
    }

    public void setQteArticle(String qteArticle) {
        this.qteArticle = qteArticle;
    }
    
    
}
