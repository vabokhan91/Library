<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.password.change_password" bundle="${messages}"/></title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="change_password" />

    <fmt:message key="label.password.enter_old_password" bundle="${messages}"/> :
    <input type = "password" name = "old_password" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.password.enter_new_password" bundle="${messages}"/> :
    <input type = "password" name = "new_password" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.password.repeat_password" bundle="${messages}"/> :
    <input type = "password" name = "confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>
    <input type="hidden" name="library_card" value="${sessionScope.user.id}">
    <input type="submit" name="submit" value=<fmt:message key="label.password.change_password" bundle="${messages}"/> />
</form>

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

<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href = "/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>

</body>
</html>
