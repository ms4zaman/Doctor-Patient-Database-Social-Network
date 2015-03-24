<%-- 
    Document   : SeeFriendRequests
    Created on : Mar 22, 2015, 2:30:32 PM
    Author     : henrychung
--%>

<%@page import="dochunt.helpers.LoginUtil"%>
<%@page import="dochunt.models.Patient"%>
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
        <table>
            <thead>
                <tr>
                    <td>Alias</td>
                    <td>Email</td>
                    <td>Action</td>
                </tr>
            </thead>
            <tbody>
                <%! ArrayList<Patient> patients;%>
                <%
                    patients = (ArrayList<Patient>)request.getAttribute("friendRequests");
                %>
                <% for(Patient patient : patients) { %>
                    <tr>
                        <td><%= patient.alias %></td>
                        <td><%= patient.email %></td>
                        <td><a href="AddFriendshipServlet?requestee=<%= patient.alias %>">Confirm Friendship!</a></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
