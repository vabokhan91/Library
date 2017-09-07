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
    <title><fmt:message key="label.genre.add_genre" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-12 col-md-9">
            <div class="row">
                <div class="col-lg-6">
                    <div class="col-12 col-md-9">
                        <form class="form-inline" method="post" action="/controller">
                            <input type="hidden" name="command" value="add_genre"/>

                            <div class="form-group row required">
                                <label for="name" class="col-6 col-form-label required"><fmt:message key="label.book.enter_genre_name" bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type="text"  class="form-control" id="name" name="genre_name" value="" pattern="[a-zA-Zа-яА-Я\s]{1,30}" placeholder=<fmt:message key="label.placeholder.genre_name" bundle="${messages}"/> required/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="submit" class="btn btn-primary"><fmt:message key="label.genre.add_genre" bundle="${messages}"/></button>
                                </div>
                            </div>


                        </form>
                    </div>
                    <c:choose>
                        <c:when test="${not empty sessionScope.isGenreAdded && sessionScope.isGenreAdded eq true}">
                            <fmt:message key="label.book.genre_is_added" bundle="${messages}"/>
                        </c:when>
                        <c:when test="${not empty sessionScope.isGenreAdded && sessionScope.isGenreAdded eq false}">
                            <fmt:message key="label.book.genre_not_added" bundle="${messages}"/>
                        </c:when>
                    </c:choose><br/>

                    <c:if test="${not empty sessionScope.isGenreAdded}">
                        <c:remove var="isGenreAdded" scope="session" />
                    </c:if>
                </div>
            </div>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>




    </div>
</div>


<jsp:include page="../footer.jsp"/>

</body>
</html>

