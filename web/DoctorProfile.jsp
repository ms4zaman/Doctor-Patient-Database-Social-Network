<%-- 
    Document   : DoctorProfile
    Created on : Mar 23, 2015, 1:50:20 PM
    Author     : henrychung
--%>

<%@page import="dochunt.helpers.DateUtil"%>
<%@page import="dochunt.models.Review"%>
<%@page import="dochunt.models.Address"%>
<%@page import="dochunt.helpers.LoginUtil"%>
<%@page import="dochunt.models.Doctor"%>

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
        <h1>Doctor Profile</h1>
        <%
            Doctor doctor = (Doctor)request.getAttribute("doctor");
            if (doctor == null) {
        %>
            Doctor not found
        <% } else { %>
            Doctor: <%= doctor.lastName %>, <%= doctor.firstName %> <br/>
            <% if (LoginUtil.isDoctorLoggedIn(loginInfo)) { %>
                Email: <%= doctor.email %> <br/>
            <% } %>
            Gender: <%= doctor.getGenderText() %> <br/>
            No. Years Licensed: <%= doctor.numYearsLicensed %> <br/>
            Average Rating:
            <% if (doctor.rating < 0) { %>
                n/a
            <% } else { %>
                <%= doctor.rating %>
            <% } %> <br/>
            No. Reviews: <%= doctor.numReviews %> <br/>
            Address(es): <br/>
            <% for(Address address : doctor.addresses) { %>
                <%= address.toString() %> <br/>
            <% } %>
            Specialization(s): <br/>
            <% for(String specialization : doctor.specializations) { %>
                <%= specialization %> <br/>
            <% } %>
            Review(s): <br/>
            <table>
                <thead>
                    <tr>
                        <td>Review ID</td>
                        <td>Rating</td>
                        <td>Comments</td>
                        <td>Date</td>
                    </tr>
                </thead>
                <tbody>
                    <% for(int i = 0; i < doctor.reviews.size(); i++) {
                        Review review = doctor.reviews.get(i);
                    %>
                    <tr>
                        <td><a href="ViewReviewServlet?reviewId=<%= review.reviewId %>">
                            <%= review.reviewId %>
                        </a></td>
                        <td><%= review.rating %></td>
                        <td><%= review.comments %></td>
                        <td><%= DateUtil.formatDateFromEpoch(review.date) %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% if (LoginUtil.isPatientLoggedIn(loginInfo)) { %>
                <a href="WriteDoctorReview.jsp?doctorAlias=<%= doctor.alias %>">
                    Write a new review!
                </a>
            <% } %>
        <% } %>
    </body>
</html>
