<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Blocking failed</title>
</head>
<body>
<fmt:message key="label.user.blocked_false" bundle="${messages}"/>

<form method="get" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="get_not_blocked_users"/>

    <input type="submit" name="submit" value="<fmt:message key="label.try.once.again" bundle="${messages}"/>"/>
</form>

<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
</body>
</html>
