<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.user.edit_user" bundle="${messages}"/> </title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
    <input type="hidden" name="command" value="edit_user"/>
    <c:forEach items="${foundUser}" var="found_user">
        <fmt:message key="label.library_card" bundle="${messages}"/> : ${found_user.libraryCardNumber}<br/>
        <fmt:message key="label.name" bundle="${messages}"/> : <input type="text" name="user_name" value="${found_user.name}"/> <br/>
        <fmt:message key="label.surname" bundle="${messages}"/> : <input type="text" name="user_surname" value="${found_user.surname}"/> <br/>
        <fmt:message key="label.patronymic" bundle="${messages}"/> : <input type="text" name="user_patronymic" value="${found_user.patronymic}"/> <br/>

        <c:choose>
            <c:when test="${user.role.ordinal() == 3}">
                <fmt:message key="label.role" bundle="${messages}"/> : ${found_user.role}
                <select name="user_role">
                    <option value="client" ><fmt:message key="label.user.client" bundle="${messages}" /> </option>
                    <option value="librarian"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                </select><br/>
            </c:when>
            <c:when test="${user.role.ordinal() == 2}">
                <input type = "hidden" name = "user_role" value="${found_user.role}"/>
            </c:when>

        </c:choose><br/>

        <fmt:message key="label.address" bundle="${messages}"/> : <input type="text" name="user_address" value="${found_user.address}"/> <br/>
        <fmt:message key="label.mobile_phone" bundle="${messages}"/> : <input type="text" name="user_mobilephone" value="${found_user.mobilePhone}"/> <br/>
        <c:choose>
        <c:when test="${user.role.ordinal()==3}">
        <fmt:message key="label.login" bundle="${messages}"/> : <input type="text" name="login" value="${found_user.login}"/> <br/>
        <fmt:message key="label.password" bundle="${messages}"/> : <input type="password" name="new_password" value=""/> <br/>
            <fmt:message key="label.password.repeat_password" bundle="${messages}"/> : <input type="password" name="confirm_password" value=""/> <br/>
            <input type="file" name="user_photo" size="50"/>
        </c:when>
            <c:when test="${user.role.ordinal()==2}">
                <input type = "hidden" name = "login" value="${found_user.login}"/>
            </c:when>
        </c:choose>
        <input type="submit" name="submit" value=<fmt:message key="label.user.edit_user" bundle="${messages}"/> />
        <input type="hidden" name="library_card" value="${found_user.libraryCardNumber}"/>
        <input type="hidden" name="user_id" value="${found_user.id}"/>
    </c:forEach>

</form>

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
