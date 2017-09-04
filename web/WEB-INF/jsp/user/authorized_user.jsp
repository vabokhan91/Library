<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.main_page" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some
                    responsive-range viewport sizes to see it in action.</p>
            </div>
            <div class="row">

            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <div><ctg:welcome-tag/>
                <div class="user-image">
                    <img src="data:image/jpg;base64,${user.photo}"width="100px" height="150px"/>
                    <form  method="post" action="/controller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="upload_user_photo"/>
                        <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
                        <input class="btn btn-secondary-menu" type="file" name="user_photo" size="50" onchange="submit()"/>
                        <input type="submit" value="<fmt:message key="label.upload_new_photo" bundle="${messages}"/> ">
                    </form>

                    <c:choose>
                        <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq true}">
                            <fmt:message key="label.user.photo_uploaded" bundle="${messages}"/>
                        </c:when>
                        <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq false}">
                            <fmt:message key="label.user.photo_not_uploaded" bundle="${messages}"/>
                        </c:when>
                    </c:choose><br/>

                    <c:if test="${not empty sessionScope.isPhotoUploaded}">
                        <c:remove var="isPhotoUploaded" scope="session" />
                    </c:if>

                </div>
            </div><br/>
            <br/>
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/><br/>

            <div class="list-group">
                <a href="/controller?command=to_change_password_page" class="list-group-item"><fmt:message key="label.user.change_password" bundle="${messages}"/></a>
                <a href="/controller?command=to_change_login_page" class="list-group-item"><fmt:message key="label.login.change_login" bundle="${messages}"/></a>
                <a href="/controller?command=get_user_orders&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.order.watch_orders" bundle="${messages}"/></a>
                <a href="/controller?command=to_user_find_book_page" class="list-group-item"><fmt:message key="label.book.find_book" bundle="${messages}"/></a>
                <a href="/controller?command=get_all_books" class="list-group-item"><fmt:message key="label.book.show_all_books" bundle="${messages}"/></a>
                <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.online_orders" bundle="${messages}"/></a>
                <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.cancel_online_order" bundle="${messages}"/></a>

            </div>
        </div><!--/span-->
    </div><!--/row-->
    <br/>

</div>

<hr>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
