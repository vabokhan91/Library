<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Find book</title>
</head>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="user_find_book">
    <input type="hidden" name="type_of_search" value="by_title">
    <fmt:message key="label.book.enter_book_title" bundle="${messages}"/> :

    <input name="find_query_value" value="">
    <input type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
</form>

<br/>

<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>



<a href = "/controller?command=logout">Log Out</a>

</body>
</html>
