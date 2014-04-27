package web.servlet;

import commun.dao.BDDHelper;
import dao.DaoIngredient;
import web.dao.DaoProduct;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import commun.model.Ingredient;
import commun.model.Produit;
import web.model.IngredientOfProduct;
import web.model.Panier;
import web.model.ProductOfPanier;

@WebServlet(name = "AjoutPanierSaladePersoServlet", urlPatterns = {"/ajoutpaniersaladeperso"})
public class AjoutPanierSaladePersoServlet extends HttpServlet {

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
        // Permet de récupérer l'url de la page qui a appelé la servlet AjoutPanierSaladePersoServlet
        String pageAppelante = request.getParameter("page_name");
        
        if (request.getParameter("reset") != null){
            // Si l'utilisateur a cliqué sur Annuler alors on redirige vers la page appelante
             RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
             rd.forward(request, response);  
        }else if (request.getParameter("addSalade") != null){
            BDDHelper myBddHelper = new BDDHelper();
            Connection connex = null;
            
            try {
                connex = myBddHelper.open();
                Panier myPanier = null;      
                Collection<IngredientOfProduct> mylistIngredientsProduct = new ArrayList<IngredientOfProduct>();
                Collection<ProductOfPanier> mylistArticlesPanier = new ArrayList<ProductOfPanier>();
                DaoProduct daoProduct = new DaoProduct(connex);            
                ProductOfPanier pop = null;
                // Le produit est une salade personnalisée
                Produit p = daoProduct.findProductByLibelle("Salade Personnalisée");
                
                HttpSession sessionPanier = request.getSession();
            
                // Si l'utilisateur a cliqué sur le bouton ajouter alors on effectue les tests suivants  
                // Si le panier de l'utilisateur n'est pas vide alors
                if (sessionPanier.getAttribute("panier") != null){
                    myPanier = (Panier)sessionPanier.getAttribute("panier");
                    int qteTotal = 0;
                    float prixTotalPanier = 0.F;
                    int qteArticle = 1;
                    float prixTotalArticle = 0.F;
                        
                    // On récupère le contenu du panier actuel
                    mylistArticlesPanier = myPanier.getListArticlesPanier();
                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                    df.applyPattern("#,##0.00");
                    
                    // On récupère la liste des ingrédients que l'utilisateur veut ajouter à sa salade
                    mylistIngredientsProduct = checkListOfCheckbox(request, response, mylistIngredientsProduct, myPanier);
                    if (mylistIngredientsProduct != null)
                    {
                        // Pour chaque ingrédient de la liste récupérée 
                        // On va vérifier que la quantité demandée plus la quantité déjà existante est toujours disponible en stock
                        for (IngredientOfProduct i : mylistIngredientsProduct)
                        {
                            // On calcule le prix total de la salade personnalisée en ajoutant le prix de chaque ingrédient multiplié par la quantité
                            prixTotalArticle = prixTotalArticle + Float.parseFloat(i.getTotalPrixIngredient().replace(",", "."));
                        }
                        p.setPrix(df.format(prixTotalArticle));                        
                        pop = new ProductOfPanier(p, qteArticle, df.format(prixTotalArticle * qteArticle), mylistIngredientsProduct, 0);
                        mylistArticlesPanier.add(pop);
                        
                        // On recalcule le prix total du panier
                        for (ProductOfPanier prod : mylistArticlesPanier){
                            prixTotalPanier = prixTotalPanier + Float.parseFloat(prod.getTotalPrixArticle().replace(",", "."));
                            qteTotal = qteTotal + prod.getQuantiteArticle();
                        }             
                                                
                        // On actualise les données du panier
                        myPanier.setListArticlesPanier(mylistArticlesPanier);
                        myPanier.setPrixTotalPanier(df.format(prixTotalPanier));
                        myPanier.setQteArticlesPanier(qteTotal);

                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);                       
                    }
                }else{
                    int qteTotal = 0;
                    float prixTotalPanier = 0.F;
                    int qteArticle = 1;
                    float prixTotalArticle = 0.F;
                    
                    // On récupère la liste des ingrédients que l'utilisateur veut ajouter à sa salade
                    mylistIngredientsProduct = checkListOfCheckbox(request, response, mylistIngredientsProduct, myPanier);                   
                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                    df.applyPattern("#,##0.00");
                    
                    if (mylistIngredientsProduct != null)
                    {                        
                        for (IngredientOfProduct i : mylistIngredientsProduct)
                        {
                            // On calcule le prix total de la salade personnalisée en ajoutant le prix de chaque ingrédient multiplié par la quantité
                            prixTotalArticle = prixTotalArticle + Float.parseFloat(i.getTotalPrixIngredient().replace(",", "."));
                        }
                        p.setPrix(df.format(prixTotalArticle));
                        pop = new ProductOfPanier(p, qteArticle, df.format(prixTotalArticle * qteArticle), mylistIngredientsProduct, 0);
                        mylistArticlesPanier.add(pop);
                        
                        // On calcule le prix total du panier
                        prixTotalPanier = prixTotalPanier + prixTotalArticle;
                        // On calcule la quantité totale d'articles dans le panier
                        qteTotal = qteTotal + qteArticle;
                        
                        // On créé un nouveau panier
                        myPanier = new Panier(mylistArticlesPanier, df.format(prixTotalPanier), qteTotal);
                        sessionPanier.setAttribute("panier", myPanier);       
                        
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);       
                    }                    
                }                             
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally{
                myBddHelper.close(connex);
            }
        }else{
            // Sinon on redirige vers l'accueil
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
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
    }
    
    private Collection<IngredientOfProduct> checkListOfCheckbox(HttpServletRequest request, HttpServletResponse response, Collection<IngredientOfProduct> mylistIngredientsProduct, Panier myPanier)
    {
        // Permet de récupérer l'url de la page qui a appelé la servlet AjoutPanierSaladePersoServlet
        String pageAppelante = request.getParameter("page_name");
        
        BDDHelper myBddHelper = new BDDHelper();
        Connection connex = null;
        
        try {
            connex = myBddHelper.open();
            DaoIngredient daoIngredient = new DaoIngredient(connex);
            Ingredient i = null;
            IngredientOfProduct iop = null;
            
            // Vérification des checkbox opt_salade
            String[] optSalade = request.getParameterValues("opt_salade");

            if(optSalade == null)
            {
                request.setAttribute("error_checkbox_salade", "Veuillez choisir le type de salade pour votre salade !");
                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                rd.forward(request, response);
                return null;
            }
            else
            {
                // Si plus d'une checkbox opt_salade est cochée alors on renvoie une erreur
                if (optSalade.length > 1){
                    request.setAttribute("error_checkbox_salade", "Veuillez ne saisir qu'un seul type de salade pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // On récupère la valeur de la checkbox                
                String optSaladeValue = "";
                for (String optSaladeChecked : optSalade) {
                    optSaladeValue = optSaladeChecked.replace("opt_salade_", "");
                    long idIngr = Long.parseLong(optSaladeValue);
                    i = daoIngredient.findIngredientById(idIngr);
                    // On vérifie que l'ingrédient est disponible dans la quantité demandée
                    if (myPanier == null){
                        if ((i.getStock() - 1) < 0)
                        {
                            request.setAttribute("error_checkbox_salade", "La quantité demandée : " + 1 + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                            rd.forward(request, response);
                            return null;
                        }       
                    }else{
                        for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                        {
                            if (prod.getListIngredientOfProduct() != null){
                                for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                {
                                    // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                    if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                        // On additionne les 2 quantités et on vérifie le stock
                                        if ((i.getStock() - (ingr.getQuantiteIngredient() + 1)) < 0){
                                            request.setAttribute("error_checkbox_salade", "La quantité demandée : 1 de " + i.getLibelle() + "<br />" +
                                                    "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                    "n'est pas disponible en stock !");
                                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                            rd.forward(request, response);  
                                            return null;
                                        }
                                    }
                                }                                
                            }
                        }    
                    }

                    float prixTotalIngredient = 1 * Float.parseFloat(i.getPrix().replace(",", "."));
                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                    df.applyPattern("#,##0.00");                    
                    iop = new IngredientOfProduct(i, 1, 1, df.format(prixTotalIngredient));
                    mylistIngredientsProduct.add(iop);
                }  
                // A commenter
                // request.setAttribute("error_checkbox_salade", optSaladeValue);       
                //
            }
            // Si l'utilisateur n'a pas effectué de choix pour l'élément principal de la salade, on renvoie un message d'erreur
            if (request.getParameter("main_element") == null)
            {
                request.setAttribute("error_checkbox_main_elem", "Veuillez choisir l'élément principal pour votre salade !");
                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                rd.forward(request, response);
                return null;
            }else{
                String[] mainElement = request.getParameterValues("main_element");

                for (String mainElementChosen : mainElement){
                    if (mainElementChosen.equals("main_element_poisson")){
                        // Si l'utilisateur a choisi l'élément principal poisson alors on vérifie les checkbox opt_poisson
                        String[] optPoisson = request.getParameterValues("opt_poisson");
                        int count = 0;

                        if(optPoisson == null){
                            request.setAttribute("error_checkbox_main_elem", "Veuillez choisir le poisson pour votre salade !");
                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                            rd.forward(request, response);
                            return null;
                        }else{
                            // Si plus de deux checkbox opt_poisson sont cochées alors on renvoie une erreur
                            if (optPoisson.length > 2){
                                request.setAttribute("error_checkbox_main_elem", "Veuillez ne saisir qu'un ou deux types de poisson pour votre salade !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }
                            // Enfin on vérifie que le client a choisi un poisson de quantité 2 ou deux poissons de quantité 1
                            for (String optPoissonValue : optPoisson){
                                int qteOptPoissonValue;
                                try {
                                    // On vérifie que le client a saisi une valeur numérique
                                    qteOptPoissonValue = Integer.parseInt(request.getParameter("qte_" + optPoissonValue));                                                            
                                } catch (NumberFormatException ex) {
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour le poisson choisi n'est pas valide !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }

                                if (qteOptPoissonValue <= 0){
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour le poisson choisi doit être supérieure à 0 !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }else if (qteOptPoissonValue == 1){
                                     count = count + 1;
                                }else if (qteOptPoissonValue == 2){
                                    count = count + 2;
                                }else{
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour le poisson choisi n'est pas valide !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }                                
                            } 
                            if (count > 2){
                                request.setAttribute("error_checkbox_main_elem", "Vous ne pouvez choisir que deux poissons de quantité 1 ou un poisson de quantité 2 !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }else{
                                // On récupère la valeur de la checkbox                
                                String optionsPoissonValue = "";
                                for (String optPoissonValue : optPoisson){
                                    // On récupère la quantité désirée par le client
                                    int qteOptPoissonValue = Integer.parseInt(request.getParameter("qte_" + optPoissonValue));
                                    // On récupère l'identifiant de l'ingrédient
                                    optionsPoissonValue = optPoissonValue.replace("opt_poisson_", "");
                                    long idIngr = Long.parseLong(optionsPoissonValue);
                                    // On récupère l'ingrédient correspondant à cet identifiant
                                    i = daoIngredient.findIngredientById(idIngr);
                                    // On vérifie que l'ingrédient est disponible dans la quantité demandée
                                    if (myPanier == null){
                                        if ((i.getStock() - qteOptPoissonValue) < 0)
                                        {
                                            request.setAttribute("error_checkbox_main_elem", "La quantité demandée : " + qteOptPoissonValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                            rd.forward(request, response);
                                            return null;
                                        }      
                                    }else{
                                        for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                                        {
                                            if (prod.getListIngredientOfProduct() != null){
                                                for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                                {
                                                    // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                                    if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                                        // On additionne les 2 quantités et on vérifie le stock
                                                        if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptPoissonValue)) < 0){
                                                            request.setAttribute("error_checkbox_main_elem", "La quantité demandée : " + qteOptPoissonValue + 
                                                                    " de " + i.getLibelle() + "<br />" +
                                                                    "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                                    "n'est pas disponible en stock !");
                                                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                            rd.forward(request, response);  
                                                            return null;
                                                        }
                                                    }
                                                }                                
                                            }
                                        } 
                                    }

                                    float prixTotalIngredient = qteOptPoissonValue * Float.parseFloat(i.getPrix().replace(",", "."));
                                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                                    df.applyPattern("#,##0.00");
                                    iop = new IngredientOfProduct(i, qteOptPoissonValue, qteOptPoissonValue, df.format(prixTotalIngredient));
                                    mylistIngredientsProduct.add(iop);
                                }
                                // A commenter
                                // request.setAttribute("error_checkbox_main_elem", optionsPoissonValue);      
                                //
                            }                            
                         }                       
                    }
                    if (mainElementChosen.equals("main_element_charcuterie")){
                        // Si l'utilisateur a choisi l'élément principal charcuterie alors on vérifie les checkbox opt_charcu
                        String[] optCharcu = request.getParameterValues("opt_charcu");
                        int count = 0;

                        if(optCharcu == null){
                            request.setAttribute("error_checkbox_main_elem", "Veuillez choisir la charcuterie pour votre salade !");
                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                            rd.forward(request, response);
                            return null;
                        }else{
                            // Si plus de deux checkbox opt_charcu sont cochées alors on renvoie une erreur
                            if (optCharcu.length > 2){
                                request.setAttribute("error_checkbox_main_elem", "Veuillez ne saisir qu'un ou deux types de charcuterie pour votre salade !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }
                            // Enfin on vérifie que le client a choisi une charcuterie de quantité 2 ou deux charcuterie de quantité 1
                            for (String optCharcuValue : optCharcu){
                                int qteOptCharcuValue;
                                // On vérifie que le client a saisi une valeur numérique
                                String inputParamQteCharcu = "qte_" + optCharcuValue;
                                try {           
                                    qteOptCharcuValue = Integer.parseInt(request.getParameter(inputParamQteCharcu));                                                            
                                } catch (NumberFormatException ex) {
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour la charcuterie choisie n'est pas valide !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }

                                if (qteOptCharcuValue <= 0){
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour la charcuterie choisie doit être supérieure à 0 !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }else if (qteOptCharcuValue == 1){
                                     count = count + 1;
                                }else if (qteOptCharcuValue == 2){
                                    count = count + 2;
                                }else{
                                    request.setAttribute("error_checkbox_main_elem", "La quantité saisie pour la charcuterie choisie n'est pas valide !");
                                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                    rd.forward(request, response);
                                    return null;
                                }                                
                            } 
                            if (count > 2){
                                request.setAttribute("error_checkbox_main_elem", "Vous ne pouvez choisir que deux charcuterie de quantité 1 ou une charcuterie de quantité 2 !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }else{
                                // On récupère la valeur de la checkbox                
                                String optionsCharcuValue = "";
                                for (String optCharcuValue : optCharcu){
                                    // On récupère la quantité désirée par le client
                                    int qteOptCharcuValue = Integer.parseInt(request.getParameter("qte_" + optCharcuValue));
                                    // On récupère l'identifiant de l'ingrédient
                                    optionsCharcuValue = optCharcuValue.replace("opt_charcu_", "");
                                    long idIngr = Long.parseLong(optionsCharcuValue);
                                    // On récupère l'ingrédient correspondant à cet identifiant
                                    i = daoIngredient.findIngredientById(idIngr);
                                    // On vérifie que l'ingrédient est disponible dans la quantité demandée
                                    if (myPanier == null){
                                        if ((i.getStock() - qteOptCharcuValue) < 0)
                                        {
                                            request.setAttribute("error_checkbox_main_elem", "La quantité demandée : " + qteOptCharcuValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                            rd.forward(request, response);
                                            return null;
                                        }        
                                    }else{
                                        for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                                        {
                                            if (prod.getListIngredientOfProduct() != null){
                                                for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                                {
                                                    // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                                    if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                                        // On additionne les 2 quantités et on vérifie le stock
                                                        if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptCharcuValue)) < 0){
                                                            request.setAttribute("error_checkbox_main_elem", "La quantité demandée : " + qteOptCharcuValue + 
                                                                    " de " + i.getLibelle() + "<br />" +
                                                                    "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                                    "n'est pas disponible en stock !");
                                                            RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                            rd.forward(request, response);  
                                                            return null;
                                                        }
                                                    }
                                                }                                
                                            }
                                        }      
                                    }

                                    float prixTotalIngredient = qteOptCharcuValue * Float.parseFloat(i.getPrix().replace(",", "."));
                                    DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                                    df.applyPattern("#,##0.00");
                                    iop = new IngredientOfProduct(i, qteOptCharcuValue, qteOptCharcuValue, df.format(prixTotalIngredient));
                                    mylistIngredientsProduct.add(iop);
                                }
                                // A commenter
                                // request.setAttribute("error_checkbox_main_elem", optionsCharcuValue);       
                                //
                            }
                        }                      
                    }
                }
            }
            // Si l'utilisateur a sélectionné des checkbox pour la garniture alors on vérifie les checkbox opt_legume
            String[] optLegume = request.getParameterValues("opt_legume");
            int count = 0;

            // On vérifie si les checkbox opt_legume sont cochées.
            // Si elles ne le sont pas on ne renvoie pas d'erreur car le choix de garniture est optionnel
            if(optLegume != null){                        
                // Si plus de deux checkbox opt_legume sont cochées alors on renvoie une erreur
                if (optLegume.length > 2){
                    request.setAttribute("error_checkbox_legume", "Veuillez ne saisir qu'un ou deux types de légume pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // Enfin on vérifie que le client a choisi un légume de quantité 2 ou deux légumes de quantité 1
                for (String optLegumeValue : optLegume){
                    int qteOptLegumeValue;
                    try {
                        // On vérifie que le client a saisi une valeur numérique
                        qteOptLegumeValue = Integer.parseInt(request.getParameter("qte_" + optLegumeValue));                                                            
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_checkbox_legume", "La quantité saisie pour la garniture choisie n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }

                    if (qteOptLegumeValue <= 0){
                        request.setAttribute("error_checkbox_legume", "La quantité saisie pour la garniture choisie doit être supérieure à 0 !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }else if (qteOptLegumeValue == 1){
                         count = count + 1;
                    }else if (qteOptLegumeValue == 2){
                        count = count + 2;
                    }else{
                        request.setAttribute("error_checkbox_legume", "La quantité saisie pour la garniture choisie n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }                                
                }
                if (count > 2){
                    request.setAttribute("error_checkbox_legume", "Vous ne pouvez choisir que deux légumes de quantité 1 ou un légume de quantité 2 !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }else{
                    // On récupère la valeur de la checkbox                
                    String optionsLegumeValue = "";
                    for (String optLegumeValue : optLegume){
                        // On récupère la quantité désirée par le client
                        int qteOptLegumeValue = Integer.parseInt(request.getParameter("qte_" + optLegumeValue));
                        // On récupère l'identifiant de l'ingrédient
                        optionsLegumeValue = optLegumeValue.replace("opt_legume_", "");
                        long idIngr = Long.parseLong(optionsLegumeValue);
                        // On récupère l'ingrédient correspondant à cet identifiant
                        i = daoIngredient.findIngredientById(idIngr);
                        // On vérifie que l'ingrédient est disponible dans la quantité demandée
                        if (myPanier == null){
                            if ((i.getStock() - qteOptLegumeValue) < 0)
                            {
                                request.setAttribute("error_checkbox_legume", "La quantité demandée : " + qteOptLegumeValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }                
                        }else{
                            for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                            {
                                if (prod.getListIngredientOfProduct() != null){
                                    for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                    {
                                        // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                        if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                            // On additionne les 2 quantités et on vérifie le stock
                                            if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptLegumeValue)) < 0){
                                                request.setAttribute("error_checkbox_legume", "La quantité demandée : " + qteOptLegumeValue + 
                                                        " de " + i.getLibelle() + "<br />" +
                                                        "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                        "n'est pas disponible en stock !");
                                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                rd.forward(request, response);  
                                                return null;
                                            }
                                        }
                                    }                                
                                }
                            }  
                        }

                        float prixTotalIngredient = qteOptLegumeValue * Float.parseFloat(i.getPrix().replace(",", "."));
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        iop = new IngredientOfProduct(i, qteOptLegumeValue, qteOptLegumeValue, df.format(prixTotalIngredient));
                        mylistIngredientsProduct.add(iop);
                    }
                    // A commenter
                    // request.setAttribute("error_checkbox_legume", optionsLegumeValue);      
                    //
                }
            }                

            // Si l'utilisateur a sélectionné des checkbox pour la sauce alors on vérifie les checkbox opt_sauce
            String[] optSauce = request.getParameterValues("opt_sauce");
            count = 0;

            // On vérifie si les checkbox opt_sauce sont cochées.
            // Si elles ne le sont pas on ne renvoie pas d'erreur car le choix de la sauce est optionnel
            if(optSauce != null){                        
                // Si plus de deux checkbox opt_sauce sont cochées alors on renvoie une erreur
                if (optSauce.length > 2){
                    request.setAttribute("error_checkbox_sauce", "Veuillez ne saisir qu'un ou deux types de sauce pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // Enfin on vérifie que le client a choisi une sauce de quantité 2 ou deux sauces de quantité 1
                for (String optSauceValue : optSauce){
                    int qteOptSauceValue;
                    try {
                        // On vérifie que le client a saisi une valeur numérique
                        qteOptSauceValue = Integer.parseInt(request.getParameter("qte_" + optSauceValue));                                                            
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_checkbox_sauce", "La quantité saisie pour la sauce choisie n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }

                    if (qteOptSauceValue <= 0){
                        request.setAttribute("error_checkbox_sauce", "La quantité saisie pour la sauce choisie doit être supérieure à 0 !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }else if (qteOptSauceValue == 1){
                         count = count + 1;
                    }else if (qteOptSauceValue == 2){
                        count = count + 2;
                    }else{
                        request.setAttribute("error_checkbox_sauce", "La quantité saisie pour la sauce choisie n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }                                
                }  
                if (count > 2){
                    request.setAttribute("error_checkbox_sauce", "Vous ne pouvez choisir que deux sauces de quantité 1 ou une sauce de quantité 2 !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }else{
                    // On récupère la valeur de la checkbox                
                    String optionsSauceValue = "";
                    for (String optSauceValue : optSauce){
                        // On récupère la quantité désirée par le client
                        int qteOptSauceValue = Integer.parseInt(request.getParameter("qte_" + optSauceValue));
                        // On récupère l'identifiant de l'ingrédient
                        optionsSauceValue = optSauceValue.replace("opt_sauce_", "");
                        long idIngr = Long.parseLong(optionsSauceValue);
                        // On récupère l'ingrédient correspondant à cet identifiant
                        i = daoIngredient.findIngredientById(idIngr);
                        // On vérifie que l'ingrédient est disponible dans la quantité demandée
                        if (myPanier == null){
                            if ((i.getStock() - qteOptSauceValue) < 0)
                            {
                                request.setAttribute("error_checkbox_sauce", "La quantité demandée : " + qteOptSauceValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }               
                        }else{
                            for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                            {
                                if (prod.getListIngredientOfProduct() != null){
                                    for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                    {
                                        // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                        if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                            // On additionne les 2 quantités et on vérifie le stock
                                            if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptSauceValue)) < 0){
                                                request.setAttribute("error_checkbox_sauce", "La quantité demandée : " + qteOptSauceValue + 
                                                        " de " + i.getLibelle() + "<br />" +
                                                        "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                        "n'est pas disponible en stock !");
                                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                rd.forward(request, response);  
                                                return null;
                                            }
                                        }
                                    }                                
                                }
                            }    
                        }

                        float prixTotalIngredient = qteOptSauceValue * Float.parseFloat(i.getPrix().replace(",", "."));
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        iop = new IngredientOfProduct(i, qteOptSauceValue, qteOptSauceValue, df.format(prixTotalIngredient));
                        mylistIngredientsProduct.add(iop);
                    }
                    // A commenter
                    // request.setAttribute("error_checkbox_sauce", optionsSauceValue);      
                    //
                }
            }

            // Si l'utilisateur a sélectionné des checkbox pour le fromage alors on vérifie les checkbox opt_fromage
            String[] optFromage = request.getParameterValues("opt_fromage");
            count = 0;

            // On vérifie si les checkbox opt_fromage sont cochées.
            // Si elles ne le sont pas on ne renvoie pas d'erreur car le choix du fromage est optionnel
            if(optFromage != null){                        
                // Si plus de deux checkbox opt_fromage sont cochées alors on renvoie une erreur
                if (optFromage.length > 2){
                    request.setAttribute("error_checkbox_fromage", "Veuillez ne saisir qu'un ou deux types de fromage pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // Enfin on vérifie que le client a choisi un fromage de quantité 2 ou deux fromages de quantité 1
                for (String optFromageValue : optFromage){
                    int qteOptFromageValue;
                    try {
                        // On vérifie que le client a saisi une valeur numérique
                        qteOptFromageValue = Integer.parseInt(request.getParameter("qte_" + optFromageValue));                                                            
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_checkbox_fromage", "La quantité saisie pour le fromage choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }

                    if (qteOptFromageValue <= 0){
                        request.setAttribute("error_checkbox_fromage", "La quantité saisie pour le fromage choisi doit être supérieure à 0 !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }else if (qteOptFromageValue == 1){
                         count = count + 1;
                    }else if (qteOptFromageValue == 2){
                        count = count + 2;
                    }else{
                        request.setAttribute("error_checkbox_fromage", "La quantité saisie pour le fromage choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }                                
                }  
                if (count > 2){
                    request.setAttribute("error_checkbox_fromage", "Vous ne pouvez choisir que deux fromages de quantité 1 ou un fromage de quantité 2 !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }else{
                    // On récupère la valeur de la checkbox                
                    String optionsFromageValue = "";
                    for (String optFromageValue : optFromage){
                        // On récupère la quantité désirée par le client
                        int qteOptFromageValue = Integer.parseInt(request.getParameter("qte_" + optFromageValue));
                        // On récupère l'identifiant de l'ingrédient
                        optionsFromageValue = optFromageValue.replace("opt_fromage_", "");
                        long idIngr = Long.parseLong(optionsFromageValue);
                        // On récupère l'ingrédient correspondant à cet identifiant
                        i = daoIngredient.findIngredientById(idIngr);
                        // On vérifie que l'ingrédient est disponible dans la quantité demandée
                        if (myPanier == null){
                            if ((i.getStock() - qteOptFromageValue) < 0)
                            {
                                request.setAttribute("error_checkbox_fromage", "La quantité demandée : " + qteOptFromageValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }                    
                        }else{
                            for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                            {
                                if (prod.getListIngredientOfProduct() != null){
                                    for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                    {
                                        // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                        if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                            // On additionne les 2 quantités et on vérifie le stock
                                            if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptFromageValue)) < 0){
                                                request.setAttribute("error_checkbox_fromage", "La quantité demandée : " + qteOptFromageValue + 
                                                        " de " + i.getLibelle() + "<br />" +
                                                        "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                        "n'est pas disponible en stock !");
                                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                rd.forward(request, response);  
                                                return null;
                                            }
                                        }
                                    }                                
                                }
                            } 
                        }

                        float prixTotalIngredient = qteOptFromageValue * Float.parseFloat(i.getPrix().replace(",", "."));
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        iop = new IngredientOfProduct(i, qteOptFromageValue, qteOptFromageValue, df.format(prixTotalIngredient));
                        mylistIngredientsProduct.add(iop);
                    }
                    // A commenter
                    // request.setAttribute("error_checkbox_fromage", optionsFromageValue);      
                    //
                }
            }            
            
            // Si l'utilisateur a sélectionné des checkbox pour un fruit alors on vérifie les checkbox opt_fruit
            String[] optFruit = request.getParameterValues("opt_fruit");
            count = 0;

            // On vérifie si les checkbox opt_fruit sont cochées.
            // Si elles ne le sont pas on ne renvoie pas d'erreur car le choix d'un fruit est optionnel
            if(optFruit != null){                        
                // Si plus de deux checkbox opt_fruit sont cochées alors on renvoie une erreur
                if (optFruit.length > 2){
                    request.setAttribute("error_checkbox_fruit", "Veuillez ne saisir qu'un ou deux types de fruit pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // Enfin on vérifie que le client a choisi un fruit de quantité 2 ou deux fruits de quantité 1
                for (String optFruitValue : optFruit){
                    int qteOptFruitValue;
                    try {
                        // On vérifie que le client a saisi une valeur numérique
                        qteOptFruitValue = Integer.parseInt(request.getParameter("qte_" + optFruitValue));                                                            
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_checkbox_fruit", "La quantité saisie pour le fruit choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }

                    if (qteOptFruitValue <= 0){
                        request.setAttribute("error_checkbox_fruit", "La quantité saisie pour le fruit choisi doit être supérieure à 0 !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }else if (qteOptFruitValue == 1){
                         count = count + 1;
                    }else if (qteOptFruitValue == 2){
                        count = count + 2;
                    }else{
                        request.setAttribute("error_checkbox_fruit", "La quantité saisie pour le fruit choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }                                
                }  
                if (count > 2){
                    request.setAttribute("error_checkbox_fruit", "Vous ne pouvez choisir que deux fruits de quantité 1 ou un fruit de quantité 2 !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }else{
                    // On récupère la valeur de la checkbox                
                    String optionsFruitValue = "";
                    for (String optFruitValue : optFruit){
                        // On récupère la quantité désirée par le client
                        int qteOptFruitValue = Integer.parseInt(request.getParameter("qte_" + optFruitValue));
                        // On récupère l'identifiant de l'ingrédient
                        optionsFruitValue = optFruitValue.replace("opt_fruit_", "");
                        long idIngr = Long.parseLong(optionsFruitValue);
                        // On récupère l'ingrédient correspondant à cet identifiant
                        i = daoIngredient.findIngredientById(idIngr);
                        // On vérifie que l'ingrédient est disponible dans la quantité demandée
                        if (myPanier == null){
                            if ((i.getStock() - qteOptFruitValue) < 0)
                            {
                                request.setAttribute("error_checkbox_fruit", "La quantité demandée : " + qteOptFruitValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }                         
                        }else{
                            for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                            {
                                if (prod.getListIngredientOfProduct() != null){
                                    for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                    {
                                        // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                        if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                            // On additionne les 2 quantités et on vérifie le stock
                                            if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptFruitValue)) < 0){
                                                request.setAttribute("error_checkbox_fruit", "La quantité demandée : " + qteOptFruitValue + 
                                                        " de " + i.getLibelle() + "<br />" +
                                                        "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                        "n'est pas disponible en stock !");
                                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                rd.forward(request, response);  
                                                return null;
                                            }
                                        }
                                    }                                
                                }
                            } 
                        }

                        float prixTotalIngredient = qteOptFruitValue * Float.parseFloat(i.getPrix().replace(",", "."));
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        iop = new IngredientOfProduct(i, qteOptFruitValue, qteOptFruitValue, df.format(prixTotalIngredient));
                        mylistIngredientsProduct.add(iop);
                    }
                    // A commenter
                    // request.setAttribute("error_checkbox_fruit", optionsFruitValue);      
                    //
                }
            }
            
            // Si l'utilisateur a sélectionné des checkbox pour un extra alors on vérifie les checkbox opt_extra
            String[] optExtra = request.getParameterValues("opt_extra");
            count = 0;

            // On vérifie si les checkbox opt_extra sont cochées.
            // Si elles ne le sont pas on ne renvoie pas d'erreur car le choix d'un extra est optionnel
            if(optExtra != null){                        
                // Si plus de deux checkbox opt_extra sont cochées alors on renvoie une erreur
                if (optExtra.length > 2){
                    request.setAttribute("error_checkbox_extra", "Veuillez ne saisir qu'un ou deux types d'extra pour votre salade !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }
                // Enfin on vérifie que le client a choisi un extra de quantité 2 ou deux extras de quantité 1
                for (String optExtraValue : optExtra){
                    int qteOptExtraValue;
                    try {
                        // On vérifie que le client a saisi une valeur numérique
                        qteOptExtraValue = Integer.parseInt(request.getParameter("qte_" + optExtraValue));                                                            
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error_checkbox_extra", "La quantité saisie pour l'extra choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }

                    if (qteOptExtraValue <= 0){
                        request.setAttribute("error_checkbox_extra", "La quantité saisie pour l'extra choisi doit être supérieure à 0 !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }else if (qteOptExtraValue == 1){
                         count = count + 1;
                    }else if (qteOptExtraValue == 2){
                        count = count + 2;
                    }else{
                        request.setAttribute("error_checkbox_extra", "La quantité saisie pour l'extra choisi n'est pas valide !");
                        RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                        rd.forward(request, response);
                        return null;
                    }                                
                }  
                if (count > 2){
                    request.setAttribute("error_checkbox_extra", "Vous ne pouvez choisir que deux extras de quantité 1 ou un extra de quantité 2 !");
                    RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                    rd.forward(request, response);
                    return null;
                }else{
                    // On récupère la valeur de la checkbox                
                    String optionsExtraValue = "";
                    for (String optExtraValue : optExtra){
                        // On récupère la quantité désirée par le client
                        int qteOptExtraValue = Integer.parseInt(request.getParameter("qte_" + optExtraValue));
                        // On récupère l'identifiant de l'ingrédient
                        optionsExtraValue = optExtraValue.replace("opt_extra_", "");
                        long idIngr = Long.parseLong(optionsExtraValue);
                        // On récupère l'ingrédient correspondant à cet identifiant
                        i = daoIngredient.findIngredientById(idIngr);
                        // On vérifie que l'ingrédient est disponible dans la quantité demandée
                        if (myPanier == null){
                            if ((i.getStock() - qteOptExtraValue) < 0)
                            {
                                request.setAttribute("error_checkbox_extra", "La quantité demandée : " + qteOptExtraValue + " de " + i.getLibelle().toString() + " n'est pas disponible en stock !");
                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                rd.forward(request, response);
                                return null;
                            }                           
                        }else{
                            for (ProductOfPanier prod : myPanier.getListArticlesPanier())
                            {
                                if (prod.getListIngredientOfProduct() != null){
                                    for (IngredientOfProduct ingr : prod.getListIngredientOfProduct())
                                    {
                                        // Si l'ingrédient est déjà présent pour un autre produit demandé alors on vérifie la quantité totale
                                        if (ingr.getIngredient().getLibelle().equals(i.getLibelle())){
                                            // On additionne les 2 quantités et on vérifie le stock
                                            if ((i.getStock() - (ingr.getQuantiteIngredient() + qteOptExtraValue)) < 0){
                                                request.setAttribute("error_checkbox_extra", "La quantité demandée : " + qteOptExtraValue + 
                                                        " de " + i.getLibelle() + "<br />" +
                                                        "additionnée aux quantités désirées de ce même ingrédient pour d'autres produits" + "<br />" +
                                                        "n'est pas disponible en stock !");
                                                RequestDispatcher rd = request.getRequestDispatcher(pageAppelante);
                                                rd.forward(request, response);  
                                                return null;
                                            }
                                        }
                                    }                                
                                }
                            }       
                        }

                        float prixTotalIngredient = qteOptExtraValue * Float.parseFloat(i.getPrix().replace(",", "."));
                        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
                        df.applyPattern("#,##0.00");
                        iop = new IngredientOfProduct(i, qteOptExtraValue, qteOptExtraValue, df.format(prixTotalIngredient));
                        mylistIngredientsProduct.add(iop);
                    }
                    // A commenter
                    // request.setAttribute("error_checkbox_extra", optionsExtraValue);      
                    //
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            myBddHelper.close(connex);
        }  
        return mylistIngredientsProduct;               
    }
    // </editor-fold>    
}
