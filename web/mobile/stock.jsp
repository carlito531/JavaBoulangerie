<%-- 
    Document   : stock
    Created on : 14 janv. 2014, 20:36:49
    Author     : INF-PORT-CR2
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ingredients" value="${requestScope['ingredients']}"/>
<c:set var="produits" value="${requestScope['produits']}"/>
<!DOCTYPE html>
<html>
    <head>
        <link href="mobile/css/stock.css" rel="stylesheet" media="all" type="text/css"> 
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Gestion des stock</h1>

    <center><img src="Images/Tendre_Epis_03_detoure.png" /> </center>

    <div id="menu">
        <!-- Affichage des ingrédients -->
        <h2> Ingredients </h2> 
        <table>     
            <tr> 
                <th></th>
                <th> Libellé </th> 
                <th> Statut </th> 
                <th> Quantité </th> 
                <th> Prix </th>
                <th> Ajouter/Supprimer</th>
            </tr> 

            <c:forEach var="ingredient" items="${requestScope['ingredients']}">   

                <form action="GestionStock" method="POST">
                    <tr>
                        <td  style="width:20px;"><input type="hidden" value="${ingredient.identifiant}" name="id_ingredient"/></td>
                        <td> ${ingredient.libelle} </td>
                        <td> ${ingredient.statut} </td> 
                        <td> ${ingredient.stock} </td>
                        <td> ${ingredient.prix} </td>
                        <td> <input type="text" name="txt_stockIngredient" style="width:40px;"/>
                            &nbsp<input type="submit" name="btn_ajoutIngredient" value="+"/>
                            &nbsp<input type="submit" name="btn_supprimerIngredient" value="-"/>
                        </td>
                    </tr>  
                </form>
            </c:forEach>
        </table>

        </br>

        <h2> Produits </h2>
        <table>     
            <tr> 
                <th></th>
                <th> Libellé </th> 
                <th> Statut </th> 
                <th> Quantité </th> 
                <th> Prix </th>
                <th> Image </th>
                <th> Ajouter/Supprimer</th>
            </tr> 

            <c:forEach var="produit" items="${requestScope['produits']}">   

                <form action="GestionStock" method="POST">

                    <tr>
                        <td style="width:20px;"><input type="hidden" value="${produit.id}" name="id_produit"/></td>
                        <td> ${produit.libelle} </td>
                        <td> ${produit.statut} </td> 
                        <td> ${produit.stock} </td>
                        <td> ${produit.prix} </td>
                        <td> <img src="${produit.image}"/></td>
                        <td> <input type="text" name="txt_stockProduit" style="width:40px;"/>
                            &nbsp<input type="submit" name="btn_ajoutProduit" value="+"/>
                            &nbsp<input type="submit" name="btn_supprimerProduit" value="-"/>
                        </td>
                    </tr> 
                </form>

            </c:forEach>
        </table>

    </div>     

</body>
</html>
