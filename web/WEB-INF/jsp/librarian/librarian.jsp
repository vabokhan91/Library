<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.librarian_page" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">
<jsp:include page="../header.jsp"/>

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
            <div><ctg:welcome-tag/></div><br/>

            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a>
            <br/>
            <br/>

            <div class="list-group">
                <a href="/controller?command=to_add_user_page" class="list-group-item"><fmt:message key="label.add_user" bundle="${messages}"/></a>
                <a href="/controller?command=get_all_users" class="list-group-item"><fmt:message key="label.show_all_users" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_page" class="list-group-item"><fmt:message key="label.button.find_user" bundle="${messages}"/></a>
                <a href="/controller?command=get_all_books" class="list-group-item"><fmt:message key="label.book.show_all_books" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.book.find_book" bundle="${messages}"/></a>
                <a href="/controller?command=to_add_book_page" class="list-group-item"><fmt:message key="label.book.add_book" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.button.book.add_order" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_orders_page" class="list-group-item"><fmt:message key="label.button.book.return_book" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_online_orders" class="list-group-item "><fmt:message key="label.order.execute_online_order" bundle="${messages}"/> </a>
                <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.button.book.delete_book" bundle="${messages}"/></a>
                <a href="/controller?command=to_add_author_page" class="list-group-item"><fmt:message key="label.book.add_author" bundle="${messages}"/></a>
                <a href="/controller?command=to_delete_author_page" class="list-group-item"><fmt:message key="label.book.delete_author" bundle="${messages}"/></a>
                <a href="/controller?command=to_add_genre_page" class="list-group-item"><fmt:message key="label.genre.add_genre" bundle="${messages}"/></a>
                <a href="/controller?command=to_delete_genre_page" class="list-group-item"><fmt:message key="label.button.delete_genre" bundle="${messages}"/></a>
                <a href="/controller?command=to_add_publisher_page" class="list-group-item"><fmt:message key="label.book.add_publisher" bundle="${messages}"/></a>
                <a href="/controller?command=to_delete_publisher_page" class="list-group-item"><fmt:message key="label.book.delete_publisher" bundle="${messages}"/></a>

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
