<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Remove status</title>
</head>
<body>


<c:choose>
    <c:when test="${not empty sessionScope.isUserDeleted && sessionScope.isUserDeleted eq true}">
        <fmt:message key="label.user.remove_success" bundle="${messages}"/><br/>
    </c:when>
    <c:when test="${not empty sessionScope.isUserDeleted && sessionScope.isUserDeleted eq false}">
        <fmt:message key="label.user.remove_failed" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isUserDeleted}">
    <c:remove var="isUserDeleted" scope="session" />
</c:if>



<form method="get" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="get_users_for_removal"/>

    <input type="submit" name="submit" value="<fmt:message key="label.remove.one_more_user" bundle="${messages}"/>"/>
</form>

<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
</body>
</html>

