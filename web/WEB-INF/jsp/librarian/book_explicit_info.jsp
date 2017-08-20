<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()==0}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.book.explicit_info" bundle="${messages}"/> </title>
</head>
<body>


<c:forEach items="${foundBook}" var="item">
    <c:if test="${user.role.ordinal()!=1}">
    <fmt:message key="label.book.id" bundle="${messages}"/> : ${item.id}<br/>
    </c:if>
    <fmt:message key="label.book.title" bundle="${messages}"/> : ${item.title}<br/>
    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
    <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
    <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/>
    <fmt:message key="label.book.author" bundle="${messages}"/> :
    <c:forEach items="${item.authors}" var="authors">
        ${authors.toString()}
    </c:forEach><br/>
    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <c:forEach items="${item.genre}" var="genres">
        ${genres.getName()}
    </c:forEach><br/>
    <c:if test="${user.role.ordinal()!=1}">
    <fmt:message key="label.book.location" bundle="${messages}"/> : ${item.location}<br/>
    </c:if>
</c:forEach><br/>
<c:if test="${user.role.ordinal()!=1}">
<fmt:message key="label.book.latest_order_information" bundle="${messages}"/> : <br/>

<c:forEach items="${foundOrder}" var="item">
    <fmt:message key="label.library_card" bundle="${messages}"/> : ${item.user.libraryCardNumber}<br/>

    <fmt:message key="label.name" bundle="${messages}"/> : ${item.user.name}<br/>

    <fmt:message key="label.surname" bundle="${messages}"/> : ${item.user.surname}<br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> : ${item.user.patronymic}<br/>

    <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${item.user.mobilePhone}<br/>

    <fmt:message key="label.book.order_date" bundle="${messages}"/> : ${item.orderDate}<br/>

    <fmt:message key="label.book.expiration_date" bundle="${messages}"/> : ${item.expirationDate}<br/>

    <fmt:message key="label.book.return_date" bundle="${messages}"/> : ${item.returnDate}<br/>
</c:forEach><br/>
</c:if>

<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
</c:choose>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>


</body>
</html>
