package web.servlet;

import commun.dao.BDDHelper;
import commun.dao.ClientDao;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import commun.model.Client;

@WebServlet(name = "WebConnexionServlet", urlPatterns = {"/web_connexion"})
public class WebConnexionServlet extends HttpServlet {

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
        // On vérifie qu l'utilisateur a bien cliqué sur Connexion avant d'exécuter les actions suivantes
        if (request.getParameter("button_co") != null)
        {
            String login = request.getParameter("txtbox_id");
            String mdp = request.getParameter("txtbox_pwd");
            String pageAppelante = request.getParameter("page_name");

            BDDHelper myBddHelper = new BDDHelper();
            Connection connex = null;
            Client u = null;

            if (login.equals("") || mdp.equals("")){
                request.setAttribute("error_empty", "Veuillez saisir un login et mot de passe !");
                request.setAttribute("recup_login", login);
                RequestDispatcher rd = request.getRequestDispatcher("connexion.jsp");
                rd.forward(request, response);
            }else{
                try {
//                    connex = myBddHelper.open();
                    ClientDao daoUser = new ClientDao();

                    u = daoUser.trouve(login);

                    if (u != null){
                        if (mdp.equals(u.getMotDePasse())){                    
                            HttpSession session = request.getSession();
                            session.setAttribute("utilisateur", u);
                            if(pageAppelante == null)
                            {
                                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                                rd.forward(request, response);
                            }else
                            {
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);  
                            }                                    
                        }else {
                            request.setAttribute("error_auth", "Veuillez saisir un login et mot de passe correct !");
                            request.setAttribute("recup_login", login);
                            RequestDispatcher rd = request.getRequestDispatcher("connexion.jsp");
                            rd.forward(request, response);
                        }        
                    }else{
                        request.setAttribute("error_cmpt", "Erreur d'authentification veuillez réessayer !");
                        request.setAttribute("recup_login", login);
                        RequestDispatcher rd = request.getRequestDispatcher("connexion.jsp");
                        rd.forward(request, response);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally{
                    myBddHelper.close(connex);
                }
            } 
        }else{
            // Sinon on redirige vers la page de connexion
            String pageAppelante = request.getParameter("page_name");
            
            if (pageAppelante != null){
                if (pageAppelante.equals("consultepanier")){
                    request.setAttribute("error_add_commande", "Veuillez vous connecter avant de passer votre commande !");
                    request.setAttribute("page_appelante", "consultepanier");
                }
            }
            String login = "Identifiant...";
            request.setAttribute("recup_login", login);
            RequestDispatcher rd = request.getRequestDispatcher("connexion.jsp");
            rd.forward(request, response);
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
