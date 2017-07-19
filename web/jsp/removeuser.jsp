<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language"/>
<html>
<head>
    <title>Remove User</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="remove_user"/>

    <fmt:message key="label.id"/> :
    <input type = "text" name = "user_id" value=""/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.remove_user"/> />
</form>
${user_remove_status}

<form action="<fmt:message key="path.page.main"/> " />">
<input type="submit" value="Back to admin page">
</form>

</body>
</html>
