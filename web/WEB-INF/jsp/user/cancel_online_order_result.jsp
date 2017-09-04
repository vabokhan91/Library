<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT && user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.main_page" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="jumbotron">
                <c:choose>
                    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
                        <fmt:message key="label.book.online_order_cancelled" bundle="${messages}"/><br/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
                        <fmt:message key="label.book.online_order_not_cancelled" bundle="${messages}"/>
                    </c:when>
                </c:choose>

            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
                    <c:choose>
                        <c:when test="${user.role==Role.LIBRARIAN}">
                            <a class="btn btn-secondary" href="/controller?command=to_find_user_online_orders"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a><br/>
                        </c:when>
                        <c:when test="${user.role==Role.CLIENT}">
                            <a class="btn btn-secondary" href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a><br/>
                        </c:when>
                    </c:choose>
                </c:when>

                <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
                    <c:choose>
                        <c:when test="${user.role==Role.LIBRARIAN}">
                            <a class="btn btn-secondary" href="/controller?command=to_find_user_online_orders"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
                        </c:when>
                        <c:when test="${user.role==Role.CLIENT}">
                            <a class="btn btn-secondary" href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isOnlineOrderCancelled}">
                <c:remove var="isOnlineOrderCancelled" scope="session" />
            </c:if>
        </div><!--/span-->

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>--%>
    </div>
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
