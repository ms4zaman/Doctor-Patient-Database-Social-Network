<%-- 
    Document   : SeeFriendship
    Created on : Mar 22, 2015, 1:51:03 PM
    Author     : henrychung
--%>

<%@page import="dochunt.helpers.LoginUtil"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% LoginUtil.assertUserLoggedIn(session, response); %>
        <h1>Hello World!</h1>
        <%! ArrayList<String> friends;%>
        <%
            friends = (ArrayList<String>)request.getAttribute("friends");
            for (String friend : friends) {
        %>
            <%= friend %> <br/>
        <% } %>
        <% if (friends.size() == 0) { %>
            What a loser, you have no friends
        <% } %>
    </body>
</html>
