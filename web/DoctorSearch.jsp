<%-- 
    Document   : DoctorSearch
    Created on : Mar 23, 2015, 8:49:49 PM
    Author     : Dylan
--%>

<%@page import="dochunt.models.Province"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Advanced Filter Search</h1>
    </body>
    <form method="post" action="DoctorSearchResultsServlet">
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" /><br/>
        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" /><br/>
        <label for="specializationName">Specialization:</label>
        <input type="text" name="specName" /><br/>
        <label for="city">City:</label>
        <input type="text" name="city" /><br/>
        <label for="province">Province:</label>
        <select name="provId">
            <option value="">(unspecified)</option>
            <%! ArrayList<Province> provinces;%>
            <%
                provinces = (ArrayList<Province>)request.getAttribute("provinces");
                for (Province province : provinces) {
            %>
            <option value="<%= province.provId %>"><%= province.provName %></option>
            <%
                }
            %>
        </select><br/>
        <label for="postalCode">Postal Code Contains:</label>
        <input type="text" name="postalCode" /><br/>
        <label for="reviewedByFriend">Reviewed by Friend:</label>
        <input type="checkbox" name="reviewedByFriend" /><br>
        <label for="comment">Comment Contains:</label>        
        <input type="text" name="comment" /><br/>
        <input type="submit" Value="Search">
    </form>
</html>
