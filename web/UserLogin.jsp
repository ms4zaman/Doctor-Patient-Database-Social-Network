<%-- 
    Document   : UserLogin
    Created on : Mar 22, 2015, 3:34:06 PM
    Author     : henrychung
--%>

<%@page import="dochunt.models.LoginInfo"%>
<%@page import="dochunt.helpers.StringHelper"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DocHunt! | Login</title>
        <link rel="stylesheet" type="text/css" href="login.css">
    </head>
    <body>
        <div class="form">
            <h1>Doc Hunt! Login</h1>
            <%
                LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
                if (loginInfo != null) {
                    response.sendRedirect("index.jsp");
                }
            %>

            <%! String authMessage; %>
            <%
                authMessage = (String)request.getAttribute("auth");
                if (!StringHelper.isNullOrEmpty(authMessage)) {
            %>
                <%= authMessage %>
            <% } %>
            <form method="post" action="UserLoginServlet">
                <input type="text" name="alias" id="tx1" class="textbox" placeholder="Alias"> <br/>
                <input type="password" name="password" id="tx2" class="textbox" placeholder="Password"> <br/>

                <input type="submit" value="Login" id="submitButton">
            </form>
        </div>
        <div class="grass"></div>
    </body>
</html>
