<%-- 
    Document   : ProfileSearch
    Created on : Mar 21, 2015, 5:15:47 PM
    Author     : henrychung
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dochunt.models.Province"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | Profile Search</title>
    </head>
    <body>
        <h1>Search</h1>
        <form method="post" action="PatientSearchResultsServlet">
            Alias: <input type="text" name="alias"><br/>
            Province:
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
            City: <input type="text" name="city"><br/>

            <input type="submit" value="View Profile">
        </form>
    </body>
</html>
