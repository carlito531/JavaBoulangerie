<%-- 
    Document   : recapCommande
    Created on : 21 mai 2014, 14:15:39
    Author     : INF-PORT-CR2
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="panier" value="${sessionScope['monpanier']}"/>
<c:set var="clients" value="${requestScope['clients']}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="mobile/css/recapCommande.css" rel="stylesheet" media="all" type="text/css"> 
        <title>Récapitulatif commande</title>
    </head>
    <body>
        <h1>Récapitulatif de la commande</h1>

    <center>
        <div id="recap">
            <table>   
                <tr>
                    <th> Article </th>
                    <th> Infos </th> 
                    <th> Ajouter </th> 
                    <th> Supprimer </th> 
                </tr>

                <c:forEach var="p" items="${panier}">

                    <tr>                  
                    <form action="RecapCommande" method="POST">

                        <td> <input type="hidden" name="id_produit" value="${p.idProduit}"/> 
                            ${p.libelleArticle}
                        </td>
                        <td> Quantitée ${p.quantiteArticle} </br>
                            Prix unitaire ${p.prixArticle} Euros</br>
                            Prix total ${p.totalPrixArticle} Euros</br>
                        </td> 
                        <td> <input type="submit"  class="btn" name="btn_ajout" value="+"/>  </td>
                        <td> <input type="submit" class="btn" name="btn_suppr" value="-"/>  </td>

                    </form>
                    </tr> 

                </c:forEach> 
            </table>
        </div>
    </center>


    <!-- choix client pour la commande -->
    <div id="choixClient">
        <h2>Choix client</h2> </br>

        <form action="RecapCommande" method="POST">
            <select name="client">
                <c:forEach var="c" items="${clients}">
                     <option value="${c.id}"> ${c.nom} ${c.prenom} </option>
                </c:forEach>
            </select> 
            </br>
            </br>
            <input type="submit" class="btn_1" name="btn_valider" value="Commander"/> 
        </form>
    </div>

</body>
</html>
