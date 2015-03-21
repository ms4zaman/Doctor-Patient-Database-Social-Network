<%-- 
    Document   : ProfileSearch
    Created on : Mar 21, 2015, 5:15:47 PM
    Author     : henrychung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | Profile Search</title>
    </head>
    <body>
        <h1>Search</h1>
        <form method="post" action="ProfileViewServlet">
            <input type="text" name="alias">
            <input type="submit" value="View Profile">
        </form>
    </body>
</html>
