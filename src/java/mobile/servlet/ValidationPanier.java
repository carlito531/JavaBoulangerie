/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import mobile.dao.CommandeDao;
import mobile.dao.LigneCommandeDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import commun.model.Client;
import mobile.model.Commande;
import model.LigneCommande;
import mobile.model.ProduitDuPanier;

/**
 * Serlvet permettant d'insérer une nouvelle commande
 * avec les lignes de commandes qui correspondent
 * dans la bdd
 * @author INF-PORT-CR2
 */
@WebServlet(name = "ValidationPanier", urlPatterns = {"/ValidationPanier"})
public class ValidationPanier extends HttpServlet {

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

        HttpSession session = request.getSession();
        Collection<ProduitDuPanier> panier = (ArrayList) (session.getAttribute("monpanier"));
        Client cli = (Client) session.getAttribute("utilisateur");   
        LigneCommandeDao lcd = new LigneCommandeDao();
        CommandeDao cdao = new CommandeDao();
        long idCommande = cdao.retourneDernierId() + 1;
        Commande c = null;
        Iterator<ProduitDuPanier> iter = panier.iterator();
       
        int quantitee = 0;
        float prixTotal = 0F;

        try {
            // Pour chaque produit du panier on addititone la quantitée
            // ainsi que le prix pour avoir leur totaux
            for (ProduitDuPanier prod : panier){
                quantitee = quantitee + prod.getQuantiteArticle();
                prixTotal = prixTotal + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
            }
            
            // Ajoute la quantitée et le prix totale dans la commande
            c = new Commande(idCommande, "", "", "" + prixTotal, cli, "1");
            
            // Insert la nouvelle commande dans la bdd
            cdao.ajoute(c);
            
            // Une nouvelle fois on parcours le panier et on ajoute une ligne de commande
            // pour chaque produit du panier avec la quantitée et le prix correspondant
            while (iter.hasNext()) {
                ProduitDuPanier prod = iter.next();

                LigneCommande lc = new LigneCommande(prod.getQuantiteArticle(), prod.getPrixArticle(), prod.getTotalPrixArticle(), c.getId(), prod.getIdProduit());
                lcd.ajouteLigneACommande(lc);
            }    
            
                // Une fois la commande réalisé on supprime la session panier
                // et on redirige vers la servlet CreerCommande pour pouvoir
                // éventuellement refaire une commande
                session.removeAttribute("monpanier");
                RequestDispatcher rd = request.getRequestDispatcher("CreerCommande");
                rd.forward(request, response);

        } catch (IOException e) {
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
