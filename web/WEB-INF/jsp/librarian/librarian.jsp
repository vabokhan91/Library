<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
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
