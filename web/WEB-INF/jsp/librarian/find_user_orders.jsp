<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Find user's orders</title>
</head>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="get_user_orders">
    <fmt:message key="label.book.enter_library_card" bundle="${messages}"/> : <input type="text" name="library_card" value=""/>

    <input type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
</form>

<br/>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>



<a href = "/controller?command=logout">Log Out</a>

</body>
</html>