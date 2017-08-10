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
    <title>Delete Genre</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="delete_genre"/>

    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <br/>
    <select name="book_genre" multiple>
        <c:forEach items="${genres}" var="genre">
            <option value="${genre.id}">  ${genre.getName()}</option>
        </c:forEach>
    </select>
    <br/>
    <input type="submit" name="submit" value=<fmt:message key="label.button.delete_genre" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isGenreDeleted && sessionScope.isGenreDeleted eq true}">
        <fmt:message key="label.book.genre_deleted" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isGenreDeleted && sessionScope.isGenreDeleted eq false}">
        <fmt:message key="label.book.genre_not_deleted" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isGenreDeleted}">
    <c:remove var="isGenreDeleted" scope="session" />
</c:if>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
