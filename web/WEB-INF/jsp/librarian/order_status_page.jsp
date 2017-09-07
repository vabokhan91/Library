<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.book.order_status" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="jumbotron">
                <c:choose>
                    <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq true}">
                        <fmt:message key="message.order_added_successfully" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq false}">
                        <fmt:message key="message.order_was_not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose>

            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq true}">
                    <a class="btn btn-secondary" href="/controller?command=to_find_book_page"><fmt:message key="label.book.add_one_more_order" bundle="${messages}"/> </a><br/>
                </c:when>
                <c:when test="${not empty sessionScope.isOrderAdded && sessionScope.isOrderAdded eq false}">
                    <a class="btn btn-secondary" href="/controller?command=to_find_book_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isOrderAdded}">
                <c:remove var="isOrderAdded" scope="session" />
            </c:if>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


    </div>
    <hr>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
