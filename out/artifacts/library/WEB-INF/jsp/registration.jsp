<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<%--<script>
    function checkPass(e) {
        if (document.getElementById('userpassword').value !== document.getElementById('userpassword2').value) {
            alert('Passwords do not match');
            e.preventDefault();
            return false;
        } else return true;
    }

</script>--%>
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
    <input type = "password" name = "userpassword" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name = "userpassword2" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "usermobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$"/><br/>
    <input type = "hidden" name = "user_role" value="2"/><br/>
    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
</form>

</body>
</html>
