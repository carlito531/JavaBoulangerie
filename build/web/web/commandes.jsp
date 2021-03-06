<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="error_empty" value="${requestScope['error_empty']}" />
<c:set var="error_auth" value="${requestScope['error_auth']}" />
<c:set var="error_cmpt" value="${requestScope['error_cmpt']}" />
<c:set var="user" value="${sessionScope['utilisateur']}" />
<c:set var="panier" value="${sessionScope['panier']}" />
<c:set var="list_commandes" value="${requestScope['list_commandes']}" />
<c:set var="my_commande" value="${requestScope['my_commande']}" />
<c:set var="url" value="${requestScope['url']}" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>- Tendr'Epis - Mes Commandes -</title>
        <link rel="stylesheet" type="text/css" href="CSS/default.css" />
        <script src="JavaScript/AfficheDivCompte.js" type="text/javascript"></script>
        <script src="JavaScript/ClearTextBox.js" type="text/javascript"></script>
        <script src="JavaScript/CheckQuantite.js" type="text/javascript"></script>
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
                                                    <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="consultecommande" />
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
                    <a href="index.jsp" class="contenu_lien">Accueil</a> > <a href="consultecommande" class="contenu_lien">Mes Commandes</a>
                </td>                
            </tr>
            <tr>
                <td class="interligne_02">
                </td>
            </tr>
            <tr>
                <td class="titre_contenu_page">
                    VOS COMMANDES
                    <hr />
                </td>
            </tr>
            <c:choose>
                <c:when test="${(user == null)}">
                    <tr>
                        <td class="error_list_commandes_vide">
                            <label name="error_commande">Vous devez vous connecter pour consulter vos commandes !</label>
                        </td>
                    </tr>
                    <tr>
                        <td class="interligne_02">                                            
                        </td>
                    </tr>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${((list_commandes == null) && (my_commande == null) && (user != null))}">
                    <tr>
                        <td class="error_list_commandes_vide">
                            <label name="error_commande">Vous n'avez passé aucune commande !</label>
                        </td>
                    </tr>
                    <tr>
                        <td class="interligne_02">                                            
                        </td>
                    </tr>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${(list_commandes != null)}">
                    <tr>
                        <td>
                            <table id="tableau_mes_commandes" cellpadding="0" cellspacing="0" align="center">
                                <tr>
                                    <td class="header_text">
                                        Identifiant
                                    </td>
                                    <td class="header_text">
                                        Date de création
                                    </td>
                                    <td class="header_text">
                                        Statut
                                    </td>
                                    <td class="header_text">
                                        Date de Livraison
                                    </td>
                                    <td class="header_text">
                                        Prix Total
                                    </td>
                                    <td class="header_sans_bord">
                                         
                                    </td>
                                </tr>
                                <c:forEach var="c" items="${list_commandes}">
                                    <tr>
                                        <td class="cell_text">
                                            ${c.identifiant}
                                        </td>
                                        <td class="cell_text">
                                            ${c.dateCreation}
                                        </td>
                                        <td class="cell_text">
                                            ${c.statut.libelle}
                                        </td>
                                        <td class="cell_text">
                                            ${c.dateLivraison}
                                        </td>
                                        <td class="cell_text">
                                            ${c.prixTotal}€
                                        </td>
                                        <td class="cell_text_sans_bord">
                                            <form id="form_consulte_commande_${c.identifiant}" method="post" action="consultemycommande">
                                                <input type="text" name="id_commande" class="hidden_txtbox_id_produit" value="${c.identifiant}" />
                                                <input type="text" name="prix_tot_commande" class="hidden_txtbox_id_produit" value="${c.prixTotal}" />
                                                <input type="submit" name="consulte_my_commande" value="CONSULTER CETTE COMMANDE" class="button_commande" />
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="interligne_02">
                        </td>
                    </tr>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${(my_commande != null)}">
                    <tr>
                        <td>
                            <form id="form_consulte_commande_bis" method="post" action="consultecommande">
                                <input type="submit" name="consulte_commande_bis" value="REVENIR A LA LISTE DE MES COMMANDES" class="button_retour_commande" />
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td class="interligne_02">                                            
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table id="tableau_commande" cellpadding="0" cellspacing="0" align="center">
                                <tr>
                                    <td>
                                        
                                    </td>
                                    <td class="header_text">
                                        Désignation
                                    </td>
                                    <td class="header_text">
                                        Prix Unitaire
                                    </td>
                                    <td class="header_text">
                                        Quantité
                                    </td>
                                    <td class="header_text">
                                        Sous-Total
                                    </td>
                                </tr>
                                <c:forEach var="p" items="${my_commande.listArticlesPanier}" varStatus="status">
                                    <tr>
                                        <td class="cell_img">
                                            <img src="${p.article.urlImage}" alt="" title="" />
                                        </td>
                                        <td class="cell_text_top">
                                            ${p.article.libelle}                                            
                                            <c:choose>
                                                <c:when test="${(p.listIngredientOfProduct != null)}">
                                                    <div class="list_ingr">
                                                        <table class="tableau_ingr" cellpadding="0" cellspacing="0" align="left">
                                                           <c:forEach var="iop" items="${p.listIngredientOfProduct}">
                                                                <tr>
                                                                    <td class="list_ingr_cell">
                                                                        ${iop.quantiteIngredient} x
                                                                    </td>
                                                                    <td class="list_ingr_cell_lib">
                                                                        ${iop.ingredient.libelle}
                                                                    </td>
                                                                    <td class="list_ingr_cell">
                                                                        ${iop.ingredient.prix}€ =
                                                                    </td>
                                                                    <td class="list_ingr_cell">
                                                                        ${iop.totalPrixIngredient}€
                                                                    </td>
                                                                </tr>
                                                           </c:forEach>
                                                        </table>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="cell_text_top_bgc">                                                
                                            <c:choose>
                                                <c:when test="${(p.article.prix != '')}">
                                                    ${p.article.prix}€
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="cell_text_top">
                                            <input type="text" name="quantite_produit" value="${p.quantiteArticle}" class="qte_produit" maxlength="2" readOnly="true"/>
                                        </td>
                                        <td class="cell_text_top_bgc">
                                            ${p.totalPrixArticle}€
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="3">

                                    </td>
                                    <td class="total_panier">
                                        Total
                                    </td>
                                    <td class="total_prix_panier">
                                        ${my_commande.prixTotalPanier}€
                                    </td>
                                </tr>  
                                <tr>
                                    <td class="interligne_02" colspan="5">
                                        <hr />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </table>
    </body>
</html>
