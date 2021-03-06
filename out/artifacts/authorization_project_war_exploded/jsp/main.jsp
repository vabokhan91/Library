<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="message"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html lang="${language}">
<head><title>
    <fmt:message key="label.login" bundle="${message}"/> </title></head>
<body>
<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>


<form method = "POST" action = "/controller">
    <input type = "hidden" name = "command" value = "login"/>
    <fmt:message key="label.login" bundle="${message}"/> : <br/>
    <input type = "text" name = "login" value=""/><br/>
    <fmt:message key="label.password" bundle="${message}"/> : <br/>
    <input type = "password" name = "password" value=""/>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${nullpage}
    <br/>
    ${wrongAction}
    <br/>
    <input type="submit" name="submit" value=<fmt:message key="label.button.login" bundle="${message}"/> />
</form>

<a href="<fmt:message key="path.page.registration_page" bundle="${config}"/>"><fmt:message key="label.registration" bundle="${message}"/> </a>

</body></html>

