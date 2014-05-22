/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import mobile.dao.ProduitDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import commun.model.Produit;
import mobile.model.ProduitDuPanier;

/**
 * Servlet permettant l'ajout de produits au panier
 *
 * @author INF-PORT-CR2
 */
@WebServlet(name = "GestionPanier", urlPatterns = {"/GestionPanier"})
public class GestionPanier extends HttpServlet {

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

        ProduitDao pdao = new ProduitDao();
        HttpSession session = request.getSession();

        try {

            // On récupère le panier en session qui est une collection de produit du panier
            Collection<ProduitDuPanier> panier = (ArrayList) (session.getAttribute("monpanier"));

            // S'il n'y a rien dans le panier
            if (panier == null) {

                try {

                    // On cherche le produit sur lequel on a cliqué via son id
                    long idProduit = Long.parseLong(request.getParameter("id_produit"));
                    Produit p = pdao.trouve(idProduit);

                    // Si le statut du produit est disponible on l'ajoute au panier
                    if (p.getStatut().equals("Disponible")) {

                        ProduitDuPanier pdp = new ProduitDuPanier(p.getLibelle(), 1, p.getPrix(), p.getPrix(), p.getId());

                        Collection<ProduitDuPanier> colp = new ArrayList();
                        colp.add(pdp);

                        panier = new ArrayList(colp);
                        session.setAttribute("monpanier", panier);

                        // Sinon on affiche l'indisponibilitée du produit
                    } else {
                        request.setAttribute("produitIndisponible", "Le produit n'est actuellement pas disponible !");
                    }

                    // On redirige vers la servlet CreerCommande pour relancer l'affichage des
                    // produits par catégories
                    RequestDispatcher rd = request.getRequestDispatcher("CreerCommande");
                    rd.forward(request, response);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // sinon (si le panier n'est pas vide)
            } else {

                try {

                    long idProduit = Long.parseLong(request.getParameter("id_produit"));
                    Produit p = pdao.trouve(idProduit);

                    // Si le statut du produit est disponible
                    if (p.getStatut().equals("Disponible")) {

                        ProduitDuPanier pdp = new ProduitDuPanier(p.getLibelle(), 1, p.getPrix(), p.getPrix(), p.getId());

                        // On fait une copie du panier copiePanier
                        CopyOnWriteArrayList copiePanier = new CopyOnWriteArrayList(panier);
                        Iterator<ProduitDuPanier> it = copiePanier.iterator();
                        // On ajoute le produit dans le panier
                        copiePanier.add(pdp);
                        // On vide le panier
                        panier.clear();

                        // On parcours la copie du panier copiePanier
                        while (it.hasNext()) {
                            ProduitDuPanier prod = it.next();

                            // Si le produit exite déja dans la collection
                            if (pdp.getIdProduit() == prod.getIdProduit()) {

                                // on ajoute 1 à la quantitée de l'article déja présent dans la collection
                                // on multiplie la quantitée par le prix
                                prod.setQuantiteArticle(prod.getQuantiteArticle() + 1);
                                Float prixTotale = ((prod.getQuantiteArticle()) * Float.parseFloat(prod.getPrixArticle().replace(',', '.')));
                                prod.setTotalPrixArticle("" + prixTotale);

                                // On supprime le produit venant d'être ajouté pour ne pas avoir de doublon
                                // puis on sort de la boucle
                                copiePanier.remove(pdp);
                                break;
                            }
                        }
                        // On recopie le contenu du panierCopier dans le panier d'origine en session
                        panier.addAll(copiePanier);

                    } // sinon, on indique l'indisponibilitée du produit
                    else {
                        request.setAttribute("produitIndisponible", "Le produit n'est actuellement pas disponible");
                    }

                    // On redirige vers la servlet CreerCommande pour l'affichage des produits par catégories
                    RequestDispatcher rd = request.getRequestDispatcher("CreerCommande");
                    rd.forward(request, response);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
