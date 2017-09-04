<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<html>
<head>
    <title><fmt:message key="label.book.add_author" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="add_author"/>
                    <div class="form-group row">
                        <label for="name" class="col-3 col-form-label required"> <fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="text"  class="form-control" id="name" name="author_name" value="" pattern="[a-zA-ZА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="surname" class="col-3 col-form-label required"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="surname" name="author_surname" value="" pattern="[a-zA-ZА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="patronymic" class="col-3 col-form-label"><fmt:message key="label.patronymic" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="patronymic" name="author_patronymic" value="" pattern="[a-zA-ZА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.patronymic" bundle="${messages}"/>>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label for="date_of_birth" class="col-3 col-form-label required"><fmt:message key="label.date_of_birth" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "date" class="form-control" id="date_of_birth" name="date_of_birth" value="" required>
                        </div>

                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.book.add_author" bundle="${messages}"/></button>
                        </div>
                    </div>


                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isAuthorAdded && sessionScope.isAuthorAdded eq true}">
                        <fmt:message key="label.book.author_is_added" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isAuthorAdded && sessionScope.isAuthorAdded eq false}">
                        <fmt:message key="label.book.author_not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isAuthorAdded}">
                    <c:remove var="isAuthorAdded" scope="session" />
                </c:if>
            </div>
            <div class="row">

            </div><!--/row-->
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

       <%-- <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
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
