/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile.servlet;

import commun.dao.ClientDao;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commun.model.Client;

/**
 * Servlet permettant l'affichage des clients, il est aussi possible d'en
 * ajouter
 *
 * @author INF-PORT-CR2
 */
@WebServlet(name = "GestionClient", urlPatterns = {"/GestionClient"})
public class GestionClient extends HttpServlet {

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

        ClientDao cdao = new ClientDao();
        Client c = null;
        Collection<Client> colc = cdao.liste();

        try {
            // Si on appuie sur le bouton ajouter on récupérère tous les 
            // paramètres de la jsp pour créer un nouveau client
            if (request.getParameter("btn_ajouter") != null) {

                String login = request.getParameter("login");
                String mdp = request.getParameter("mdp");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String adresse = request.getParameter("adresse");
                String ville = request.getParameter("ville");
                int cp = Integer.parseInt(request.getParameter("cp"));
                String mail = request.getParameter("mail");
                String statut = request.getParameter("statut");

                Boolean estCuisi = false;
                Boolean estBoul = false;

                if (statut.equalsIgnoreCase("estCuisinier")) {
                    estCuisi = true;
                } else if (statut.equalsIgnoreCase("estBoul")) {
                    estBoul = true;
                } else {
                }

                c = new Client(login, mdp, nom, prenom, adresse, cp, ville, mail, estCuisi, estBoul);
                cdao.ajoute(c);

                // Sinon si on appuie sur le bouton supprimer
                // on supprime l'utilisateur via son ID
            } else if (request.getParameter("btn_supprimer") != null) {

                long id = Long.parseLong(request.getParameter("id_client"));
                cdao.supprime(id);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            
            request.setAttribute("clients", colc);

            // Redirige vers la page commandes.jsp
            RequestDispatcher rd = request.getRequestDispatcher("mobile/clients.jsp");
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
