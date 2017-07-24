<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>User Information</title></head>

<body>
<h3>User Information</h3>

${user_found_status}
${foundUser}


<%--<a href = "jsp/add_user.jsp">Add User</a>--%>
<br/>
<a href = "/controller?command=logout">Log Out</a>
</body>
</html>
