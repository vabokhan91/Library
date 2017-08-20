<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.add_user" bundle="${messages}"/> </title>
</head>
<body>

<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>

<form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add_user"/>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "user_patronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "user_address" value="" required/><br/>
    <c:choose>
        <c:when test="${user.role.ordinal() == 3}">
            <fmt:message key="label.role" bundle="${messages}"/> :
            <select name="user_role">
                <option  value="librarian"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                <option value="client"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
            </select><br/>

            <fmt:message key="label.login" bundle="${messages}"/> :
            <input type = "text" name = "login" value="" pattern="[^\W]{1,12}"/><br/>

            <fmt:message key="label.password"  bundle="${messages}"/> :
            <input type = "password" name = "user_password" placeholder="Password" id="password" value="" pattern="[\w!()*&^%$@]{1,12}"/><br/>

            <fmt:message key="label.password" bundle="${messages}"/> :
            <input type = "password" name = "confirm_password" placeholder="Confirm password" id="confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}"/>
        </c:when>
        <c:otherwise>
            <input type = "hidden" name = "user_role" value="client"/>
        </c:otherwise>
    </c:choose><br/>
    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "user_mobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$" required/><br/>
    <c:if test="${user.role.ordinal()==3}">
        <fmt:message key="label.upload_new_photo" bundle="${messages}"/>
    <input type = "file" name = "user_photo" size="50"/><br/>
    </c:if>
    <input type="submit" name="submit" value=<fmt:message key="button.add_user" bundle="${messages}"/> />
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

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

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
