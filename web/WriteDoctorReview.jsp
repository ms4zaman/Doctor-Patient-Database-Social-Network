<%-- 
    Document   : WriteDoctorReview
    Created on : Mar 22, 2015, 6:27:44 PM
    Author     : henrychung
--%>

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
        <%@include file="includes/AccountInfo.jsp" %>
        <h1>Write Doctor Review</h1>
        <form method="post" action="AddReviewServlet">
            <input type="hidden" name="d_alias" value="doc_aiken"> <!-- TODO: REPLACE WITH VALID TINGz -->
            Comment: <br/>
            <textarea name="comments" rows="5" cols="30" wrap="virtual"></textarea><br/>
            Rating:
            <select name="rating">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select><br/>

            <input type="submit" value="Rate that Doc!">
        </form>
    </body>
</html>
