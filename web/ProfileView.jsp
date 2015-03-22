<%-- 
    Document   : ProfileView
    Created on : Mar 21, 2015, 5:08:11 PM
    Author     : henrychung
--%>

<%@page import="dochunt.models.Patient"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | Profile View</title>
    </head>
    <body>
        <h1>Profile View</h1>
        <%! ArrayList<Patient> patients;%>
        <%
            patients = (ArrayList<Patient>)request.getAttribute("patients");
        %>

        <% for(Patient patient : patients) { %>
            <%= patient.alias %>
            <%= patient.city %>
            <%= patient.province %>
            <%= patient.numReviews %>
            <%= patient.latestReviewDate %>
            <br/>
        <% } %>
    </body>
</html>
