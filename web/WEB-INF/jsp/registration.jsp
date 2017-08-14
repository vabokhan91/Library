<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>

<html>
<head>

    <title>Registration</title>
</head>
<body>




<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="register" />

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "username" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "usersurname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}" /> :
    <input type = "text" name = "userpatronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "useraddress" value="" /><br/>

    <fmt:message key="label.login" bundle="${messages}"/> :
    <input type = "text" name = "login" value="" pattern="[^\W]{1,12}" required/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name="user_password" placeholder="Password" id="password"  value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.confirm_password" bundle="${messages}"/> :
    <input type = "password" name = "confirm_password" placeholder="Confirm Password" id="confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "usermobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$"/><br/>
    <input type = "hidden" name = "user_role" value="2"/><br/>
    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
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

</body>
</html>
