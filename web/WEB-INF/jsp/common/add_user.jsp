<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Add User</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_user"/>
    <%--<input type = "hidden" name = "user_role" value="2"/><br/>--%>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "username" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "usersurname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "userpatronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "useraddress" value=""/><br/>
    <c:choose>
        <c:when test="${user.role.ordinal() == 3}">
            <fmt:message key="label.role" bundle="${messages}"/> :
            <select name="user_role">
                <option  value="2"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                <option value="1"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
            </select><br/>

            <fmt:message key="label.login" bundle="${messages}"/> :
            <input type = "text" name = "login" value="" pattern="[^\W]{1,12}"/><br/>

            <fmt:message key="label.password" bundle="${messages}"/> :
            <input type = "password" name = "userpassword" value="" pattern="[\w!()*&^%$@]{1,12}"/><br/>

            <fmt:message key="label.password" bundle="${messages}"/> :
            <input type = "password" name = "userpassword" value="" pattern="[\w!()*&^%$@]{1,12}"/>
        </c:when>
        <c:otherwise>
            <input type = "hidden" name = "user_role" value="1"/>
        </c:otherwise>
    </c:choose><br/>
    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "usermobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$"/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
</form>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
