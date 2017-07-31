<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Edit user</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="edit_user"/>
<fmt:message key="label.library_card" bundle="${messages}"/> : <input type="text" name="library_card" value="${foundUser.id}"/><br/>
<fmt:message key="label.name" bundle="${messages}"/> : <input type="text" name="username" value="${foundUser.name}"/> <br/>
<fmt:message key="label.surname" bundle="${messages}"/> : <input type="text" name="usersurname" value="${foundUser.surname}"/> <br/>
<fmt:message key="label.patronymic" bundle="${messages}"/> : <input type="text" name="userpatronymic" value="${foundUser.patronymic}"/> <br/>

<c:choose>
    <c:when test="${user.role.ordinal() == 3}">
        <fmt:message key="label.role" bundle="${messages}"/> : ${foundUser.role}
        <select name="user_role">
            <option value="1"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
            <option value="2"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
        </select><br/>
    </c:when>
        <c:otherwise>
        <input type = "hidden" name = "user_role" value="${foundUser.role}"/>
    </c:otherwise>

</c:choose><br/>

<fmt:message key="label.address" bundle="${messages}"/> : <input type="text" name="useraddress" value="${foundUser.address}"/> <br/>
<fmt:message key="label.login" bundle="${messages}"/> : <input type="text" name="login" value="${foundUser.login}"/> <br/>
<fmt:message key="label.mobile_phone" bundle="${messages}"/> : <input type="text" name="usermobilephone" value="${foundUser.mobilePhone}"/> <br/>
    <input type="submit" name="submit" value=<fmt:message key="label.user.edit_user" bundle="${messages}"/> />
</form>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
