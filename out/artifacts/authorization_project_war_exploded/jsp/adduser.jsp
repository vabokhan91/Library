<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html>
<head>
    <title>Add User</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_user"/>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "username" value=""/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "usersurname" value=""/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "userpatronymic" value=""/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "useraddress" value=""/><br/>

    <fmt:message key="label.role" bundle="${messages}"/> :
    <input type = "text" name = "user_role" value=""/><br/>

    <fmt:message key="label.login" bundle="${messages}"/> :
    <input type = "text" name = "login" value=""/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name = "userpassword" value=""/><br/>

    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "usermobilephone" value=""/><br/>
    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
</form>
${user_insert_status}

<form action="<fmt:message key="path.page.admin" bundle="${config}"/> " />">
    <input type="submit" value="submit">
</form>

</body>
</html>
