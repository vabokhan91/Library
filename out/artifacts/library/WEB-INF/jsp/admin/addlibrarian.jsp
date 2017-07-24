<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html>
<head>
    <title>Add Librarian</title>
</head>
<body>
<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_librarian" />

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "username" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "usersurname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}" /> :
    <input type = "text" name = "userpatronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.address" bundle="${messages}"/> :
    <input type = "text" name = "useraddress" value="" required/><br/>

    <fmt:message key="label.login" bundle="${messages}"/> :
    <input type = "text" name = "login" value="" pattern="[^\W]{1,12}" required/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name = "userpassword" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name = "userpassword2" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.mobile_phone" bundle="${messages}"/> :
    <input type = "text" name = "usermobilephone" value="" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$" required/><br/>
    <input type = "hidden" name = "user_role" value="3"/><br/>
    <input type="submit" name="submit" value=<fmt:message key="label.add_user" bundle="${messages}"/> />
</form>
</body>
</html>
