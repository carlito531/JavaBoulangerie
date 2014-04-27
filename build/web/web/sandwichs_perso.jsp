<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="error_empty" value="${requestScope['error_empty']}" />
<c:set var="error_auth" value="${requestScope['error_auth']}" />
<c:set var="error_cmpt" value="${requestScope['error_cmpt']}" />
<c:set var="user" value="${sessionScope['utilisateur']}" />
<c:set var="panier" value="${sessionScope['panier']}" />
<c:set var="error_checkbox_pain" value="${requestScope['error_checkbox_pain']}" />
<c:set var="list_ingredients_pain" value="${requestScope['list_ingredients_pain']}" />
<c:set var="error_checkbox_main_elem" value="${requestScope['error_checkbox_main_elem']}" />
<c:set var="list_ingredients_poisson" value="${requestScope['list_ingredients_poisson']}" />
<c:set var="list_ingredients_charcuterie" value="${requestScope['list_ingredients_charcuterie']}" />
<c:set var="error_checkbox_legume" value="${requestScope['error_checkbox_legume']}" />
<c:set var="list_ingredients_legume" value="${requestScope['list_ingredients_legume']}" />
<c:set var="error_checkbox_sauce" value="${requestScope['error_checkbox_sauce']}" />
<c:set var="list_ingredients_sauce" value="${requestScope['list_ingredients_sauce']}" />
<c:set var="error_checkbox_fromage" value="${requestScope['error_checkbox_fromage']}" />
<c:set var="list_ingredients_fromage" value="${requestScope['list_ingredients_fromage']}" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>- Tendr'Epis - Sandwichs Personnalisés -</title>
        <link rel="stylesheet" type="text/css" href="CSS/default.css" />
        <script src="JavaScript/AfficheDivCompte.js" type="text/javascript"></script>
        <script src="JavaScript/ClearTextBox.js" type="text/javascript"></script>
        <script src="JavaScript/CheckQuantite.js" type="text/javascript"></script>
    </head>
    <body onload="uncheckedRadioButton()">
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
                                <form id="form_deconnexion" method="post" action="deconnexion">
                                    <c:choose>
                                        <c:when test="${(user!=null)}">
                                            <div id="myAccount">
                                                <label class="title_compte" name="lbl_user_nom">${user.nom}</label>
                                                <br />
                                                <label class="title_compte" name="lbl_user_prenom">${user.prenom}</label>
                                                <br />
                                                <input type="submit" class="button_deconnexion" name="button_deco" value="DECONNEXION" />
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </form>                                
                                <form id="form_consulte_commande" method="post" action="consultecommande">
                                    <c:choose>
                                        <c:when test="${(user!=null)}">                                            
                                            <input type="submit" class="button_deconnexion" name="consult_com" value="MES COMMANDES" />
                                        </c:when>
                                    </c:choose>
                                </form>
                                <form id="form_connexion" method="post" action="connexion">
                                    <c:choose>
                                        <c:when test="${(user==null)}">
                                            <div id="conteneur" onmouseover="affiche('compte');" onmouseout="cache(event,'compte');" class="conteneur_01">
                                                <label class="title_compte" name="lbl_user">Mon compte</label>
                                                <br />                              
                                                <div id="compte">                                        
                                                    Connexion
                                                    <div class="interligne_02"></div>
                                                    <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="sandwichs_perso_list" />
                                                    <input type="text" id="txtbox_identifiant" name="txtbox_id" onfocus="clearTextBox(event);" onblur="clearTextBox(event);" value="Identifiant..." />
                                                    <div class="interligne_02"></div>
                                                    <input type="password" id="txtbox_mdp" name="txtbox_pwd" />
                                                    <div class="interligne_02"></div>
                                                    <input type="submit" value="CONNEXION" name="button_co" class="button_connexion" />
                                                    <div class="interligne_02"></div>
                                                    <c:choose>
                                                        <c:when test="${((error_empty == null) && (error_auth == null) && (error_cmpt == null))}">                                                            
                                                        </c:when>
                                                        <c:when test="${error_empty != null}">
                                                            <div class="error">${error_empty}</div>
                                                        </c:when>
                                                        <c:when test="${(error_auth != null)}">
                                                            <div class="error">${error_auth}</div>                                               
                                                        </c:when>
                                                        <c:when test="${(error_cmpt != null)}">
                                                            <div class="error">${error_cmpt}</div>                                               
                                                        </c:when>
                                                    </c:choose>
                                                    <div class="interligne_02"></div>
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </form>
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
                    <a href="index.jsp" class="contenu_lien">Accueil</a> > <a href="sandwichs_perso_list" class="contenu_lien">Sandwichs Personnalisés</a>
                </td>                
            </tr>
            <tr>
                <td class="interligne_02">
                </td>
            </tr>
            <tr>
                <td class="titre_contenu_page">
                    CREEZ VOTRE SANDWICH PERSONNALISE
                    <hr />
                </td>
            </tr>            
            <tr>
                <td>
                    <form id="form_ajout_ingredient" method="post" action="ajoutpaniersandwichperso"> 
                        <table id="tableau_ingredient" cellpadding="0" cellspacing="0" align="center">
                            <tr>
                                <td colspan="2" class="title_step">
                                    <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="sandwichs_perso_list" />
                                    1 : Choisissez le pain
                                    <hr />
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${(error_checkbox_pain != null)}">
                                    <tr>
                                        <td colspan="2">
                                            <label name="error_cbox_pain" class="error">${error_checkbox_pain}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="interligne_02" colspan="2">                                            
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            <tr>
                                <td colspan="2" class="ingredient">
                                    <table id="tableau_pain" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_pain}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_pain" type="checkbox" value="opt_pain_${ing.identifiant}" onclick="optSelectedMaxOne(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>                                            
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="title_step">
                                    2 : Choisissez l'élément principal
                                    <hr />
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${(error_checkbox_main_elem != null)}">
                                    <tr>
                                        <td colspan="2">
                                            <label name="error_cbox_main_elem" class="error">${error_checkbox_main_elem}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="interligne_02" colspan="2">                                            
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            <tr>
                                <td class="title_ingredient">
                                    <input name="main_element" type="radio" value="main_element_poisson" onclick="enableOptMainElem('tableau_poisson', 'tableau_charcuterie');" /> Poisson <span class="title_step_ital">(Max. 2)</span>
                                    <hr />
                                </td>
                                <td class="title_ingredient">
                                    <input name="main_element" type="radio" value="main_element_charcuterie" onclick="enableOptMainElem('tableau_charcuterie', 'tableau_poisson');" /> Charcuterie <span class="title_step_ital">(Max. 2)</span>
                                    <hr />
                                </td>
                            </tr>
                            <tr>
                                <td class="ingredient">
                                    <table id="tableau_poisson" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                            <td class="tableau_ingredient_header">
                                               Quantité 
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_poisson}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_poisson" type="checkbox" value="opt_poisson_${ing.identifiant}" onclick="optSelectedMaxTwo(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>    
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                          
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input id="my_qte_opt_poisson_${ing.identifiant}" name="qte_opt_poisson_${ing.identifiant}" type="text" value="1" class="qte_ingredient" maxlength="1" onkeyup="CheckQteIngredient(this);" />                           
                                                        </c:when>
                                                    </c:choose>
                                                </td>  
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                                <td class="ingredient">
                                    <table id="tableau_charcuterie" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                            <td class="tableau_ingredient_header">
                                               Quantité 
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_charcuterie}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_charcu" type="checkbox" value="opt_charcu_${ing.identifiant}" onclick="optSelectedMaxTwo(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>    
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                          
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input id="my_qte_opt_charcu_${ing.identifiant}" name="qte_opt_charcu_${ing.identifiant}" type="text" value="1" class="qte_ingredient" maxlength="1" onkeyup="CheckQteIngredient(this);" />                           
                                                        </c:when>
                                                    </c:choose>
                                                </td>  
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="title_step">
                                    <span class="title_step_opt">(Optionnel)</span> 3 : Choisissez la garniture <span class="title_step_ital">(Max. 2)</span>
                                    <hr />
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${(error_checkbox_legume != null)}">
                                    <tr>
                                        <td colspan="2">
                                            <label name="error_cbox_legume" class="error">${error_checkbox_legume}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="interligne_02" colspan="2">                                            
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            <tr>
                                <td colspan="2" class="ingredient">
                                    <table id="tableau_legume" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                            <td class="tableau_ingredient_header">
                                               Quantité 
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_legume}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_legume" type="checkbox" value="opt_legume_${ing.identifiant}" onclick="optSelectedMaxTwo(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>    
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                          
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input id="my_qte_opt_legume_${ing.identifiant}" name="qte_opt_legume_${ing.identifiant}" type="text" value="1" class="qte_ingredient" maxlength="1" onkeyup="CheckQteIngredient(this);" />                           
                                                        </c:when>
                                                    </c:choose>
                                                </td>  
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="title_step">
                                    <span class="title_step_opt">(Optionnel)</span> 4 : Choisissez la sauce <span class="title_step_ital">(Max. 2)</span>
                                    <hr />
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${(error_checkbox_sauce != null)}">
                                    <tr>
                                        <td colspan="2">
                                            <label name="error_cbox_sauce" class="error">${error_checkbox_sauce}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="interligne_02" colspan="2">                                            
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            <tr>
                                <td colspan="2" class="ingredient">
                                    <table id="tableau_sauce" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                            <td class="tableau_ingredient_header">
                                               Quantité 
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_sauce}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_sauce" type="checkbox" value="opt_sauce_${ing.identifiant}" onclick="optSelectedMaxTwo(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>    
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                          
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input id="my_qte_opt_sauce_${ing.identifiant}" name="qte_opt_sauce_${ing.identifiant}" type="text" value="1" class="qte_ingredient" maxlength="1" onkeyup="CheckQteIngredient(this);" />                           
                                                        </c:when>
                                                    </c:choose>
                                                </td>  
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="title_step">
                                    <span class="title_step_opt">(Optionnel)</span> 5 : Choisissez le fromage <span class="title_step_ital">(Max. 2)</span>
                                    <hr />
                                </td>
                            </tr>
                            <c:choose>
                                <c:when test="${(error_checkbox_fromage != null)}">
                                    <tr>
                                        <td colspan="2">
                                            <label name="error_cbox_fromage" class="error">${error_checkbox_fromage}</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="interligne_02" colspan="2">                                            
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            <tr>
                                <td colspan="2" class="ingredient">
                                    <table id="tableau_fromage" cellpadding="0" cellspacing="0" align="center">
                                        <tr>
                                            <td class="tableau_ingredient_header">
                                                
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Désignation
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Prix
                                            </td>
                                            <td class="tableau_ingredient_header">
                                                Stock
                                            </td>
                                            <td class="tableau_ingredient_header">
                                               Quantité 
                                            </td>
                                        </tr>
                                        <c:forEach var="ing" items="${list_ingredients_fromage}">
                                            <tr>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                                                       
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input name="opt_fromage" type="checkbox" value="opt_fromage_${ing.identifiant}" onclick="optSelectedMaxTwo(this);" />
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.libelle}
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    ${ing.prix} €
                                                </td>
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                                                    
                                                            <label name="stock_ingre" class="indispo_ingredient">Indisponible</label>                            
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <label name="stock_ingre" class="dispo_ingredient">Disponible</label>                            
                                                        </c:when>
                                                    </c:choose>
                                                </td>    
                                                <td class="tableau_ingredient_cell">
                                                    <c:choose>
                                                        <c:when test="${(ing.stock == 0)}">                          
                                                        </c:when>
                                                        <c:when test="${(ing.stock > 0)}">                                                    
                                                            <input id="my_qte_opt_fromage_${ing.identifiant}" name="qte_opt_fromage_${ing.identifiant}" type="text" value="1" class="qte_ingredient" maxlength="1" onkeyup="CheckQteIngredient(this);" />                           
                                                        </c:when>
                                                    </c:choose>
                                                </td>  
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>                            
                            <tr>
                                <td colspan="2">
                                    <hr />                                    
                                </td>
                            </tr>
                            <tr>
                                <td>                                    
                                </td>
                                <td>
                                    <table id="tableau_button" cellspacing="0" cellpadding="0" align="right">
                                        <tr>
                                            <td class="button_on_right">
                                                <input type="submit" name="reset" value="ANNULER" class="button_ajouter" onclic="ResetDisplay();" />                                          
                                            </td>
                                            <td class="button_on_left">
                                                <input type="submit" name="addSandwich" value="AJOUTER MON SANDWICH AU PANIER" class="button_ajouter" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <hr />                                    
                                </td>
                            </tr>
                        </table> 
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>