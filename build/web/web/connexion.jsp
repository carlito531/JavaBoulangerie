<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="error_empty" value="${requestScope['error_empty']}" />
<c:set var="error_auth" value="${requestScope['error_auth']}" />
<c:set var="error_cmpt" value="${requestScope['error_cmpt']}" />
<c:set var="recup_login" value="${requestScope['recup_login']}" />
<c:set var="error_add_commande" value="${requestScope['error_add_commande']}" />
<c:set var="page_appelante" value="${requestScope['page_appelante']}" />
<c:set var="user" value="${sessionScope['utilisateur']}" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>- Tendr'Epis - Connexion -</title>
        <link rel="stylesheet" type="text/css" href="CSS/default.css" />
        <script src="JavaScript/AfficheDivCompte.js" type="text/javascript"></script>
        <script src="JavaScript/ClearTextBox.js" type="text/javascript"></script>
    </head>
    <body>
        <table id="tableau_01" cellpadding="0" cellspacing="0" align="center">
            <tr>
                <td>

                </td>
            </tr>
            <tr>
                <td id="menu_01">
                    <table id="tableau_02" cellpadding="0" cellspacing="0" align="center">
                        <tr>
                            <td id="logo">
                                <a href="index.jsp"><img src="Images/Tendre_Epis_03.png" title="" alt=""/></a>
                            </td>
                            <td class="image_compte_panier">
                                <img src="Images/Mon_Profil_01.png" title="Mon Profil" alt="Mon Profil"/>
                            </td>
                            <td class="item_compte_panier">
                                <div id="myAccount">
                                    <label class="title_compte" name="lbl_user">Mon Compte</label>       
                                </div>
                            </td>
                            <td class="image_compte_panier">
                                <a href=""><img src="Images/Mon_Panier_01.png" title="Mon Panier" alt="Mon Panier"/></a>
                            </td>
                            <td class="item_compte_panier">
                                <div id="conteneur_panier" onmouseover="affiche('myPanier');" onmouseout="cache_panier(event,'myPanier');" class="conteneur_panier_01">
                                    <a href="consultepanier" class="title_compte">Mon Panier</a>
                                    <br/>
                                    <div id="myPanier">                                        
                                        <c:choose>
                                            <c:when test="${(panier == null)}">
                                                Votre panier est vide !
                                                <div class="interligne_02"></div>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${(panier != null)}">
                                                <c:forEach var="pop" items="${panier.listArticlesPanier}">
                                                    <label name="lbl_produit">${pop.article.libelle}</label>
                                                    <br />
                                                    <label name="lbl_qte_produit">Quantité : ${pop.quantiteArticle}</label>
                                                    <div class="interligne_02"></div>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${((panier != null) && (panier.qteArticlesPanier > 1))}">
                                        <label name="lbl_article" class="title_compte">${panier.qteArticlesPanier} Articles</label>
                                        <br />
                                        <label name="prix_tot_panier" class="title_compte">${panier.prixTotalPanier}€</label>
                                    </c:when>
                                    <c:when test="${((panier != null) && (panier.qteArticlesPanier == 1))}">
                                        <label name="lbl_article" class="title_compte">${panier.qteArticlesPanier} Article</label>
                                        <br />
                                        <label name="prix_tot_panier" class="title_compte">${panier.prixTotalPanier}€</label>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${(panier == null)}">
                                        <label name="lbl_article" class="title_compte">0 Article 0,00€</label>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td id="menu_02">
                    <ul class="menu_deroulant">
                        <li>
                            <a href="viennoiseries_list">VIENNOISERIES</a>                            
                        </li>
                        <li class="disable_shadow">
                        </li>
                        <li>
                            <a href="sandwichs_list">SANDWICHS</a>                            
                        </li>
                        <li class="disable_shadow">
                        </li>
                        <li>
                            <a href="desserts_list">DESSERTS</a>                            
                        </li>
                        <li class="disable_shadow">
                        </li>
                        <li>
                            <a href="salades_list">SALADES</a>                            
                        </li>
                        <li class="disable_shadow">
                        </li>
                        <li class="deux_lignes">
                            <a href="sandwichs_perso_list">SANDWICHS PERSONNALISES</a>
                        </li>
                        <li class="disable_shadow">
                        </li>
                        <li class="deux_lignes">
                            <a href="salades_perso_list" >SALADES PERSONNALISEES</a>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td class="interligne_01">
                </td>
            </tr>
            <tr>
                <td class="contenu">
                    <a href="index.jsp" class="contenu_lien">Accueil</a> > <a href="connexion" class="contenu_lien">Connexion</a>
                </td>                
            </tr>
            <tr>
                <td class="interligne_02">
                </td>
            </tr>
            <tr>
                <td class="titre_contenu_page">
                    VEUILLEZ VOUS CONNECTER
                    <hr />
                </td>
            </tr>
            <c:choose>
                <c:when test="${(error_add_commande != null)}">
                    <tr>
                        <td class="error">
                            ${error_add_commande}
                        </td>
                    </tr>
                    <tr>
                        <td class="interligne_02">                                    
                        </td>
                    </tr>
                </c:when>
            </c:choose>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${(user == null)}">
                            <table cellpadding="0" cellspacing="0" align="center">
                                <form id="form_connexion" method="post" action="connexion">
                                    <tr>
                                        <td class="lbl_padding">                                    
                                            <label for="txtbox_identifiant" name="lbl_identifiant">Identifiant :</label>
                                        </td>
                                        <td>
                                            <input type="text" id="txtbox_identifiant" name="txtbox_id" value="${recup_login}" onfocus="clearTextBox(event);" onblur="clearTextBox(event);" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="interligne_02">                                    
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="lbl_padding">
                                            <label for="txtbox_mdp" name="lbl_mdp">Mot de Passe :</label>
                                        </td>
                                        <td>
                                            <input type="password" id="txtbox_mdp" name="txtbox_pwd" />
                                        </td>
                                    </tr>                                
                                        <c:choose>
                                            <c:when test="${((error_empty == null) && (error_auth == null) && (error_cmpt == null))}">                                                            
                                            </c:when>
                                            <c:when test="${error_empty != null}">
                                                <tr>
                                                    <td colspan="2" class="interligne_02">                                    
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="error">
                                                        ${error_empty}
                                                    </td>
                                                </tr>
                                            </c:when>
                                            <c:when test="${(error_auth != null)}">
                                                <tr>
                                                    <td colspan="2" class="interligne_02">                                    
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="error">
                                                        ${error_auth}
                                                    </td>
                                                </tr>                                            
                                            </c:when>
                                            <c:when test="${(error_cmpt != null)}">
                                                <tr>
                                                    <td colspan="2" class="interligne_02">                                    
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="error">
                                                        ${error_cmpt}
                                                    </td>
                                                </tr>                                               
                                            </c:when>
                                        </c:choose>
                                    <tr>
                                        <td colspan="2" class="interligne_02">                                    
                                        </td>
                                    </tr>                                                    
                                    <tr>
                                        <td colspan="2" class="button_padding">
                                            <c:choose>
                                                <c:when test="${(page_appelante != null)}">
                                                    <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="${page_appelante}" />
                                                </c:when>
                                            </c:choose>                                           
                                           <input type="submit" name="button_co" value="CONNEXION" class="button_connexion" /> 
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="interligne_02">                                    
                                        </td>
                                    </tr> 
                                </form>
                            </table>
                        </c:when>
                        <c:when test="${(user != null)}">
                            <% 
                                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                                rd.forward(request, response);
                            %>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </table>
    </body>
</html>
