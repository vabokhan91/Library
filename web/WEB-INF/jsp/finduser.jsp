<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="label.button.find_user" bundle="${messages}"/> </title></head>

<body>

<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>
<br/>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="find_user">
    <fmt:message key="label.enter_library_card_or_surname" bundle="${messages}"/> :
    <input name="find_query_value" value="">
    <input type="submit" value="<fmt:message key="label.button.find_user" bundle="${messages}"/> ">
</form>
<br/>

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose>
<br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href = "/controller?command=logout">Log Out</a>


</body>
</html>
