<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.personal_cabinet" bundle="${messages}"/> </title>
</head>
<body>

<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>
<br/>


<div>
    <img src="data:image/jpg;base64,${user.photo}"width="100px" height="150px"/>
</div>
<br/>

<form method="post" action="/controller" enctype="multipart/form-data">
    <input type="hidden" name="command" value="upload_user_photo"/>
    <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
    <input type="file" name="user_photo" size="50" onchange="submit()"/>
    <%--<input type="submit" value="<fmt:message key="label.upload_new_photo" bundle="${messages}"/> ">--%>
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq true}">
        <fmt:message key="label.user.photo_uploaded" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq false}">
        <fmt:message key="label.user.photo_not_uploaded" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isPhotoUploaded}">
    <c:remove var="isPhotoUploaded" scope="session" />
</c:if>


<a href="/controller?command=to_change_password_page"><fmt:message key="label.user.change_password" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_change_login_page"><fmt:message key="label.login.change_login" bundle="${messages}"/> </a><br/>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="get_user_orders">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">

    <input type="submit" value="<fmt:message key="label.order.watch_orders" bundle="${messages}"/> ">
</form>


<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_user_find_book_page">

    <input type="submit" value="<fmt:message key="label.book.find_book" bundle="${messages}"/> ">
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_get_online_orders_page">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
    <input type="submit" value="<fmt:message key="label.book.online_orders" bundle="${messages}"/> ">
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_get_online_orders_page">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
    <input type="submit" value="<fmt:message key="label.book.cancel_online_order" bundle="${messages}"/> ">
</form>
<br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href = "/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>


</body>
</html>
