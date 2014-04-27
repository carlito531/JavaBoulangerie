package web.servlet;

import commun.dao.BDDHelper;
import web.dao.DaoProduct;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.text.DecimalFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.model.Panier;
import commun.model.Produit;
import web.model.ProductOfPanier;

@WebServlet(name = "AjoutPanierServlet", urlPatterns = {"/ajoutpanier"})
public class AjoutPanierServlet extends HttpServlet {

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
                
        BDDHelper myBddHelper = new BDDHelper();
        Connection connex = null;
        
        // On vérifie que l'utilisateur a bien cliqué sur Ajouter au panier avant d'exécuter les actions suivantes
        if (request.getParameter("ajout_produit") != null){
            try {
                connex = myBddHelper.open();
                DaoProduct daoProduct = new DaoProduct(connex);
                ProductOfPanier pop = null;
                long idProduit = Long.parseLong(request.getParameter("id_produit"));
                Collection<ProductOfPanier> mylistArticlesPanier = new ArrayList<ProductOfPanier>();
                Panier myPanier = null;                        
                // Permet de récupérer l'url de la page qui a appelé la servlet AjoutPanierServlet
                String pageAppelante = request.getParameter("page_name");

                Produit p = daoProduct.findProductById(idProduit);

                HttpSession sessionPanier = request.getSession();

                if (sessionPanier.getAttribute("panier") != null){
                    myPanier = (Panier)sessionPanier.getAttribute("panier");
                    int qteTotal = 0;
                    float prixTotalPanier = 0.F;
                    int qteArticle = 0;
                    try {
                         // On vérifie si le client a saisi une quantité numérique
                         qteArticle = Integer.parseInt(request.getParameter("quantite_produit"));                    
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_qte", "Veuillez saisir une quantité valide !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }
                    // On vérifie que le client ne rentre pas une quantité inférieure ou égal à 0
                    if (qteArticle <= 0)
                    {
                        request.setAttribute("error_qte", "Veuillez saisir une quantité supérieure à 0 !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }
                    // On vérifie à ce que le produit soit disponible dans la quantité demandée
                    if ((p.getStock() - qteArticle) < 0)
                    {
                        request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + "<br />n'est pas disponible en stock !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }

                    float prixTotalArticle = qteArticle * Float.parseFloat(p.getPrix().replace(",", "."));
                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();

                    df.applyPattern("#,##0.00");

                    mylistArticlesPanier = myPanier.getListArticlesPanier();
                    // On récupère le contenu du panier actuel
                    boolean everExist = false;

                    for (ProductOfPanier prod : mylistArticlesPanier){
                        if (p.getLibelle().equals(prod.getArticle().getLibelle()))
                        {
                            float prixProduit = Float.parseFloat(prod.getArticle().getPrix().replace(",", "."));
                            int quantite = prod.getQuantiteArticle() + qteArticle;
                            if ((p.getStock() - quantite) >= 0)
                            {
                                // Le produit est disponible dans la nouvelle quantité demandée
                                prod.setQuantiteArticle(quantite);
                                prod.setTotalPrixArticle(df.format(quantite * prixProduit));
                            }else
                            {
                                request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + "<br />n'est pas disponible en stock !");
                                request.setAttribute("ln_error_qte", idProduit);
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return;
                            }                       
                            everExist = true;
                            break;
                        }
                    }
                    if (!everExist)
                    {
                        pop = new ProductOfPanier(p, qteArticle, df.format(prixTotalArticle), null, 0);
                        mylistArticlesPanier.add(pop);
                    }

                    for (ProductOfPanier prod : mylistArticlesPanier){
                        prixTotalPanier = prixTotalPanier + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                        qteTotal = qteTotal + prod.getQuantiteArticle();
                    }

                    myPanier.setListArticlesPanier(mylistArticlesPanier);
                    myPanier.setPrixTotalPanier(df.format(prixTotalPanier));
                    myPanier.setQteArticlesPanier(qteTotal);

                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);                               
                }else{
                    int qteTotal = 0;
                    float prixTotalPanier = 0.F;
                    int qteArticle = 0;
                    try {
                         // On vérifie si le client a saisi une quantité numérique
                         qteArticle = Integer.parseInt(request.getParameter("quantite_produit"));                    
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_qte", "Veuillez saisir une quantité valide !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }
                    // On vérifie que le client ne rentre pas une quantité inférieure ou égal à 0
                    if (qteArticle <= 0)
                    {
                        request.setAttribute("error_qte", "Veuillez saisir une quantité supérieure à 0 !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }
                    // On vérifie à ce que le produit soit disponible dans la quantité demandée
                    if ((p.getStock() - qteArticle) < 0)
                    {
                        request.setAttribute("error_qte", "La quantité demandée : " + Integer.toString(qteArticle) + "<br />n'est pas disponible en stock !");
                        request.setAttribute("ln_error_qte", idProduit);
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return;
                    }                

                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();                
                    df.applyPattern("#,##0.00");                
                    float prixTotalArticle = qteArticle * Float.parseFloat(p.getPrix().replace(",", "."));
                    pop = new ProductOfPanier(p, qteArticle, df.format(prixTotalArticle), null, 0);
                    mylistArticlesPanier.add(pop);

                    prixTotalPanier = prixTotalPanier + prixTotalArticle;
                    qteTotal = qteTotal + qteArticle;    

                    myPanier = new Panier(mylistArticlesPanier, df.format(prixTotalPanier),qteTotal);
                    sessionPanier.setAttribute("panier", myPanier);

                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                }

            } catch (Exception ex) {
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
    }// </editor-fold>

}
