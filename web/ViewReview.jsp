<%-- 
    Document   : ViewReview
    Created on : Mar 22, 2015, 11:16:19 PM
    Author     : henrychung
--%>

<%@page import="dochunt.helpers.StringHelper"%>
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
                String prevReviewId = (String)request.getAttribute("prevReviewId");
                String nextReviewId = (String)request.getAttribute("nextReviewId");
        %>
            Doctor: <%= review.doctorLastName %>, <%= review.doctorFirstName %><br/>
            Date: <%= review.date %><br/>
            Rating: <%= review.rating %><br/>
            Comments: <br/>
            <%= review.comments %><br/>
            <% if (!StringHelper.isNullOrEmpty(prevReviewId)) { %>
                <a href="ViewReviewServlet?reviewId=<%= prevReviewId %>">Prev</a>
            <% } %>
            <% if (!StringHelper.isNullOrEmpty(nextReviewId)) { %>
                <a href="ViewReviewServlet?reviewId=<%= nextReviewId %>">Next</a>
            <% } %>
        <% } %>
    </body>
</html>
