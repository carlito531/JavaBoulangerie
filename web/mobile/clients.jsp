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

    <center>
        <!-- Formulaire d'ajout de client -->
        <div id="ajoutClient">

            <h2> Ajouter un client </h2>
            </br>
            <form action="GestionClient" method="POST">

                <input type="text" name="login" value="Login"> </br></br>
                <input type="text" name="mdp" value="Mot de passe"> </br></br>
                <input type="text" name="nom" value="Nom"> </br></br>
                <input type="text" name="prenom" value="Prenom"> </br></br>
                <input type="text" name="adresse" value="Adresse"> </br></br>
                <input type="text" name="ville" value="Ville"> </br></br>
                <input type="text" name="cp" value="Code Postal"> </br></br>
                <input type="text" name="mail" value="Mail"> </br></br>
                <input type="radio" name="statut" value="estCuisinier">&nbsp Cuisinier</br></br>
                <input type="radio" name="statut" value="estBoulanger">&nbsp Boulanger</br></br>

                <input type="submit" value="Ajouter" name="btn_ajouter">  </br></br>
            </form>
        </div>
    </center>

</body>
</html>
