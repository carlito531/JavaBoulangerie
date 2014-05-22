/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import commun.dao.ClientDao;
import commun.model.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mobile.dao.CommandeDao;
import mobile.dao.LigneCommandeDao;
import mobile.dao.ProduitDao;
import mobile.model.Commande;
import mobile.model.ProduitDuPanier;
import model.LigneCommande;

/**
 *
 * @author INF-PORT-CR2
 */
public class RecapCommande extends HttpServlet {

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

        try {

            // Permet d'établir la liste des clients pour les choisirs
            ClientDao cdao = new ClientDao();
            request.setAttribute("clients", cdao.liste());

            // On récupère le panier en session qui est une collection de produit du panier
            HttpSession session = request.getSession();
            Collection<ProduitDuPanier> panier = (ArrayList) (session.getAttribute("monpanier"));

            // si panier n'est pas vide
            if (panier != null) {

                // si clique sur le bouton d'ajout
                if (request.getParameter("btn_ajout") != null) {

                    int id = Integer.parseInt(request.getParameter("id_produit"));

                    for (ProduitDuPanier prod : panier) {
                        if (prod.getIdProduit() == id) {
                            prod.setQuantiteArticle(prod.getQuantiteArticle() + 1);
                            Float prixTotale = ((prod.getQuantiteArticle()) * Float.parseFloat(prod.getPrixArticle().replace(',', '.')));
                            prod.setTotalPrixArticle("" + prixTotale);
                        }
                    }
                    response.sendRedirect("RecapCommande");
                }

                // Si clique sur bouton de suppression
                if (request.getParameter("btn_suppr") != null) {

                    int id = Integer.parseInt(request.getParameter("id_produit"));

                    for (ProduitDuPanier prod : panier) {
                        if (prod.getIdProduit() == id) {
                            prod.setQuantiteArticle(prod.getQuantiteArticle() - 1);
                            Float prixTotale = ((prod.getQuantiteArticle()) * Float.parseFloat(prod.getPrixArticle().replace(',', '.')));
                            prod.setTotalPrixArticle("" + prixTotale);
                        }
                    }
                    response.sendRedirect("RecapCommande");
                }

                // Si l'utilisateur clique sur le bouton de validation
                if (request.getParameter("btn_valider") != null) {
                    // Récupération de l'utilisateur sélectionné
                    Long id = Long.parseLong(request.getParameter("client"));

                    try {
                        Client client = cdao.trouveAvecId(id);

                        // Création de la commande
                        CommandeDao cmddao = new CommandeDao();
                        ProduitDao pdao = new ProduitDao();
                        long idCommande = cmddao.retourneDernierId() + 1;
                        Commande c = null;

                        // Pour chaque produit du panier on addititone la quantitée
                        // ainsi que le prix pour avoir leur totaux
                        int quantitee = 0;
                        float prixTotal = 0F;

                        for (ProduitDuPanier prod : panier) {
                            quantitee = quantitee + prod.getQuantiteArticle();
                            prixTotal = prixTotal + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                        }

                        // Construction de la commande
                        c = new Commande(idCommande, "", null, "" + prixTotal, client, "1");

                        // Insert la nouvelle commande dans la bdd
                        cmddao.ajoute(c);

                        // Mise à jour du stock
                        for (ProduitDuPanier prod : panier) {
                            pdao.supprimeQuantitee(prod.getIdProduit(), prod.getQuantiteArticle());
                        }

                        // Une nouvelle fois on parcours le panier et on ajoute une ligne de commande
                        // pour chaque produit du panier avec la quantitée et le prix correspondant
                        Iterator<ProduitDuPanier> iter = panier.iterator();
                        LigneCommandeDao lcd = new LigneCommandeDao();

                        while (iter.hasNext()) {
                            ProduitDuPanier prod = iter.next();

                            LigneCommande lc = new LigneCommande(prod.getQuantiteArticle(), prod.getPrixArticle(), prod.getTotalPrixArticle(), c.getId(), prod.getIdProduit());
                            lcd.ajouteLigneACommande(lc);
                        }

                        RequestDispatcher rd = request.getRequestDispatcher("CreerCommande");
                        rd.forward(request, response);

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }

                // refresh
                RequestDispatcher rd = request.getRequestDispatcher("mobile/recapCommande.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
