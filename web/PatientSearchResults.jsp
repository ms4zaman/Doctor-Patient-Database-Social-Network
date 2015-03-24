<%-- 
    Document   : ProfileView
    Created on : Mar 21, 2015, 5:08:11 PM
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
        <title>DocHunt! | Patient Search Results</title>
    </head>
    <body>
        <% LoginUtil.assertUserLoggedIn(session, response); %>
        <h1>Patient Search Results</h1>
        <table>
            <thead>
                <tr>
                    <td>Alias</td>
                    <td>City</td>
                    <td>Province</td>
                    <td>Number of Reviews Written</td>
                    <td>Latest Review Date</td>
                </tr>
            </thead>
            <tbody>
                <%! ArrayList<Patient> patients;%>
                <%
                    patients = (ArrayList<Patient>)request.getAttribute("patients");
                %>
                <% for(Patient patient : patients) { %>
                    <tr>
                        <td><%= patient.alias %></td>
                        <td><%= patient.city %></td>
                        <td><%= patient.province %></td>
                        <td><%= patient.numReviews %></td>
                        <td><%= patient.latestReviewDate %></td>
                        <td><a href="AddFriendshipServlet?requestee=<%= patient.alias %>">Add As Friend</a></td
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
