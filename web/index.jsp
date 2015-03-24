<%-- 
    Document   : index
    Created on : Mar 21, 2015, 5:09:10 PM
    Author     : henrychung
--%>

<%@page import="dochunt.helpers.LoginUtil"%>
<%@page import="dochunt.models.LoginInfo"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt!</title>
    </head>
    <body>
        <h1>DocHunt!</h1>
        <%@include file="includes/AccountInfo.jsp" %>
        Please select one of the following:
        <ul>
            <li><a href="PatientSearchServlet">Profile View</a></li>
            <li>
                <form method="post" action="SeeFriendshipServlet">
                    See Friendship for Alias: <input type="text" name="alias"><br/>
                    <input type="submit" value="See!">
                </form>
            </li>
            <% if (LoginUtil.isPatientLoggedIn(loginInfo)) { %>
                <li>
                    <a href="SeeFriendshipServlet">View Friends</a>
                </li>
                <li>
                    <a href="SeeFriendRequestsServlet">View Pending Requests</a>
                </li>
            <% } %>
            <% if (LoginUtil.isDoctorLoggedIn(loginInfo)) { %>
            <li>
                <form method="post" action="ViewReviewServlet">
                    View Review with ID: <input type="text" name="reviewId"><br/>
                    <input type="submit" value="View Review">
                </form>
            </li>
            <% } %>
            <% if (LoginUtil.isDoctorLoggedIn(loginInfo)) { %>
            <li>
                <form method="post" action="DoctorProfileServlet">
                    View Doctor Profile with alias: <input type="text" name="doctorAlias"><br/>
                    <input type="submit" value="View Profile">
                </form>
            </li>
            <% } %>
        </ul>
    </body>
</html>
