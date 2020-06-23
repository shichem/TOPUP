<%-- 
    Document   : index
    Created on : Apr 16, 2020, 2:58:10 PM
    Author     : GarandaTech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String User = "";
    if (session.getAttribute("username") != null) {
        response.sendRedirect("./view/dashboard.jsp");
    } else {
        response.sendRedirect("login.jsp");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
