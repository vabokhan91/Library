<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Online order cancel status</title>
</head>
<body>

<c:choose>
    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
        <fmt:message key="label.book.online_order_cancelled" bundle="${messages}"/>
        <c:choose>
            <c:when test="${user.role.ordinal()==2}">
                <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a>
            </c:when>
            <c:when test="${user.role.ordinal()==1}">
                <form action="/controller">
                    <input type="hidden" name="command" value="to_get_online_orders_page">
                    <input type="hidden" name="user_id" value="${sessionScope.user.libraryCardNumber}">
                    <input type="submit" value=" <fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/> ">
                </form>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
        <fmt:message key="label.book.online_order_not_cancelled" bundle="${messages}"/>
    <c:choose>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <form action="/controller">
            <input type="hidden" name="command" value="to_get_online_orders_page">
            <input type="hidden" name="user_id" value="${sessionScope.user.libraryCardNumber}">
            <input type="submit" value="<fmt:message key="label.try.once.again" bundle="${messages}"/> ">
        </form>
    </c:when>
</c:choose>


    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isOnlineOrderCancelled}">
    <c:remove var="isOnlineOrderCancelled" scope="session" />
</c:if>


<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
