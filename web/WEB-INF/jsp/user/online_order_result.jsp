<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<%@ page import="by.epam.bokhan.entity.Role" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.online_order_status" bundle="${messages}"/></title>
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
                    <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq true}">
                        <fmt:message key="message.order_added_successfully" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq false}">
                        <fmt:message key="message.order_was_not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose>
            </div>
            <c:choose>
                <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq true}">
                    <a class="btn btn-secondary" href="/controller?command=to_user_find_book_page"
                       class="list-group-item"><fmt:message key="label.book.add_one_more_online_order"
                                                            bundle="${messages}"/></a>
                </c:when>
                <c:when test="${not empty sessionScope.isOnlineOrderAdded && sessionScope.isOnlineOrderAdded eq false}">
                    <a class="btn btn-secondary" href="/controller?command=to_user_find_book_page"
                       class="list-group-item"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isOnlineOrderAdded}">
                <c:remove var="isOnlineOrderAdded" scope="session"/>
            </c:if>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>--%>
    </div>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
