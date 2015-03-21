<%-- 
    Document   : ProfileView
    Created on : Mar 21, 2015, 5:08:11 PM
    Author     : henrychung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | Profile View</title>
    </head>
    <body>
        <h1>Profile View</h1>
        <%! String alias;%>
        <%
            alias = (String)request.getAttribute("newalias");
        %>
        <%= alias %>
    </body>
</html>
