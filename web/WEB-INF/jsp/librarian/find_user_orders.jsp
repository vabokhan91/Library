<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.find_users_orders" bundle="${messages}"/> </title>
</head>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="get_user_orders">
    <fmt:message key="label.book.enter_library_card" bundle="${messages}"/> : <input type="text" name="library_card" value="" required pattern="\d{1,5}"/>

    <input type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
</form>

<br/>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>



<a href = "/controller?command=logout">Log Out</a>

</body>
</html>