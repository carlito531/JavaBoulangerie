<%-- 
    Document   : clients
    Created on : 24 janv. 2014, 17:32:35
    Author     : INF-PORT-CR2
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="client" value="${requestScope['clients']}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="mobile/css/clients.css" rel="stylesheet" media="all" type="text/css">
        <title>Gestion clients</title>
    </head>
    <body>
        <h1>Gestion des clients</h1>

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

    </br>

    <div id="menu">
        <!-- Affichage des Clients -->
        <table>     
            <tr> 
                <th> Réf. Client </th>
                <th> Nom </th> 
                <th> Prenom </th> 
                <th> Adresse </th> 
                <th> Code postal </th>
                <th> Ville </th>
                <th> Mail </th>
                <th> </th>

            </tr>  

            <c:forEach var="client" items="${client}">   

                <tr>                  
                <form action="GestionClient" method="POST">

                    <td> REF_${client.id}<input type="hidden" name="id_client" value="${client.id}"/> </td>
                    <td> ${client.nom} </td> 
                    <td> ${client.prenom}  </td>
                    <td> ${client.adresse}  </td>
                    <td> ${client.codePostal}  </td>
                    <td> ${client.ville}  </td>
                    <td> ${client.mail}  </td>
                    <td> <input type="submit" value="supprimer" name="btn_supprimer"></td>

                </form>
                </tr> 

            </c:forEach>
        </table>
    </div>

    </br>
    </br>
    
    <!-- séparation -->
    <div class="trait" />
    </br>
    
    <center>
        <!-- Formulaire d'ajout de client -->
        <div id="ajoutClient">

            <h2> Ajouter un client </h2>
            </br>
            <form action="GestionClient" method="POST">
                
                <table>

                    <tr>
                        <th> Login </th>
                        <th> <input type="text" name="login"> </th>
                    </tr>
                    <tr>
                        <th> Mot de passe </th>
                        <th> <input type="text" name="mdp"> </th>
                    </tr>
                    <tr>
                        <th> Nom </th>
                        <th> <input type="text" name="nom"> </th>
                    </tr>
                    <tr>
                        <th> Prenom </th>
                        <th> <input type="text" name="prenom"> </th>
                    </tr>
                    <tr>
                        <th> Adresse </th>
                        <th> <input type="text" name="adresse"> </th>
                    </tr>
                    <tr>
                        <th> Ville </th>
                        <th> <input type="text" name="ville"> </th>
                    </tr>
                    <tr>
                        <th> Code postal </th>
                        <th> <input type="text" name="cp"> </th>
                    </tr>
                    <tr>
                        <th> Adresse mail </th>
                        <th> <input type="text" name="mail"> </th>
                    </tr>
                    <tr>
                        <th> Type status </th>
                        <th> <input type="radio" name="statut"> Cuisinier </br>
                        <input type="radio" name="statut"> Boulanger </th>
                    </tr>
                </table>
                </br>
                <input type="submit" value="Ajouter" name="btn_ajouter">

            </form>
        </div>
    </center>
    
    

</body>
</html>
