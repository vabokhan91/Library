<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.online_order_status" bundle="${messages}"/> </title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isOnlineOrderExecuted && sessionScope.isOnlineOrderExecuted eq true}">
        <fmt:message key="label.book.order_executed" bundle="${messages}"/>
        <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.order.execute_one_more_online_order" bundle="${messages}"/> </a><br/>
    </c:when>
    <c:when test="${not empty sessionScope.isOnlineOrderExecuted && sessionScope.isOnlineOrderExecuted eq false}">
        <fmt:message key="label.book.order_not_executed" bundle="${messages}"/>
        <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isOnlineOrderExecuted}">
    <c:remove var="isOnlineOrderExecuted" scope="session" />
</c:if>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
