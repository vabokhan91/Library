<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.roleId!=4}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Remove User</title>
</head>
<body>


<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="remove_user"/>

    <fmt:message key="label.remove_user" bundle="${messages}"/>
    <select name="type_of_search">
        <option value="by_library_card" ><fmt:message key="label.by_library_card" bundle="${messages}"/></option>
        <option value="by_login"><fmt:message key="label.by.login" bundle="${messages}"/></option>
    </select> :
    <input type = "text" name = "remove_query_value" value=""/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.remove_user" bundle="${messages}"/>/>
</form>
<c:if test="${user_remove_status !=null}">
    <c:choose>
        <c:when test="${isUserDeleted == true}" >
            <fmt:message key="message.remove_user_true" bundle="${messages}"/>
        </c:when>
        <c:otherwise><fmt:message key="message.remove_user_false" bundle="${messages}"/> </c:otherwise>
    </c:choose>
</c:if>
<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/></a>

</body>
</html>
