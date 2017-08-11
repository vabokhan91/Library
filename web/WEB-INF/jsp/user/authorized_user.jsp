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
    <title>User</title>
</head>
<body>

<a href="/controller?command=to_change_password_page"><fmt:message key="label.user.change_password" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_change_login_page"><fmt:message key="label.login.change_login" bundle="${messages}"/> </a><br/>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="get_user_orders">
    <input type="hidden" name="library_card" value="${sessionScope.user.id}">

    <input type="submit" value="<fmt:message key="label.order.watch_orders" bundle="${messages}"/> ">
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_user_find_book_page">

    <input type="submit" value="<fmt:message key="label.book.find_book" bundle="${messages}"/> ">
</form>

</body>
</html>
