<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Add Author</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_genre"/>

    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <input type = "text" name = "genre_name" value="" required/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.button.add_genre" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isGenreAdded && sessionScope.isGenreAdded eq true}">
        <fmt:message key="label.book.genre_is_added" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isGenreAdded && sessionScope.isGenreAdded eq false}">
        <fmt:message key="label.book.genre_not_added" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isGenreAdded}">
    <c:remove var="isGenreAdded" scope="session" />
</c:if>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>

