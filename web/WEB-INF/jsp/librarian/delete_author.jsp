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
    <title><fmt:message key="label.delete_author" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-9 col-md-9">
            <div >

                <form class="form-group" method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="delete_author"/>

                   <div class="form-group row"> <fmt:message key="label.book.choose_author_to remove" bundle="${messages}"/>
                    <br/>
                       <br/>
                    <select class="form-control" name="book_author" multiple required>
                        <c:forEach items="${authors}" var="author">
                            <option value="${author.id}">  <c:out value="${author.surname.concat(' ').concat(author.name).concat(' ').concat(author.patronymic)}"/></option>
                        </c:forEach>
                    </select>

                   </div>
                    <br/>

                   <div class="form-group row">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary"><fmt:message key="label.delete_author" bundle="${messages}"/></button>
                    </div>
            </div>
                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq true}">
                        <fmt:message key="label.book.author_deleted" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq false}">
                        <fmt:message key="label.book.author_not_deleted" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isAuthorDeleted}">
                    <c:remove var="isAuthorDeleted" scope="session" />
                </c:if>
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


<jsp:include page="../footer.jsp"/>
</body>
</html>
