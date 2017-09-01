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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="css/library.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
            integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
            integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
            crossorigin="anonymous"></script>
    <title><fmt:message key="label.genre.add_genre" bundle="${messages}"/> </title>
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

                            <%--<div class="col-12 required">
                                <fmt:message key="label.book.enter_genre_name" bundle="${messages}"/> </div><br/>

                            <input type="text" class="form-control" name="genre_name" value="" placeholder=<fmt:message key="label.placeholder.genre_name" bundle="${messages}"/> pattern="[a-zA-Zа-яА-Я\s]{1,30}" required>

                            <input class="btn btn-secondary" type="submit" value="<fmt:message key="label.genre.add_genre" bundle="${messages}"/> ">--%>
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

