<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Find online orders</title>
</head>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_get_online_orders_page">
    <fmt:message key="label.book.enter_users_library_card" bundle="${messages}"/> :

    <input type="text" name="library_card" value="">
    <input type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
</form>

<br/>
