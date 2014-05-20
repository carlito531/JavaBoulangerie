<%-- 
    Document   : choixFormat
    Created on : 17 mars 2014, 16:34:27
    Author     : INF-PORT-CR2
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="msg" value="${requestScope['hello']}"/>
<!DOCTYPE html>

<html>
    <head>
        <link href="choixFormat.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>



        <div id="left">
        </div>
        <div id="right">
        </div>

        <!-- affichage image -->
         <img src="Images/Baker.png" 
             style = "position: absolute; 
             top:100px; 
             right: 100px;" />
        
         <!-- affichage menu -->
        <div id="center">
            <form action="ChoixFormat" method="POST">

                <div id="center_left">
                    <input type="submit" name="btn_mobile" value="MOBILE"/>
                </div>
                <div id="center_right">
                    <input type="submit" name="btn_web" value="WEB"/> 
                </div>  

            </form> 
        </div>


        <!-- affichage image -->
        <img src="Images/Tendre_Epis_03_detoure.png" style="position:relative;"/>

       
    </body>
</html>
