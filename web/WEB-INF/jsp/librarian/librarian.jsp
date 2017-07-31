<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="message"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Librarian</title>
</head>
<body>

<a href="/controller?command=to_add_user_page"><fmt:message key="label.add_user" bundle="${message}"/> </a><br/>

<a href="/controller?command=get_all_users"><fmt:message key="label.show_all_users" bundle="${message}"/> </a><br/>

<a href="/controller?command=to_Find_User_Page" ><fmt:message key="label.button.find_user" bundle="${message}"/> </a><br/>

</body>
</html>
