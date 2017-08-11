<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Return done</title>
</head>
<body>


<c:choose>
    <c:when test="${not empty sessionScope.isBookReturned && sessionScope.isBookReturned eq true}">
        <fmt:message key="label.book.return_success" bundle="${messages}"/>
        <a href="/controller?command=to_find_user_orders_page"><fmt:message key="label.book.return_one_more_book" bundle="${messages}"/> </a><br/>
    </c:when>
    <c:when test="${not empty sessionScope.isBookReturned && sessionScope.isBookReturned eq false}">
        <fmt:message key="label.book_was_not_returned" bundle="${messages}"/>
        <a href="/controller?command=to_find_user_orders_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.bookDeleteStatus}">
    <c:remove var="bookAddStatus" scope="session" />
</c:if>


<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>


<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
