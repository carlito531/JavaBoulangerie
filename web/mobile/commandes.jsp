<%-- 
    Document   : commandes
    Created on : 13 janv. 2014, 14:02:21
    Author     : INF-PORT-CR2
--%>

<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="commandes" value="${requestScope['commandes']}"/>

<!DOCTYPE html>
<html>
    <head>
        <link href="mobile/css/commandes.css" rel="stylesheet" media="all" type="text/css"> 
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Affichage des commandes</title>
    </head>
    <body>
        <h1>Etat des commandes </h1>

    <center><img src="Images/Tendre_Epis_03_detoure.png"/></center>
    
     <!-- bouton-image de retour vers accueil -->
    <form action="RetourAccueil" method="POST">
    <input type=image border=0 src="Images/home.png" Value=submit align="middle" name="btn_accueil" 
            style="position:absolute;
                   top:5%;
                   border:none;
                   background-color:inherit;
                   right:10px;"/>    
    </form>

    <div id="menu">
        <!-- Affichage des commandes -->
        <table>     
            <tr> 
                <th> Ref. Commande </th> 
                <th> Date changement statut </th> 
                <th> Date de livraison </th> 
                <th> Prix </th>
                <th> Statut </th>
                <th> Détail commande </th>
            </tr> 


            <c:forEach var="commande" items="${requestScope['commandes']}">   

                <tr>                  
                <form action="DetailCommande" method="POST">

                    <td> REF_${commande.id}<input type="hidden" name="id_commande" value="${commande.id}"/> </td>
                    <td> ${commande.chgStatut} </td> 
                    <td> ${commande.dateLivraison}  </td>
                    <td> ${commande.prixTotale}  </td> 
                    <td> ${commande.statut}  </td>
                    <td>
                      <!-- redirige vers la servlet DetailCommande pour afficher les détails-->
                      <input type="submit" value="modifier"> 
                </form>
                </td>   
                </tr> 

            </c:forEach>

        </table> 

    </div>

</body>
</html>
