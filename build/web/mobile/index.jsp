<%-- 
    Document   : index
    Created on : 3 déc. 2013, 10:01:55
    Author     : INF-PORT-CR2
--%>


<%
    String erreur = (String) request.getAttribute("erreur"); 
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="mobile/css/index.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body>

    <center><img src="Images/Tendre_Epis_03_detoure.png"/></center>
        
        <div id="connexion">
            
        <h2>Connexion</h2>
        </br>
        </br>
        
            <form action="connexion" method="POST">
                
                Login </br> <input type="text" id ="idtext_login" name="text_login"> </br></br>
                Mot de passe </br> <input type="password" id="idpassword_motDePasse" name ="password_motDePasse">
                </br>
                </br>
                </br>
                <input type="submit" value="Connexion">
                
                </br>
                </br>
                
              <% if(erreur != null) { %>
              <h1> <font color="red"><%= erreur %></font></h1>
              <% } %>
                
            </form>
        </div>
            
    </body>
</html>
