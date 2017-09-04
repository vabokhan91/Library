<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN&& user.role!=Role.ADMINISTRATOR}">
    <jsp:forward page="/index.jsp"/>
</c:if>

<html>
<head>
    <title><fmt:message key="label.edit_status" bundle="${messages}"/> </title>
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
                    <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq true}">
                        <fmt:message key="label.user.edit_success" bundle="${messages}"/><br/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq false}">
                        <fmt:message key="label.user.edit_failed" bundle="${messages}"/>
                    </c:when>
                </c:choose>
            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq true}">
                    <a class="btn btn-secondary" href="/controller?command=to_find_user_page"><fmt:message key="label.user.edit_one_more_user" bundle="${messages}"/> </a><br/>
                </c:when>
                <c:when test="${not empty sessionScope.isUserEdited && sessionScope.isUserEdited eq false}">
                    <a class="btn btn-secondary" href="/controller?command=to_find_user_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isUserEdited}">
                <c:remove var="isUserEdited" scope="session" />
            </c:if>

        </div><!--/span-->

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">

            <c:if test="${not empty user}">
                <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                        key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
                <c:choose>
                    <c:when test="${user.role==Role.ADMINISTRATOR}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message
                                key="label.button.to_main_menu"
                                bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role==Role.LIBRARIAN}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>

                </c:choose>
            </c:if>

        </div>--%>
    </div><!--/row-->
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
