<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Change password status</title>
</head>
<body>


<c:choose>
    <c:when test="${not empty sessionScope.isPasswordChanged && sessionScope.isPasswordChanged eq true}">
        <fmt:message key="label.password.password_changed" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isPasswordChanged && sessionScope.isPasswordChanged eq false}">
        <fmt:message key="label.password.password_not_changed" bundle="${messages}"/>
        <a href="/controller?command=to_change_password_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isPasswordChanged}">
    <c:remove var="isPasswordChanged" scope="session" />
</c:if>


<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>


<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>

