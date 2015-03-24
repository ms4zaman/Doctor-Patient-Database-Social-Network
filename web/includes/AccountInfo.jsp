<%-- 
    Document   : AccountInfo.jsp
    Created on : Mar 22, 2015, 5:29:34 PM
    Author     : henrychung
--%>

<%@page import="dochunt.models.LoginInfo"%>

<%
    LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
    if (loginInfo == null) {
%>
    <a href="UserLogin.jsp">Login!</a><br/>
<% } else { %>
    Welcome Back to to Doc Hunt! <%= loginInfo.alias %> (<%= loginInfo.getLevelAsString() %>)<br/>
    <a href="UserLogoutServlet">Logout!</a><br/>
    <br/>
<% } %>