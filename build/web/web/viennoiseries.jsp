<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:set var="error_empty" value="${requestScope['error_empty']}" />
<c:set var="error_auth" value="${requestScope['error_auth']}" />
<c:set var="error_cmpt" value="${requestScope['error_cmpt']}" />
<c:set var="user" value="${sessionScope['utilisateur']}" />
<c:set var="list_viennoiseries" value="${requestScope['list_viennoiseries']}" />
<c:set var="panier" value="${sessionScope['panier']}" />
<c:set var="errorQte" value="${requestScope['error_qte']}" />
<c:set var="ligneErrorQte" value="${requestScope['ln_error_qte']}" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>- Tendr'Epis - Viennoiseries -</title>
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
                                                    <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="viennoiseries_list" />
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
                    <a href="index.jsp" class="contenu_lien">Accueil</a> > <a href="viennoiseries_list" class="contenu_lien">Viennoiseries</a>
                </td>                
            </tr>
            <tr>
                <td class="interligne_02">
                </td>
            </tr>
            <tr>
                <td class="titre_contenu_page">
                    NOS VIENNOISERIES
                    <hr />
                </td>
            </tr>            
            <tr>
                <td>
                    <table cellpadding="0" cellspacing="0" align="center">
                            <tr>
                                <td>

                                </td>
                                <td class="header_text">
                                    Produit
                                </td>
                                <td class="header_text">
                                    Prix
                                </td>
                                <td class="header_text">
                                    Quantité
                                </td>
                                <td class="header_text">
                                    Stock
                                </td>
                                <td>

                                </td>
                            </tr>
                            <c:forEach var="p" items="${list_viennoiseries}">
                                <tr>
                                    <td class="cell_text">
                                        <img src="${p.urlImage}" alt="" title="" />
                                    </td>
                                    <td class="cell_text">
                                        ${p.libelle}
                                    </td>
                                    <td class="cell_text">
                                        ${p.prix} €
                                    </td>
                                    <td class="cell_text">
                                        <c:set var="id_form" value="form_ajout_${p.identifiant}" />
                                        <form id="${id_form}" method="post" action="ajoutpanier">
                                            <input type="text" name="id_produit" class="hidden_txtbox_id_produit" value="${p.identifiant}" />
                                            <input type="text" name="page_name" class="hidden_txtbox_id_produit" value="viennoiseries_list" />
                                            <c:choose>
                                                <c:when test="${(p.stock == 0)}">                                                    
                                                </c:when>
                                                <c:when test="${(p.stock > 0)}">
                                                    <input type="text" name="quantite_produit" value="1" class="qte_produit" maxlength="2" onkeyup="CheckIsNumber(this);" />
                                                </c:when>
                                            </c:choose>                                                                                       
                                            <c:choose>
                                                <c:when test="${(errorQte == null)}">
                                                </c:when>
                                                <c:when test="${((errorQte != null) && (ligneErrorQte.equals(p.identifiant)))}">
                                                    <br/><label name="lbl_erreur_qte" class="erreur_quantite">${errorQte}</label>
                                                </c:when>
                                            </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${(p.stock == 0)}">
                                                <label name="stock_produit" class="indispo_produit">Indisponible</label>
                                            </c:when>
                                            <c:when test="${(p.stock > 0)}">
                                                <label name="stock_produit" class="dispo_produit">Disponible</label>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td class="cell_text">
                                            <c:choose>
                                                <c:when test="${(p.stock == 0)}">                                                    
                                                </c:when>
                                                <c:when test="${(p.stock > 0)}">
                                                    <input type="submit" name="ajout_produit" value="AJOUTER AU PANIER" class="button_ajouter">
                                                </c:when>
                                            </c:choose>                                            
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>