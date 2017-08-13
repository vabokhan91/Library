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
    <input type = "text" name = "user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "user_patronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "user_address" value=""/><br/>
    <c:choose>
        <c:when test="${user.role.ordinal() == 3}">
            <fmt:message key="label.role" bundle="${messages}"/> :
            <select name="user_role">
                <option  value="2"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                <option value="1"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
            </select><br/>

            <fmt:message key="label.login" bundle="${messages}"/> :
            <input type = "text" name = "login" value="" pattern="[^\W]{1,12}"/><br/>

            <fmt:message key="label.password"  bundle="${messages}"/> :
            <input type = "password" name = "user_password" placeholder="Password" id="password" value="" pattern="[\w!()*&^%$@]{1,12}"/><br/>

            <fmt:message key="label.password" bundle="${messages}"/> :
            <input type = "password" name = "confirm_password" placeholder="Confirm password" id="confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}"/>
        </c:when>
        <c:otherwise>
            <input type = "hidden" name = "user_role" value="1"/>
        </c:otherwise>
    </c:choose><br/>
    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "user_mobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$"/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.userIsAdded && sessionScope.userIsAdded eq true}">
        <fmt:message key="label.user.added_successfully" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.userIsAdded && sessionScope.userIsAdded eq false}">
        <fmt:message key="label.user.not_added" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.userIsAdded}">
    <c:remove var="userIsAdded" scope="session" />
</c:if>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

<script>
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        debugger
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>

</body>
</html>
