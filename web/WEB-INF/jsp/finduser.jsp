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
    <title>Find USer</title></head>

<body>


<form method="post" action="/controller">
    <input type="hidden" name="command" value="find_user">
    <fmt:message key="label.id" bundle="${messages}"/> :

    <select name="type_of_search">
        <option value="by_library_card"><fmt:message key="label.by_library_card" bundle="${messages}"/> </option>
        <option value="by_login"><fmt:message key="label.by.login" bundle="${messages}"/></option>
    </select>

    <input name="find_query_value" value="">
    <input type="submit" value="<fmt:message key="label.button.find_user" bundle="${messages}"/> ">
</form>

<br/>
<a href = "/controller?command=logout">Log Out</a>


</body>
</html>
