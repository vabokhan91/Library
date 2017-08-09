<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Add Publisher</title>
</head>
<body>
<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_publisher"/>
    <fmt:message key="label.book.enter_publisher_name" bundle="${messages}"/> : <input type="text" name="publisher_name" value="" required/><br/>
    <input type="submit" name="submit" value=<fmt:message key="label.book.add_publisher" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isPublisherAdded || sessionScope.isPublisherAdded eq true}">
        <fmt:message key="label.book.publisher_was_added" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isPublisherAdded || sessionScope.isPublisherAdded eq false}">
        <fmt:message key="label.book.publisher_was_not_added" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isPublisherAdded}">
    <c:remove var="isPublisherAdded" scope="session" />
</c:if>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
