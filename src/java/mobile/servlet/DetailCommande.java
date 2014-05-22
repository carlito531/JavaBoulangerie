/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import commun.dao.ClientDao;
import mobile.dao.CommandeDao;
import mobile.dao.LigneCommandeDao;
import mobile.dao.ProduitDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commun.model.Client;
import mobile.model.Commande;
import model.LigneCommande;
import commun.model.Produit;

/**
 * Servlet permettant d'afficher les détails de commandes comprenant la
 * reférence de la commande, les produits ainsi que les données clients. Elle
 * permet aussi de modifier l'état de la commande en fonction de l'utilisateur
 * connecté
 *
 * @author INF-PORT-CR2
 */
@WebServlet(name = "DetailCommande", urlPatterns = {"/DetailCommande"})
public class DetailCommande extends HttpServlet {

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

        CommandeDao cdao = new CommandeDao();
        ClientDao clidao = new ClientDao();
        LigneCommandeDao lcdao = new LigneCommandeDao();
        ProduitDao pdao = new ProduitDao();
        long id = Long.parseLong(request.getParameter("id_commande"));
        String statut = request.getParameter("choixStatut");
        Commande com = null;
        Client c = null;
        Collection<LigneCommande> collc = new ArrayList();
        Produit p = null;

        try {

            // Si on appuie sur le bouton modifStatut on change le statut
            // de la commande visualisée en fonction d'une liste (input type select) 
            // de statut
            if (request.getParameter("modifStatut") != null) {

                cdao.changeStatut(id, statut);

                com = cdao.trouve(id);
                c = clidao.trouveAvecIdCommande(id);
                collc = lcdao.trouveAvecIdCommande(id);

                for (LigneCommande lc : collc) {

                    p = pdao.trouve(lc.getProduitId());
                    lc.setProduit(p);
                }

                request.setAttribute("commande", com);
                request.setAttribute("client", c);
                request.setAttribute("ligneCommande", collc);

                RequestDispatcher rd = request.getRequestDispatcher("GestionCommande");
                rd.forward(request, response);

                // Sinon, on affiche simplement les données
            } else {
                com = cdao.trouve(id);
                c = clidao.trouveAvecIdCommande(id);
                collc = lcdao.trouveAvecIdCommande(id);

                for (LigneCommande lc : collc) {

                    p = pdao.trouve(lc.getProduitId());
                    lc.setProduit(p);
                }

                request.setAttribute("commande", com);
                request.setAttribute("client", c);
                request.setAttribute("ligneCommande", collc);

                RequestDispatcher rd = request.getRequestDispatcher("mobile/detailCommande.jsp");
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
