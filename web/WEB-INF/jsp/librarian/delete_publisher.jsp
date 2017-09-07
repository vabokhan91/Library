<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.book.delete_publisher" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-9 col-md-9">
            <div>
                <form class="form-group" method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="delete_publisher"/>

                    <div class="form-group row "><fmt:message key="label.book.choose_publisher_to_remove" bundle="${messages}"/>
                        <br/>
                        <br/>
                        <select class="form-control" name="book_publisher" multiple required>
                            <c:forEach items="${publishers}" var="publisher">
                                <option value="${publisher.id}">  ${publisher}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.book.delete_publisher" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
                <c:choose>
                    <c:when test="${not empty sessionScope.isPublisherDeleted && sessionScope.isPublisherDeleted eq true}">
                        <fmt:message key="label.book.publisher_deleted" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isPublisherDeleted && sessionScope.isPublisherDeleted eq false}">
                        <fmt:message key="label.book.publisher_not_deleted" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isPublisherDeleted}">
                    <c:remove var="isPublisherDeleted" scope="session" />
                </c:if>
            </div>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>
</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>

