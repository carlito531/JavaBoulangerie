<%-- 
    Document   : dashboard
    Created on : 8 janv. 2014, 18:33:09
    Author     : INF-PORT-CR2
--%>


<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="status" value="${sessionScope['utilisateur']}" />

<!DOCTYPE html>

<html>
    <head>
        <link href="mobile/css/dashboard.css" rel="stylesheet" media="all" type="text/css"> 
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        
        <title>Affichage des commandes</title>
    </head>
    <body>
        <h1>Bienvenue ${sessionScope['utilisateur'].prenom}  ${sessionScope['utilisateur'].nom}</h1>
        
        <center><img src="Images/Tendre_Epis_03_detoure.png"></center>
            
        <c:choose>
            <c:when test="${status.boulanger}">
                
            <!-- Accès à la dashboard -->
            <center><h3>Dashboard</h3></center>
            
            <div id ="menu">
                   <center>
                   <div class='menu_block'>
                       <h2>Commandes</h2>
                       </br>
                       <a href="GestionCommande"> <img src="Images/order_icon.png"></a>
                   </div>                  
                   <div class='menu_block'>
                       <h2>Stocks</h2>
                       </br>
                      <a href="GestionStock"> <img src="Images/stock_icon.png"></a>
                   </div>
                    <div class='menu_block'>
                       <h2>Gérer Clients</h2>
                       </br>
                      <a href="GestionClient"> <img src="Images/clients_icon.png"></a>
                   </div>
                    <div class='menu_block'>
                       <h2>Passer Commande</h2>
                       </br>
                      <a href="CreerCommande"> <img src="Images/panier_icon.png"></a>
                   </div>
                   </center>
               </div>
            </c:when>
            <c:when test="${status.cuisinier}">
                <!-- Accès à la dashboard -->
            <center><h3>Dashboard</h3></center>
            
            <div id ="menu">
                   <center>
                   <div class='menu_block'>
                       <h2>Commandes</h2>
                       </br>
                       <a href="GestionCommande"> <img src="Images/order_icon.png"></a>
                   </div>
                   <div class='menu_block'>
                       <h2>Stocks</h2>
                       </br>
                      <a href="GestionStock"> <img src="Images/stock_icon.png"></a>
                   </div>
                   </center>
            </div>                  
            </c:when>
           <c:otherwise>
               Accès non autorisé !
           </c:otherwise>
        </c:choose>
               
    </body>
</html>