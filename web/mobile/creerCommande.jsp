<%-- 
    Document   : creerCommande
    Created on : 29 janv. 2014, 10:49:46
    Author     : INF-PORT-CR2
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="categoriesProduit" value="${requestScope['categoriesProduit']}"/>
<c:set var="produits" value="${requestScope['produits']}"/>
<c:set var="panier" value="${sessionScope['monpanier']}"/>
<c:set var="erreur" value="${requestScope['produitIndisponible']}"/>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="mobile/css/creerCommande.css" rel="stylesheet" media="all" type="text/css">
        <script src="mobile/js/fonctions.js" type="text/javascript"></script>
        <title>Passer une commande pour le client</title>
    </head>
    <body>

        <!-- bouton-image de retour vers accueil -->
        <form action="RetourAccueil" method="POST">
            <input type=image border=0 src="Images/home.png" Value=submit align="middle" name="btn_accueil" 
                   style="position:absolute;
                   bottom:5%;
                   border:none;
                   background-color:inherit;
                   left:5%;
                   z-index:1"/>    
        </form>

    <center> 
        <!-- affichage des catégories -->
        <div id="categories">
            <c:forEach var="categorie" items="${requestScope['categoriesProduit']}">
                <div class="categories_box">
                    <form action="CreerCommande" method="POST">
                        <input type="hidden" value="${categorie.id}" name="id_categorie"/>
                        <center><input type="submit" value="${categorie.libelle}" name="btn_categorie"
                                       style="border:none;
                                       background-color:peru;
                                       -webkit-appearance: none;
                                       color:white;
                                       font-size:20px;"/>
                        </center>
                    </form>
                </div>  
            </c:forEach> 
        </div>
    </center>

    <div id="conteneur">
        <form action="Deconnexion" method="POST">
            <input type=image border=0 src="Images/annulation.png" Value=submit align="middle" name="btn_deconnexion" style="
                   position:absolute;
                   top:0;
                   border:none;
                   background-color:inherit;
                   right:10px;"/>
        </form>

        <center>
            <!-- affichage des produits par catégories -->
            <div id="produits">
                <c:forEach var="produit" items="${requestScope['produits']}">
                    <div class="produits_box">
                        <form action="GestionPanier" method="POST">
                            <input type="hidden" value="${produit.id}" name="id_produit"/>
                            <input type=image border=0 src="${produit.image}" Value=submit align="middle" name="btn_produit"/></br>
                            ${produit.libelle}</br>
                            <c:choose>
                                <c:when test="${produit.statut == 'Disponible'}">
                                    <p style="color:green;"> ${produit.statut}</p>
                                </c:when>
                                <c:when test="${produit.statut == 'Indisponible'}">
                                    <p style="color:red;"> ${produit.statut}</p>
                                </c:when>
                            </c:choose>                       
                            ${produit.prix}&nbsp Euros </br>
                        </form>
                    </div>
                </c:forEach>

        </center>

        <!-- affichage des produits dans le panier -->
        <div id="panier">
            <div id="conteneur_panier_box">
                <c:choose>
                    <c:when test="${panier != null}">
                        <center>
                            <c:forEach var="panier" items="${sessionScope['monpanier']}">
                                <div class="panier_box">
                                    ${panier.libelleArticle}</br>
                                    ${panier.quantiteArticle}</br>                           
                                    ${panier.totalPrixArticle}</br>
                                </div>
                            </c:forEach>
                            <form action="RecapCommande" method="POST">
                                <center>
                                    <input type="submit" value="Valider" name="btn_validation" style="width:70px;
                                           height:70px;
                                           border-radius:50%;
                                           background-color:peru;
                                           -webkit-appearance: none;
                                           color:wheat;"/>
                                </center>
                            </form>
                        </center>
                    </c:when>
                    <c:when test="${panier == null}">
                        Le panier est vide !
                    </c:when>
                </c:choose>

                <!-- affichage une Alert javaScript si le produit est indisponible -->
                <c:choose>
                    <c:when test="${erreur != null}">
                        <script>
                            MsgBox("${erreur}");
                        </script> 
                    </c:when>     
                </c:choose> 

            </div>
        </div>
    </div>
</div>
</body>
</html>
