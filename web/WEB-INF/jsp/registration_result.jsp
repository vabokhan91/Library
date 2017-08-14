<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html>
<head>
    <title>Congratulations</title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq true}">
        <fmt:message key="label.registration.success" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq false}">
        <fmt:message key="label.registration_failed" bundle="${messages}"/>
        <a href="/controller?command=to_registration_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isUserRegistered}">
    <c:remove var="isUserRegistered" scope="session" />
</c:if>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
