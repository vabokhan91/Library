<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Change login</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="change_login" />

    <fmt:message key="label.login.enter_new_login" bundle="${messages}"/> :
    <input type = "text" name = "new_login" value="" pattern="[^\W]{1,12}" required/><br/>

    <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
    <input type="submit" name="submit" value=<fmt:message key="label.login.change_login" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq true}">
        <fmt:message key="label.user.login_changed" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq false}">
        <fmt:message key="label.user.login_not_chaged" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isLoginChanged}">
    <c:remove var="isLoginChanged" scope="session" />
</c:if>

<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>


</body>
</html>
