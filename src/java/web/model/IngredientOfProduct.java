package web.model;

import commun.model.Ingredient;

public class IngredientOfProduct {
    private Ingredient ingredient;
    private int quantiteIngredient;
    private int quantiteOrigineIngredient;
    private String totalPrixIngredient;

    public IngredientOfProduct(Ingredient ingr, int quantiteIngr, int quantiteOrigineIngr, String totalPrixIngr) {
        this.ingredient = ingr;
        this.quantiteIngredient = quantiteIngr;
        this.quantiteOrigineIngredient = quantiteOrigineIngr;
        this.totalPrixIngredient = totalPrixIngr;
    }    
   
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    } 

    public int getQuantiteIngredient() {
        return this.quantiteIngredient;
    }

    public void setQuantiteIngredient(int quantiteIngredient) {
        this.quantiteIngredient = quantiteIngredient;
    }

    public int getQuantiteOrigineIngredient() {
        return this.quantiteOrigineIngredient;
    }

    public void setQuantiteOrigineIngredient(int quantiteOrigineIngredient) {
        this.quantiteOrigineIngredient = quantiteOrigineIngredient;
    }   
    
    public String getTotalPrixIngredient() {
        return this.totalPrixIngredient;
    }

    public void setTotalPrixIngredient(String totalPrixIngredient) {
        this.totalPrixIngredient = totalPrixIngredient;
    }    
}
