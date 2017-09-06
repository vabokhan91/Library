<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.ADMINISTRATOR}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.block_status" bundle="${messages}"/></title>
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
                    <c:when test="${not empty sessionScope.isUserBlocked && sessionScope.isUserBlocked eq true}">
                        <fmt:message key="label.user.blocked_true" bundle="${messages}"/><br/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isUserBlocked && sessionScope.isUserBlocked eq false}">
                        <fmt:message key="label.user.blocked_false" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>
            </div>


            <c:choose>
                <c:when test="${not empty sessionScope.isUserBlocked && sessionScope.isUserBlocked eq true}">
                    <a class="btn btn-secondary" href="/controller?command=get_not_blocked_users"><fmt:message key="label.block_one_more_user" bundle="${messages}"/> </a><br/>
                </c:when>
                <c:when test="${not empty sessionScope.isUserBlocked && sessionScope.isUserBlocked eq false}">
                    <a class="btn btn-secondary" href="/controller?command=get_not_blocked_users"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
                </c:when>
            </c:choose><br/>


            <c:if test="${not empty sessionScope.isUserBlocked}">
                <c:remove var="isUserBlocked" scope="session" />
            </c:if>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>
    </div>
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
