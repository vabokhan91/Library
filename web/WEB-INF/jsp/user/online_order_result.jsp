<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.online_order_status" bundle="${messages}"/></title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq true}">
        <fmt:message key="message.order_added_successfully" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq false}">
        <fmt:message key="message.order_was_not_added" bundle="${messages}"/>
    </c:when>
</c:choose>
<br/>

<c:if test="${not empty sessionScope.isOnlineOrderAdded}">
    <c:remove var="isOnlineOrderAdded" scope="session" />
</c:if>


<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
