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
    <input type="hidden" name="command" value="add_author"/>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "author_name" value="" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "author_surname" value="" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "author_patronymic" value="" /><br/>

    <fmt:message key="label.date_of_birth" bundle="${messages}"/> :
    <input type = "date" name = "date_of_birth" value="" required/><br/>


    <input type="submit" name="submit" value=<fmt:message key="label.book.add_author" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isAuthorAdded || sessionScope.isAuthorAdded eq true}">
        <fmt:message key="label.book.author_is_added" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isAuthorAdded || sessionScope.isAuthorAdded eq false}">
        <fmt:message key="label.book.author_not_added" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isAuthorAdded}">
    <c:remove var="isAuthorAdded" scope="session" />
</c:if>



<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
