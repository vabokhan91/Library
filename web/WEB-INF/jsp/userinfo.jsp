<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.user_information" bundle="${messages}"/></title></head>

<body>

<h3><fmt:message key="label.user_information" bundle="${messages}"/></h3>

<table class="item-table">
    <tr>
        <th><fmt:message key="label.library_card" bundle="${messages}"/></th>
        <th><fmt:message key="label.name" bundle="${messages}"/></th>
        <th><fmt:message key="label.surname" bundle="${messages}"/></th>
        <th><fmt:message key="label.patronymic" bundle="${messages}"/></th>
        <th><fmt:message key="label.address" bundle="${messages}"/></th>
        <th><fmt:message key="label.role" bundle="${messages}"/></th>
        <th><fmt:message key="label.login" bundle="${messages}"/></th>
        <th><fmt:message key="label.mobile_phone" bundle="${messages}"/></th>
    </tr>

    <c:forEach items="${foundUser}" var="item">
        <tr>
            <td>${item.libraryCardNumber}</td>
            <td>${item.name}</td>
            <td>${item.surname}</td>
            <td>${item.patronymic}</td>
            <td>${item.address}</td>
            <td>${item.role}</td>
            <td>${item.login}</td>
            <td>${item.mobilePhone}</td>
            <td><img src="data:image/jpg;base64,${item.photo}" width="50px" height="50px"></td>
            <td>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="get_user_for_editing"/>
                    <input type="hidden" name="find_query_value" value="${item.libraryCardNumber}"/><br/>
                    <input type="submit" name="submit" value=<fmt:message key="button.edit_user"
                                                                          bundle="${messages}"/>/>
                </form>
            </td>
            <td>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="get_explicit_user_info"/>
                    <input type="hidden" name="library_card" value="${item.libraryCardNumber}"/><br/>
                    <input type="submit" name="submit" value=<fmt:message key="label.button.show_explicit_info"
                                                                          bundle="${messages}"/>/>
                </form>
            </td>
            <c:if test="${user.role.ordinal()==3}">
                <td>
                    <form method="post" action="/controller" accept-charset="UTF-8">
                        <input type="hidden" name="command" value="remove_user"/>
                        <input type="hidden" name="user_id" value="${item.id}"/><br/>
                        <input type="submit" name="submit" value=<fmt:message key="button.remove_user"
                                                                              bundle="${messages}"/>/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>

</table>

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                 bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu"
                                                                          bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

<br/>
<a href="/controller?command=logout">Log Out</a>
</body>
</html>
