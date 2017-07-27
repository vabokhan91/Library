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
    <title>Block User</title>
</head>
<body>




<table class="item-table">
    <tr>
        <th>Library Card</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Patronumic</th>
        <th>Address</th>
        <th>Role</th>
        <th>Login</th>
        <th>Mobile Phone</th>
    </tr>

    <c:forEach items="${not_blocked_users}" var="item">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.surname}</td>
            <td>${item.patronymic}</td>
            <td>${item.address}</td>
            <td>${item.roleId}</td>
            <td>${item.login}</td>
            <td>${item.mobilePhone}</td>
            <td><form method="post" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="block_user"/>
                <input type = "hidden" name = "block_query_value" value="${item.id}"/><br/>
                <input type="submit" name="submit" value=<fmt:message key="label.user.block_user" bundle="${messages}"/>/>
            </form> </td>

        </tr>
    </c:forEach>

</table>


<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/></a>

</body>
</html>
