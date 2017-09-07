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
    <title><fmt:message key="label.button.delete_genre" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-9 col-md-9">
            <div>
                <form class="form-group" method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="delete_genre"/>

                    <div class="form-group row"><fmt:message key="label.book.choose_genre_to_remove" bundle="${messages}"/>
                        <br/>
                        <br/>
                        <select class="form-control" name="book_genre" multiple required>
                            <c:forEach items="${genres}" var="genre">
                                <option value="${genre.id}">  ${genre.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.button.delete_genre" bundle="${messages}"/></button>
                        </div>
                    </div>

                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isGenreDeleted && sessionScope.isGenreDeleted eq true}">
                        <fmt:message key="label.book.genre_deleted" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isGenreDeleted && sessionScope.isGenreDeleted eq false}">
                        <fmt:message key="label.book.genre_not_deleted" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isGenreDeleted}">
                    <c:remove var="isGenreDeleted" scope="session" />
                </c:if>
            </div>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


    </div>
</div>


<jsp:include page="../footer.jsp"/>

</body>
</html>
