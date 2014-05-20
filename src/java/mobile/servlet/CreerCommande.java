/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import commun.dao.CategorieProduitDao;
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
import javax.servlet.http.HttpSession;
import commun.model.CategorieProduit;
import commun.model.Produit;
import mobile.model.ProduitDuPanier;

/**
 * Servlet permettant l'affichage des produits par catégories et
 * l'initialisation du panier
 *
 * @author INF-PORT-CR2
 */
@WebServlet(name = "CreerCommande", urlPatterns = {"/CreerCommande"})
public class CreerCommande extends HttpServlet {

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

        // Initialisation des variables
        CategorieProduitDao cpd = new CategorieProduitDao();
        ProduitDao pdao = new ProduitDao();
        HttpSession session = request.getSession();

        // Liste la catégorie des produits
        Collection<CategorieProduit> colcp = cpd.listeSansPerso();
        
        // Liste les produits par catégories
        Collection<Produit> colp = pdao.listeParCategorie(1);

        // Envoie des collections vers la jsp
        request.setAttribute("produits", colp);
        request.setAttribute("categoriesProduit", colcp);

        try {

            // Si la session ne contient rien on passe une collection
            // de produit du panier en variable de session
            if (session == null) {
                Collection<ProduitDuPanier> panier = new ArrayList();
                session.setAttribute("monpanier", panier);

                // Sinon, si on appuie sur le bouton btn_categorie,
                // on affiche les produits présent dans la catégorie selectionnée
            } else if (request.getParameter("btn_categorie") != null) {

                int idCategorie = Integer.parseInt(request.getParameter("id_categorie"));
                colp = pdao.listeParCategorie(idCategorie);

                request.setAttribute("produits", colp);
                colcp = cpd.listeSansPerso();
                request.setAttribute("categoriesProduit", colcp);

            } else {
            }
            // Pour finir, on redirige vers la page creerCommande.jsp
            RequestDispatcher rd = request.getRequestDispatcher("mobile/creerCommande.jsp");
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
