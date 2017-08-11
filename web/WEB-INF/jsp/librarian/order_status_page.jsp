<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Order added</title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq true}">
        <fmt:message key="message.order_added_successfully" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq false}">
        <fmt:message key="message.order_was_not_added" bundle="${messages}"/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isOrderAdded}">
    <c:remove var="isOrderAdded" scope="session" />
</c:if>



<a href="/controller?command=to_find_book_page"><fmt:message key="label.book.add_one_more_order" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
