package web.model;

import java.util.Collection;

public class Panier {
    private Collection<ProductOfPanier> listArticlesPanier;
    private String prixTotalPanier;
    private int qteArticlesPanier;
    
    public Panier(Collection<ProductOfPanier> myList, String prixTotal, int quantiteArticles)
    {
        this.listArticlesPanier = myList;
        this.prixTotalPanier = prixTotal;
        this.qteArticlesPanier = quantiteArticles;
    }

    public Collection<ProductOfPanier> getListArticlesPanier() {
        return this.listArticlesPanier;
    }

    public void setListArticlesPanier(Collection<ProductOfPanier> listArticlesPanier) {
        this.listArticlesPanier = listArticlesPanier;
    }

    public String getPrixTotalPanier() {
        return this.prixTotalPanier;
    }

    public void setPrixTotalPanier(String prixTotalPanier) {
        this.prixTotalPanier = prixTotalPanier;
    }

    public int getQteArticlesPanier() {
        return this.qteArticlesPanier;
    }

    public void setQteArticlesPanier(int qteArticlesPanier) {
        this.qteArticlesPanier = qteArticlesPanier;
    }   
}
