package web.model;

import commun.model.Produit;
import java.util.Collection;

public class ProductOfPanier {
   
    private Produit article;
    private int quantiteArticle;
    private String totalPrixArticle;
    private Collection<IngredientOfProduct> listIngredientOfProduct;
    private long identifiantRecette;
    
    public ProductOfPanier(Produit p, int qte, String totPrixArt, Collection<IngredientOfProduct> listIngr, long idRecette)
    {
        this.article = p;
        this.quantiteArticle = qte;
        this.totalPrixArticle = totPrixArt;
        this.listIngredientOfProduct = listIngr;
        this.identifiantRecette = idRecette;
    }

    public Produit getArticle() {
        return this.article;
    }

    public void setArticle(Produit article) {
        this.article = article;
    }    

    public int getQuantiteArticle() {
        return this.quantiteArticle;
    }

    public void setQuantiteArticle(int quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
    }

    public String getTotalPrixArticle() {
        return this.totalPrixArticle;
    }

    public void setTotalPrixArticle(String totalPrixArticle) {
        this.totalPrixArticle = totalPrixArticle;
    }   

    public Collection<IngredientOfProduct> getListIngredientOfProduct() {
        return this.listIngredientOfProduct;
    }

    public void setListIngredientOfProduct(Collection<IngredientOfProduct> listIngredientOfProduct) {
        this.listIngredientOfProduct = listIngredientOfProduct;
    }   

    public long getIdentifiantRecette() {
        return this.identifiantRecette;
    }

    public void setIdentifiantRecette(long identifiantRecette) {
        this.identifiantRecette = identifiantRecette;
    }    
}
