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
    <title>Find book</title>
</head>
<body>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="find_book">
    <fmt:message key="label.book.enter_id_or_book_title" bundle="${messages}"/> :

    <select name="type_of_search">
        <option value="by_id"><fmt:message key="label.book.by_book_id" bundle="${messages}"/> </option>
        <option value="by_title"><fmt:message key="label.book.by_title" bundle="${messages}"/></option>
    </select>

    <input name="find_query_value" value="">
    <input type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
</form>

<br/>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>



<a href = "/controller?command=logout">Log Out</a>

</body>
</html>