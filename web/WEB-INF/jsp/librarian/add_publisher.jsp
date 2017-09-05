<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><fmt:message key="label.main_page" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-12 col-md-9">
            <div class="row">
                <div class="col-lg-6">
                    <div class="col-12 col-md-9">
                        <form class="form-inline" method="post" action="/controller">
                            <input type="hidden" name="command" value="add_publisher"/>

                                <div class="form-group required">
                                    <label for="name" class="col-5 col-form-label required"><fmt:message key="label.book.enter_publisher_name" bundle="${messages}"/></label>
                                    <div class="col-5">
                                        <input type="text"  class="form-control" id="name" name="publisher_name" value="" pattern="[\d\D]{1,50}" placeholder=<fmt:message key="label.placeholder.publisher_name" bundle="${messages}"/> required/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-sm-10">
                                        <button type="submit" class="btn btn-primary"><fmt:message key="label.publisher.add_publisher" bundle="${messages}"/></button>
                                    </div>
                                </div>
                        </form>
                    </div>
                    <c:choose>
                        <c:when test="${not empty sessionScope.isPublisherAdded && sessionScope.isPublisherAdded eq true}">
                            <fmt:message key="label.book.publisher_was_added" bundle="${messages}"/>
                        </c:when>
                        <c:when test="${not empty sessionScope.isPublisherAdded && sessionScope.isPublisherAdded eq false}">
                            <fmt:message key="label.book.publisher_was_not_added" bundle="${messages}"/>
                        </c:when>
                    </c:choose><br/>

                    <c:if test="${not empty sessionScope.isPublisherAdded}">
                        <c:remove var="isPublisherAdded" scope="session" />
                    </c:if>
                </div>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>--%>

    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
