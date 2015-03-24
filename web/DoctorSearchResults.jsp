<%-- 
    Document   : DoctorSearchResults
    Created on : Mar 23, 2015, 10:39:21 PM
    Author     : Dylan
--%>

<%@page import="dochunt.models.Doctor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dochunt.helpers.LoginUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% LoginUtil.assertUserLoggedIn(session, response); %>
        <h1>Doctor Search Results</h1>
        <table>
            <thead>
                <tr>
                    <td>Alias</td>
                    <td>Name</td>
                    <td>Rating</td>
                    <td>Number of Reviews</td>
                </tr>
            </thead>
            <tbody>
                <%! ArrayList<Doctor> doctors;%>
                <%
                    doctors = (ArrayList<Doctor>)request.getAttribute("doctors");
                %>
                <% for(Doctor doctor : doctors) { %>
                    <tr>
                        <td><a href="DoctorProfileServlet?doctorAlias=<%= doctor.alias %>">
                            <%= doctor.alias %>
                        </a></td>
                        <td><%= doctor.lastName %>, <%= doctor.firstName %></td>
                        <td><%= doctor.rating %></td>
                        <td><%= doctor.numReviews %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
