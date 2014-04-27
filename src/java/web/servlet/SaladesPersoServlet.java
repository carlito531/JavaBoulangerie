package web.servlet;

import commun.dao.BDDHelper;
import commun.dao.CategorieProduitDao;
import dao.DaoIngredient;
import dao.DaoTypeIngredient;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commun.model.CategorieProduit;
import model.TypeIngredient;

@WebServlet(name = "SaladesPersoServlet", urlPatterns = {"/salades_perso_list"})
public class SaladesPersoServlet extends HttpServlet {

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
        
        try {
            connex = myBddHelper.open();
            DaoIngredient daoIngredient = new DaoIngredient(connex);
            CategorieProduitDao daoCategorie = new CategorieProduitDao();
            DaoTypeIngredient daoTypeIngredient = new DaoTypeIngredient(connex);
                               
            CategorieProduit cat = daoCategorie.findCategorieByLibelle("Salades Personnalisées");
            
            TypeIngredient typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Salades");    
            request.setAttribute("list_ingredients_salade", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Poissons");
            request.setAttribute("list_ingredients_poisson", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Charcuterie");
            request.setAttribute("list_ingredients_charcuterie", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Légumes");
            request.setAttribute("list_ingredients_legume", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Sauces");
            request.setAttribute("list_ingredients_sauce", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Fromages");
            request.setAttribute("list_ingredients_fromage", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Fruits");
            request.setAttribute("list_ingredients_fruit", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            typeIng = daoTypeIngredient.findTypeIngredientByLibelle("Divers");
            request.setAttribute("list_ingredients_extra", daoIngredient.getListOfIngredientsByIdCategorieAndIdType(cat.getId(), typeIng.getIdentifiant()));
            typeIng = null;
            
            RequestDispatcher rd = request.getRequestDispatcher("salades_perso.jsp");
            rd.forward(request, response);            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            myBddHelper.close(connex);
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
