package web.servlet;

import commun.dao.BDDHelper;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.model.Panier;
import web.model.ProductOfPanier;

@WebServlet(name = "ConsultePanierServlet", urlPatterns = {"/consultepanier"})
public class ConsultePanierServlet extends HttpServlet {

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
            Collection<ProductOfPanier> mylistArticlesPanier;
            Panier myPanier = null;
            
            HttpSession sessionPanier = request.getSession();
            
            if (sessionPanier.getAttribute("panier") != null){
                myPanier = (Panier)sessionPanier.getAttribute("panier");
                
                mylistArticlesPanier = myPanier.getListArticlesPanier();
                request.setAttribute("list_prod_panier", mylistArticlesPanier);                
            }else{
                sessionPanier.setAttribute("panier", myPanier);
            }                             
            RequestDispatcher rd = request.getRequestDispatcher("panier.jsp");
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
