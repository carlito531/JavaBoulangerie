package web.servlet;

import commun.dao.BDDHelper;
import commun.dao.CategorieProduitDao;
import web.dao.DaoProduct;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commun.model.CategorieProduit;

@WebServlet(name = "SandwichsServlet", urlPatterns = {"/sandwichs_list"})
public class SandwichsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            DaoProduct daoProduct = new DaoProduct(connex);
            CategorieProduitDao daoCategorie = new CategorieProduitDao();
            
            CategorieProduit cat = daoCategorie.findCategorieByLibelle("Sandwichs");
            request.setAttribute("list_sandwichs", daoProduct.getListProductByIdCategorie(cat.getId()));
            RequestDispatcher rd = request.getRequestDispatcher("sandwichs.jsp");
            rd.forward(request, response);            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            myBddHelper.close(connex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
