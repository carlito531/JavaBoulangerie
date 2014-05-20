<%-- 
    Document   : detailCommande
    Created on : 16 janv. 2014, 11:07:26
    Author     : INF-PORT-CR2
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="commande" value="${requestScope['commande']}"/>
<c:set var="client" value="${requestScope['client']}"/>
<c:set var="utilisateur" value="${sessionScope['utilisateur']}"/>
<c:set var="ligneCommande" value="${requestScope['ligneCommande']}"/>
<c:set var="produits" value="${requestScope['produits']}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="mobile/css/detailCommande.css" rel="stylesheet" media="all" type="text/css"> 
        <title>Affichage du détail de la commande</title>
    </head>
    <body>
        <h1>Détail commande</h1>

        <!-- bouton-image de retour vers accueil -->
        <form action="RetourAccueil" method="POST">
            <input type=image border=0 src="Images/home.png" Value=submit align="middle" name="btn_accueil" 
                   style="position:absolute;
                   bottom:5%;
                   border:none;
                   background-color:inherit;
                   right:10px;"/>    
        </form>

        <div id="conteneur"> 
            <div id ="modifier_commande">

                <form action="DetailCommande" method="POST">
                    &nbsp <input type="hidden" value="${commande.id}" name="id_commande"><b>Réf. commande : </b>${commande.id}</br>
                    &nbsp <b>Statut : </b>${commande.statut}
                    &nbsp 
                    <c:choose>
                        <c:when test="${utilisateur.cuisinier}">
                            <select name="choixStatut">
                                <option value="En attente"> En attente </option>
                                <option value="En préparation"> En préparation </option>
                                <option value="Terminée"> Terminée </option>
                            </select>
                        </c:when>
                        <c:when test="${utilisateur.boulanger}">
                            <select name="choixStatut">
                                <option value="Livrée"> Livrée </option>  
                            </select>
                        </c:when>
                    </c:choose>
                    </br> 
                    &nbsp <b>Date changement statut : </b>${commande.chgStatut} </br>
                    &nbsp <b>Date livraison commande : </b>${commande.dateLivraison} </br>
                    &nbsp <b>Prix totale commande : </b>${commande.prixTotale}</br>   
                    </br> 
                    </br>
                    &nbsp
                    <input type="submit" value="Modifier" name="modifStatut" style="width:100px; height: 100px; border-radius:50%; position:absolute; right:2%; top:2%;">    
                </form>

                <center>
                    <div id ="produits">
                        <c:forEach var="ligne" items="${requestScope['ligneCommande']}">
                            <div class="produits_box">
                                <center>
                                    <img src="${ligne.produit.image}"></br>
                                    Quantitée : ${ligne.quantite}  </br>
                                    Prix totale : ${ligne.prixTotale}  </br>
                                    Libelle : ${ligne.produit.libelle} </br>
                                    Statut :  ${ligne.produit.statut} </br>
                                    Stock: ${ligne.produit.stock}
                                </center>
                            </div>
                        </c:forEach>  
                    </div>
                </center>


                </br>
                <h2>Informations client</h2></br>

                &nbsp <b>Nom :</b> ${client.nom} </br>
                &nbsp <b> Prenom :</b> ${client.prenom} </br>
                &nbsp <b> Adresse :</b> ${client.adresse} </br>
                &nbsp <b> Code postal :</b> ${client.codePostal} </br>
                &nbsp <b> Ville :</b> ${client.ville} </br>
                &nbsp <b> Adresse mail :</b> ${client.mail} </br>
                </br>
            </div> 
        </div>       

    </body>
</html>
