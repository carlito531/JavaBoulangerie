package web.servlet;

import commun.dao.BDDHelper;
import web.dao.DaoCommande;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.model.IngredientOfProduct;
import web.model.Panier;
import web.model.ProductOfPanier;
import commun.model.Client;

@WebServlet(name = "ConsulteMyCommandeServlet", urlPatterns = {"/consultemycommande"})
public class ConsulteMyCommandeServlet extends HttpServlet {

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

            HttpSession session = request.getSession();

            // Si l'utilisateur est bien connecté alors
            if (session.getAttribute("utilisateur") != null){               
                Client u = (Client)session.getAttribute("utilisateur");
                DaoCommande daoCommande = new DaoCommande(connex);
                long idCom = Long.parseLong(request.getParameter("id_commande"));
                String prixTotCommande = request.getParameter("prix_tot_commande");
                Panier panier = null;
                int nbArticles = 0;
                                
                Collection<ProductOfPanier> myListOfProduct = daoCommande.getListOfProductByIdCommande(idCom);
                if (!myListOfProduct.isEmpty())
                {
                    for (ProductOfPanier pop : myListOfProduct)
                    {
                        nbArticles = nbArticles + pop.getQuantiteArticle();
                        
                        if ((pop.getArticle().getId() == 27) || (pop.getArticle().getId() == 28))
                        {
                            float prixUnit = Float.parseFloat(pop.getTotalPrixArticle().replace(",", ".")) / pop.getQuantiteArticle();
                            
                            DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                            df.applyPattern("#,##0.00");
                            
                            pop.getArticle().setPrix(df.format(prixUnit));
                            if (pop.getIdentifiantRecette() != 0){
                                if (daoCommande.getListOfIngredientByIdRecette(pop.getIdentifiantRecette()) != null)
                                {
                                    pop.setListIngredientOfProduct(daoCommande.getListOfIngredientByIdRecette(pop.getIdentifiantRecette()));
                                }
                            }
                        }
                    }                            
                    
                    panier = new Panier(myListOfProduct, prixTotCommande, nbArticles);                    
                }                
                request.setAttribute("my_commande", panier);
                RequestDispatcher rd = request.getRequestDispatcher("commandes.jsp");
                rd.forward(request, response);
            }else{
                // Sinon on redirige vers l'accueil
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }   
        } catch (Exception ex){
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
