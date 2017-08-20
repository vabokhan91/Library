<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>


<html>
<head>
    <title><fmt:message key="label.remove_user" bundle="${messages}"/> </title>

</head>

<body>

<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form><br/>


<table id="tableData" >
    <thead>
    <tr>
        <th><fmt:message key="label.library_card" bundle="${messages}"/> </th>
        <th><fmt:message key="label.name" bundle="${messages}"/></th>
        <th><fmt:message key="label.surname" bundle="${messages}"/> </th>
        <th><fmt:message key="label.patronymic" bundle="${messages}"/> </th>
        <th><fmt:message key="label.role" bundle="${messages}"/> </th>
        <th><fmt:message key="label.login" bundle="${messages}"/></th>
    </tr>
    </thead>

    <c:forEach items="${users}" var="item">
        <tbody>
        <tr>
            <td>${item.libraryCardNumber}</td>
            <td>${item.name}</td>
            <td>${item.surname}</td>
            <td>${item.patronymic}</td>
            <td>${item.role}</td>
            <td>${item.login}</td>

            <td><form method="post" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="remove_user"/>
                <input type = "hidden" name = "user_id" value="${item.id}"/><br/>
                <input type="submit" name="submit" value=<fmt:message key="button.remove_user" bundle="${messages}"/>/>
            </form></td>
        </tr>
        </tbody>
    </c:forEach>



</table>

<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/></a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
