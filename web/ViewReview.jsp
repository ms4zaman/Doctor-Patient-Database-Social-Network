<%-- 
    Document   : ViewReview
    Created on : Mar 22, 2015, 11:16:19 PM
    Author     : henrychung
--%>

<%@page import="dochunt.models.Review"%>
<%@page import="dochunt.helpers.LoginUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | View Review</title>
    </head>
    <body>
        <% LoginUtil.assertUserLoggedIn(session, response); %>
        <%@include file="includes/AccountInfo.jsp" %>
        <h1>View Review</h1>
        <%
            Review review = (Review)request.getAttribute("review");
            if (review != null) {
        %>
            Doctor: <%= review.doctorFirstName %><br/>
            Date: <%= review.date %><br/>
            Rating: <%= review.rating %><br/>
            Comments: <br/>
            <%= review.comments %><br/>
            TODO: Prev and next button
        <% } %>
    </body>
</html>
