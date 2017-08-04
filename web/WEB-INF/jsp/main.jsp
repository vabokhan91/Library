<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html lang="${language}">
<head><title>
    <fmt:message key="label.login" bundle="${messages}"/> </title></head>
<body>
<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>

<c:choose>
    <c:when test="${user==null}">
        <ctg:welcome-tag />
        <form method = "POST" action = "/controller">
            <input type = "hidden" name = "command" value = "login"/>
            <fmt:message key="label.login" bundle="${messages}"/> : <br/>
            <input type = "text" name = "login" value=""/><br/>
            <fmt:message key="label.password" bundle="${messages}"/> : <br/>
            <input type = "password" name = "password" value=""/>
            <br/>
                ${errorLoginPassMessage}
            <c:if test="${not empty sessionScope.errorLoginPassMessage}">
                <c:remove var="errorLoginPassMessage" scope="session" />
            </c:if>
            <br/>
            <input type="submit" name="submit" value=<fmt:message key="label.button.login" bundle="${messages}"/> />
        </form>
        <br />

        <a href="/controller?command=to_Registration_Page" bundle="${config}"/>"><fmt:message key="label.registration" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <ctg:welcome-tag/>
        <br />
    </c:otherwise>
</c:choose>


<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
</c:choose>


</body></html>

