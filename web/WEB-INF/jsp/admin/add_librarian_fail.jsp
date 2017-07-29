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
    <title>Adding failed</title>
</head>
<body>
<fmt:message key="label.user.librarian_not_added" bundle="${messages}"/>
<a href="/controller?command=to_add_librarian_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>


</body>
</html>
