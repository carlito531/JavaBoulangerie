package web.servlet;

import commun.dao.BDDHelper;
import web.dao.DaoCommande;
import dao.DaoIngredient;
import web.dao.DaoProduct;
import web.dao.DaoStatut;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.model.Commande;
import web.model.IngredientOfProduct;
import web.model.Panier;
import web.model.ProductOfPanier;
import model.Statut;
import commun.model.Client;

@WebServlet(name = "ValiderCommandeServlet", urlPatterns = {"/validercommande"})
public class ValiderCommandeServlet extends HttpServlet {

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
        // On vérifie que l'utilisateur a bien cliqué sur Valider ma Commande avant d'exécuter les actions suivantes
        if (request.getParameter("valider_com") != null){
            BDDHelper myBddHelper = new BDDHelper();
            Connection connex = null;
            Panier myPanier = null;           
            try {
                connex = myBddHelper.open();

                HttpSession session = request.getSession();

                // Si le panier de l'utilisateur n'est pas vide alors
                if (session.getAttribute("panier") != null){
                    // Si l'utilisateur est bien connecté alors
                    if (session.getAttribute("utilisateur") != null){
                        myPanier = (Panier)session.getAttribute("panier");                  
                        DaoCommande daoCommande = new DaoCommande(connex);
                        DaoStatut daoStatut = new DaoStatut(connex);
                        DaoProduct daoProduct = new DaoProduct(connex);
                        DaoIngredient daoIngredient = new DaoIngredient(connex);
                        
                        // Si un des produits souhaités n'est plus en stock alors on affiche de nouveau la page du panier en informant l'utilisateur
                        if (verifierStock(request, myPanier))
                        {
                            RequestDispatcher rd = request.getRequestDispatcher("consultepanier");
                            rd.forward(request, response);
                        }else{
                            // Sinon on affiche la page confirmant la prise de commande
                            Statut stat = null;
                            stat = daoStatut.findStatutByLibelle("En Attente");
                            Client u = (Client)session.getAttribute("utilisateur");
                            Calendar calendar = Calendar.getInstance();
                            Timestamp timeStamp = new Timestamp(calendar.getTimeInMillis());
                            int delaisLivraison = 60;
                            
                            // On créé une nouvelle commande
                            Commande com = new Commande(daoCommande.getIdCommandeAvailable(), timeStamp.toString(), timeStamp.toString(), "", myPanier.getPrixTotalPanier(), u, stat);
                            
                            // Puis on l'inscrit en base
                            daoCommande.addCommande(com);
                                                        
                            for (ProductOfPanier pop : myPanier.getListArticlesPanier()){                                
                                // long newStockProduct = pop.getArticle().getStock() - pop.getQuantiteArticle();
                                // pop.getArticle().setStock(newStockProduct);                                
                                // daoProduct.updateStockProduit(pop.getArticle());
                                if (pop.getListIngredientOfProduct() != null){
                                    delaisLivraison = delaisLivraison + 30;
                                    // Si la liste des ingrédients n'est pas null pour le produit, on insère également les ingrédients du produit en base
                                    // On inscrit donc une nouvelle recette d'un produit personnalisé
                                    pop.setIdentifiantRecette(daoCommande.getIdRecetteAvailable());
                                    daoCommande.addRecette(pop);
                                    daoCommande.addLigneCommandeRecette(com, pop);                                    
                                    for (IngredientOfProduct iop : pop.getListIngredientOfProduct()){
                                        // long newStockIngredient = iop.getIngredient().getStock() - iop.getQuantiteIngredient();
                                        // iop.getIngredient().setStock(newStockIngredient);
                                        // daoIngredient.updateStockIngredient(iop.getIngredient());
                                        daoCommande.addLigneRecette(pop, iop);
                                    }
                                }else{
                                    daoCommande.addLigneCommande(com, pop);
                                }
                            }
                            myPanier = null;
                            session.setAttribute("panier", myPanier);
                            
                            Date dtNow = new Date(); // Instantiation d'un object date du namespace java.util.Date
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dtNow);
                            cal.add(Calendar.MINUTE, delaisLivraison);
                            dtNow = (Date)(cal.getTime());
                            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                            SimpleDateFormat sdfHours = new SimpleDateFormat("HH:mm", Locale.FRANCE);
                            
                            request.setAttribute("commande_valide", "Votre commande a bien été prise en compte par nos services...<br />" +
                                                "Elle sera livrée le " + sdfDate.format(dtNow) + " autour de " + sdfHours.format(dtNow) + ".");
                            RequestDispatcher rd = request.getRequestDispatcher("panier.jsp");
                            rd.forward(request, response);
                        }
                    }else{
                        // Sinon on redirige l'utilisateur vers la page de connexion
                        RequestDispatcher rd = request.getRequestDispatcher("connexion");
                        rd.forward(request, response);
                    }                                        
                }else{
                    // Sinon on redirige vers  l'accueil
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
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
    
    private boolean verifierStock(HttpServletRequest request, Panier myPanier){
        ArrayList<ProductOfPanier> mylistArticlesPanier = (ArrayList<ProductOfPanier>)myPanier.getListArticlesPanier();
        float prixTotalPanier = 0.F;
        int qteTotal = 0;
        
        try {    
            for (ProductOfPanier pop : mylistArticlesPanier){
                int qteArticle = pop.getQuantiteArticle();
                if (pop.getListIngredientOfProduct() == null){
                    // On vérifie que le produit est disponible dans la quantité demandée
                    if (pop.getArticle().getStock() - qteArticle < 0)
                    {
                        request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + " de " +
                                    pop.getArticle().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                        // Si la nouvelle quantité demandée n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                        mylistArticlesPanier.remove(pop);
                        return true;
                    }
                }else{
                    Collection<IngredientOfProduct> mylistIngredientsProduct = pop.getListIngredientOfProduct();
                    for (IngredientOfProduct iop : mylistIngredientsProduct){
                        int qteIngredient = iop.getQuantiteIngredient();
                        // On vérifie que chaque ingrédient est disponible dans la quantité demandée
                        if ((iop.getIngredient().getStock() - qteIngredient) < 0){
                            request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteIngredient) + " de " +
                                    iop.getIngredient().getLibelle() + "<br />n'est plus disponible en stock !" + "<br />Le produit a été retiré du panier.");
                            // Si la nouvelle quantité demandée pour l'ingrédient n'est plus disponible en stock on supprime le produit du panier et on informe l'utilisateur
                            mylistArticlesPanier.remove(pop);
                            return true;
                        }
                    }  
                    // On recalcule le prix total de l'article personnalisé
                    float prixTotalArticle = 0.F;
                    for (IngredientOfProduct iop : mylistIngredientsProduct){
                        prixTotalArticle = prixTotalArticle + Float.parseFloat(iop.getTotalPrixIngredient().replace(",", "."));
                    }
                    pop.setQuantiteArticle(qteArticle);
                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                    df.applyPattern("#,##0.00");
                    pop.getArticle().setPrix(df.format(prixTotalArticle / qteArticle));
                    pop.setTotalPrixArticle(df.format(prixTotalArticle));
                }             
            }
            if (mylistArticlesPanier.isEmpty()){
                myPanier = null;
                HttpSession sessionPanier = request.getSession();
                sessionPanier.setAttribute("panier", myPanier);
                return true;
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    // </editor-fold>

}
