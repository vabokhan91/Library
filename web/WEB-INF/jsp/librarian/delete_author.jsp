<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.book.delete_author" bundle="${messages}"/> </title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="delete_author"/>

    <fmt:message key="label.book.author" bundle="${messages}"/> :
    <br/>
    <select name="book_author" multiple required>
        <c:forEach items="${authors}" var="author">
            <option value="${author.id}">  ${author}</option>
        </c:forEach>
    </select>
    <br/>
    <input type="submit" name="submit" value=<fmt:message key="label.button.delete_author" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq true}">
        <fmt:message key="label.book.author_deleted" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq false}">
        <fmt:message key="label.book.author_not_deleted" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isAuthorDeleted}">
    <c:remove var="isAuthorDeleted" scope="session" />
</c:if>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
