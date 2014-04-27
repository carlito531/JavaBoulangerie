/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mobile.servlet;

import mobile.dao.IngredientDao;
import mobile.dao.ProduitDao;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commun.model.Ingredient;
import commun.model.Produit;

/**
 * Servlet permettant de manipuler le stock des produits et des ingrédients
 * @author INF-PORT-CR2
 */
@WebServlet(name = "GestionStock", urlPatterns = {"/GestionStock"})
public class GestionStock extends HttpServlet {

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
        
        
        IngredientDao idao = new IngredientDao();
        ProduitDao pdao = new ProduitDao();
        
        try {
           
            // Si on appuie sur le bouton d'ajout d'ingredient
            // on récupère la quantitée entrée et on l'ajoute au stock
            if (request.getParameter("btn_ajoutIngredient") != null){
                
                int quantitee = Integer.parseInt(request.getParameter("txt_stockIngredient"));
                Long idIngredient = Long.parseLong(request.getParameter("id_ingredient"));
                
                idao.ajouteQuantitee(idIngredient, quantitee);  
            }
            
            // Sinon si on appuie sur le bouton de suppression d'ingrédient
            // on récupère la quantitée et on la supprime du stock
            else if (request.getParameter("btn_supprimerIngredient") != null){
                
                int quantitee = Integer.parseInt(request.getParameter("txt_stockIngredient"));
                Long idIngredient = Long.parseLong(request.getParameter("id_ingredient"));
                
                idao.supprimeQuantitee(idIngredient, quantitee);    
            }
            
            // Si on appuie sur le bouton d'ajout produit
            // on récupère la quantitée entrée et on l'ajoute au stock
            else if (request.getParameter("btn_ajoutProduit") != null){
                
                int quantitee = Integer.parseInt(request.getParameter("txt_stockProduit"));
                Long idProduit = Long.parseLong(request.getParameter("id_produit"));
                
                pdao.ajouteQuantitee(idProduit, quantitee);  
            }
            
            // Sinon si on appuie sur le bouton de suppression de produit
            // on récupère la quantitée et on la supprime du stock
            else if (request.getParameter("btn_supprimerProduit") != null){
                
                int quantitee = Integer.parseInt(request.getParameter("txt_stockProduit"));
                Long idProduit = Long.parseLong(request.getParameter("id_produit"));
                
                pdao.supprimeQuantitee(idProduit, quantitee);   
            }
            
            else{   
            }  
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }finally{
      
        // On liste les ingredients et les produits d'office
        // et on renvoie vers la page de stock
        Collection<Ingredient> coli = idao.liste();
        Collection<Produit> colp = pdao.liste();
         
        request.setAttribute("ingredients", coli);
        request.setAttribute("produits", colp);
        
        RequestDispatcher rd = request.getRequestDispatcher("mobile/stock.jsp");
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
