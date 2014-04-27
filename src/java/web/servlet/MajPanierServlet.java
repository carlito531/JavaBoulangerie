package web.servlet;

import commun.dao.BDDHelper;
import web.dao.DaoProduct;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.model.IngredientOfProduct;
import web.model.Panier;
import commun.model.Produit;
import web.model.ProductOfPanier;

@WebServlet(name = "MajPanierServlet", urlPatterns = {"/majpanier"})
public class MajPanierServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        // On vérifie que l'utilisateur a bien cliqué sur Ajouter au panier avant d'exécuter les actions suivantes
        if ((request.getParameter("reduire_qte") != null) || (request.getParameter("augmenter_qte") != null) || (request.getParameter("supp_prod") != null)){
            // Permet de récupérer l'url de la page qui a appelé la servlet ValiderPanierServlet
            String pageAppelante = request.getParameter("page_name");
            BDDHelper myBddHelper = new BDDHelper();
            Connection connex = null;
            Panier myPanier = null;           
            try {
                connex = myBddHelper.open();

                HttpSession sessionPanier = request.getSession();

                // Si le panier de l'utilisateur n'est pas vide alors
                if (sessionPanier.getAttribute("panier") != null){
                    myPanier = (Panier)sessionPanier.getAttribute("panier");                  
                    DaoProduct daoProduct = new DaoProduct(connex);

                    if (request.getParameter("reduire_qte") != null){
                        // Si l'utilisateur a cliqué sur moins alors on réduit la quantité de 1                            
                        long idProduit = Long.parseLong(request.getParameter("id_produit"));  
                        Produit p = daoProduct.findProductById(idProduit);
                        
                        reduireQuantiteProduit(request, response, p, myPanier);
                    }else if (request.getParameter("augmenter_qte") != null){
                        // Si l'utilisateur a cliqué sur plus alors on augmente la quantité de 1
                        long idProduit = Long.parseLong(request.getParameter("id_produit"));  
                        Produit p = daoProduct.findProductById(idProduit);
                        
                        augmenterQuantiteProduit(request, response, p, myPanier);
                    }else if (request.getParameter("supp_prod") != null){
                        // Si l'utilisateur a cliqué sur supprimer alors on supprime le produit du panier
                        long idProduit = Long.parseLong(request.getParameter("id_produit"));  
                        Produit p = daoProduct.findProductById(idProduit);
                        
                        supprimerProduit(request, response, p, myPanier);
                    }
                }else{
                    // Sinon on redirige vers la page appelante
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }finally{
                myBddHelper.close(connex);
            }
        }else{            
            // Sinon on redirige vers l'accueil
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private void reduireQuantiteProduit(HttpServletRequest request, HttpServletResponse response, Produit p, Panier myPanier){
        ArrayList<ProductOfPanier> mylistArticlesPanier = (ArrayList<ProductOfPanier>)myPanier.getListArticlesPanier();
        float prixTotalPanier = 0.F;
        int qteTotal = 0;
        boolean exitFor = false;
        try {    
            for (ProductOfPanier pop : mylistArticlesPanier){
                // On vérifie que le produit dont on veut réduire la quantité est le même que celui que l'on parcourt
                if (mylistArticlesPanier.indexOf(pop) == Integer.parseInt(request.getParameter("index_list_product"))){
                    int qteArticle = pop.getQuantiteArticle();
                    // On diminue la quantité de 1
                    qteArticle = qteArticle - 1;
                    // On vérifie que le résultat est supérieur à 0
                    if (qteArticle <= 0)
                    {
                        // Si le résultat est inférieur ou égal à 0 on retire l'article du panier
                        mylistArticlesPanier.remove(pop);
                        break;
                    }else{
                        if (pop.getListIngredientOfProduct() == null){
                            // On vérifie que le produit est disponible dans la nouvelle quantité demandée
                            if ((pop.getArticle().getStock() - qteArticle) < 0)
                            {
                                request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + " de " +
                                        pop.getArticle().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                                // Si la nouvelle quantité demandée n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                                mylistArticlesPanier.remove(pop);
                                break;
                            }else{
                                pop.setQuantiteArticle(qteArticle);
                                DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                                df.applyPattern("#,##0.00");
                                pop.setTotalPrixArticle(df.format(qteArticle * Float.parseFloat(pop.getArticle().getPrix().replace(",", ".")))); 
                            }
                        }else{
                            Collection<IngredientOfProduct> mylistIngredientsProduct = pop.getListIngredientOfProduct();
                            for (IngredientOfProduct iop : mylistIngredientsProduct){
                                int qteIngredient = iop.getQuantiteIngredient();
                                // On diminue les quantités en fonction de la quantité d'origine désirée par l'utilisateur
                                qteIngredient = qteIngredient - iop.getQuantiteOrigineIngredient();
                                // On vérifie que chaque ingrédient est disponible dans la nouvelle quantité demandée
                                if ((iop.getIngredient().getStock() - qteIngredient) < 0){
                                    request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteIngredient) + " de " +
                                        iop.getIngredient().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                                    // Si la nouvelle quantité demandée pour l'ingrédient n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                                    mylistArticlesPanier.remove(pop);
                                    exitFor = true;
                                    break;
                                }else{
                                    iop.setQuantiteIngredient(qteIngredient);
                                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                                    df.applyPattern("#,##0.00");
                                    iop.setTotalPrixIngredient(df.format(qteIngredient * Float.parseFloat(iop.getIngredient().getPrix().replace(",", "."))));
                                }
                            }
                            if (exitFor){
                                break;
                            }
                            // On recalcule le prix total de l'article personnalisé
                            float prixTotalArticle = 0.F;
                            
                            mylistIngredientsProduct = pop.getListIngredientOfProduct();
                            
                            if (mylistIngredientsProduct != null){
                                for (IngredientOfProduct iop : mylistIngredientsProduct){
                                    prixTotalArticle = prixTotalArticle + Float.parseFloat(iop.getTotalPrixIngredient().replace(",", "."));
                                }       
                            }
                            pop.setQuantiteArticle(qteArticle);
                            DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                            df.applyPattern("#,##0.00");
                            if (qteArticle <= 0){
                                pop.getArticle().setPrix(df.format(0));
                            }else{
                                pop.getArticle().setPrix(df.format(prixTotalArticle / qteArticle));
                            }
                            pop.setTotalPrixArticle(df.format(prixTotalArticle)); 
                        }
                    }
                }
            }            
            if (mylistArticlesPanier.isEmpty()){
                myPanier = null;
                HttpSession sessionPanier = request.getSession();
                sessionPanier.setAttribute("panier", myPanier);
            }else{
                // On recalcule le panier
                for (ProductOfPanier prod : mylistArticlesPanier){
                    prixTotalPanier = prixTotalPanier + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                    qteTotal = qteTotal + prod.getQuantiteArticle();
                }
                DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                df.applyPattern("#,##0.00");
                myPanier.setListArticlesPanier(mylistArticlesPanier);
                myPanier.setPrixTotalPanier(df.format(prixTotalPanier));
                myPanier.setQteArticlesPanier(qteTotal);
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("consultepanier");
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void augmenterQuantiteProduit(HttpServletRequest request, HttpServletResponse response, Produit p, Panier myPanier){
        ArrayList<ProductOfPanier> mylistArticlesPanier = (ArrayList<ProductOfPanier>)myPanier.getListArticlesPanier();
        float prixTotalPanier = 0.F;
        int qteTotal = 0;
        boolean exitFor = false;
        try {    
            for (ProductOfPanier pop : mylistArticlesPanier){
                // On vérifie que le produit dont on veut augmenter la quantité est le même que celui que l'on parcourt
                if (mylistArticlesPanier.indexOf(pop) == Integer.parseInt(request.getParameter("index_list_product"))){
                    int qteArticle = pop.getQuantiteArticle();
                    // On augmente la quantité de 1
                    qteArticle = qteArticle + 1;
                    if (pop.getListIngredientOfProduct() == null){
                        // On vérifie que le produit est disponible dans la nouvelle quantité demandée
                        if ((pop.getArticle().getStock() - qteArticle) < 0)
                        {
                            request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + " de " +
                                        pop.getArticle().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                            // Si la nouvelle quantité demandée n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                            mylistArticlesPanier.remove(pop);
                            break;
                        }else{  
                            pop.setQuantiteArticle(qteArticle);
                            DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                            df.applyPattern("#,##0.00");
                            pop.setTotalPrixArticle(df.format(qteArticle * Float.parseFloat(pop.getArticle().getPrix().replace(",", "."))));
                        }
                    }else{
                        Collection<IngredientOfProduct> mylistIngredientsProduct = pop.getListIngredientOfProduct();
                        for (IngredientOfProduct iop : mylistIngredientsProduct){
                            int qteIngredient = iop.getQuantiteIngredient();
                            // On augmente les quantités en fonction de la quantité d'origine désirée par l'utilisateur
                            qteIngredient = qteIngredient + iop.getQuantiteOrigineIngredient();
                            // On vérifie que chaque ingrédient est disponible dans la nouvelle quantité demandée
                            if ((iop.getIngredient().getStock() - qteIngredient) < 0){
                                request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteIngredient) + " de " +
                                        iop.getIngredient().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                                // Si la nouvelle quantité demandée pour l'ingrédient n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                                mylistArticlesPanier.remove(pop);
                                exitFor = true;
                                break;
                            }else{
                                iop.setQuantiteIngredient(qteIngredient);
                                DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                                df.applyPattern("#,##0.00");
                                iop.setTotalPrixIngredient(df.format(qteIngredient * Float.parseFloat(iop.getIngredient().getPrix().replace(",", "."))));
                            }
                        }
                        if (exitFor){
                            break;
                        }
                        // On recalcule le prix total de l'article personnalisé
                        float prixTotalArticle = 0.F;
                        
                        mylistIngredientsProduct = pop.getListIngredientOfProduct();
                            
                        if (mylistIngredientsProduct != null){
                            for (IngredientOfProduct iop : mylistIngredientsProduct){
                                prixTotalArticle = prixTotalArticle + Float.parseFloat(iop.getTotalPrixIngredient().replace(",", "."));
                            }       
                        }
                        pop.setQuantiteArticle(qteArticle);
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        if (qteArticle <= 0){
                            pop.getArticle().setPrix(df.format(0));
                        }else{
                            pop.getArticle().setPrix(df.format(prixTotalArticle / qteArticle));
                        }
                        pop.setTotalPrixArticle(df.format(prixTotalArticle));
                    }
                }                
            }
            if (mylistArticlesPanier.isEmpty()){
                myPanier = null;
                HttpSession sessionPanier = request.getSession();
                sessionPanier.setAttribute("panier", myPanier);
            }else{
                // On recalcule le panier
                for (ProductOfPanier prod : mylistArticlesPanier){
                    prixTotalPanier = prixTotalPanier + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                    qteTotal = qteTotal + prod.getQuantiteArticle();
                }
                DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                df.applyPattern("#,##0.00");
                myPanier.setListArticlesPanier(mylistArticlesPanier);
                myPanier.setPrixTotalPanier(df.format(prixTotalPanier));
                myPanier.setQteArticlesPanier(qteTotal);
            }
                        
            RequestDispatcher rd = request.getRequestDispatcher("consultepanier");
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void supprimerProduit(HttpServletRequest request, HttpServletResponse response, Produit p, Panier myPanier){
        ArrayList<ProductOfPanier> mylistArticlesPanier = (ArrayList<ProductOfPanier>)myPanier.getListArticlesPanier();
        float prixTotalPanier = 0.F;
        int qteTotal = 0;
        try {    
            for (ProductOfPanier pop : mylistArticlesPanier){
                // On vérifie que le produit que l'on veut supprimer est le même que celui que l'on parcourt
                if (mylistArticlesPanier.indexOf(pop) == Integer.parseInt(request.getParameter("index_list_product"))){
                    mylistArticlesPanier.remove(pop);
                    break;
                }
            }            
            if (mylistArticlesPanier.isEmpty()){
                myPanier = null;
                HttpSession sessionPanier = request.getSession();
                sessionPanier.setAttribute("panier", myPanier);
            }else{
                // On recalcule le panier
                for (ProductOfPanier prod : mylistArticlesPanier){
                    prixTotalPanier = prixTotalPanier + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                    qteTotal = qteTotal + prod.getQuantiteArticle();
                }  
                DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                df.applyPattern("#,##0.00");
                myPanier.setListArticlesPanier(mylistArticlesPanier);
                myPanier.setPrixTotalPanier(df.format(prixTotalPanier));
                myPanier.setQteArticlesPanier(qteTotal);
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("consultepanier");
            rd.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
}
