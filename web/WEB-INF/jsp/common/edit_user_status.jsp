<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2&& user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Edit status</title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq true}">
        <fmt:message key="label.user.edit_success" bundle="${messages}"/><br/>
    </c:when>
    <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq false}">
        <fmt:message key="label.user.edit_failed" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isUserEdited}">
    <c:remove var="isUserEdited" scope="session" />
</c:if>

<a href="/controller?command=get_all_users"><fmt:message key="label.user.edit_one_more_user" bundle="${messages}"/> </a><br/>

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
