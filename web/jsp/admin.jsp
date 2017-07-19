<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<fmt:setBundle basename="resource.config"/>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>MainPage</title></head>

<body>
        <h3>WELCOME</h3>
        ${user.login}, hello!!!
        ${user.name}
        <br/>
        <form action="<fmt:message key="path.page.adduser"/>">
                <input type="submit" value="Add User">
        </form>

        <form action="<fmt:message key="path.page.remove_user"/>">
                <input type="submit" value="Remove User">
        </form>

        <form action="<fmt:message key="path.page.find_user"/>">
                <input type="submit" value="Find User">
        </form>

        <%--<a href = "jsp/adduser.jsp">Add User</a>--%>
        <br/>
        <a href = "/controller?command=logout">Log Out</a>


        </body>
        </html>