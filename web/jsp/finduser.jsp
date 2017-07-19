<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>MainPage</title></head>

<body>
<h3>Find User</h3>


<form method="post" action="/controller"/>
    <input type="hidden" name="command" value="find_user">
    <fmt:message key="label.id" bundle="${messages}"/> :
    <input name="user_id" value="">
    <input type="submit" value="<fmt:message key="label.button.find_user" bundle="${messages}"/> ">
</form>

<br/>
<a href = "/controller?command=logout">Log Out</a>


</body>
</html>
