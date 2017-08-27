<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
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
    <title><fmt:message key="label.online_order" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>



<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some
                    responsive-range viewport sizes to see it in action.</p>
            </div>
            <div class="row">
                <c:forEach items="${foundBook}" var="item">
                    <div class="col-10">
                        <div class="parent-book-info"><h2>${item.title}</h2>
                            <div>
                                <img class="main-book-img" src="data:image/jpg;base64,${item.image}"/></div>
                            <div>
                                <fmt:message key="label.book.author" bundle="${messages}"/> : <c:forEach items="${item.authors}"
                                                                                                         var="author">
                                ${author.surname.concat(' ').concat(author.name.charAt(0)).concat('. ').concat(author.patronymic.charAt(0)).concat(';')}</c:forEach><br/>
                                <fmt:message key="label.book.genre" bundle="${messages}"/> :
                                <c:forEach items="${item.genre}" var="genres">
                                    ${genres.getName()}
                                </c:forEach><br/>
                                <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
                                <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
                                <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
                                <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/>
                                <fmt:message key="label.book.description" bundle="${messages}"/> : ${item.description}<br/>
                            </div>
                        </div>
                        <div>

                            <c:if test="${item.getLocation().getName() eq 'storage' && user.role.ordinal()==1}">
                                <td>
                                    <form method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="add_online_order"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}"/>
                                        <button class="btn btn-secondary" type="submit" name="submit"><fmt:message key="label.book.order_command"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>

                        </div>

                    </div>
                    <!--/span-->
                </c:forEach>
            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu"
                                                                                                   bundle="${messages}"/> </a><br/>

        </div><!--/span-->
    </div><!--/row-->

    <hr>

    <footer>
        <p>© Company 2017</p>
    </footer>

</div>









<%--<c:forEach items="${foundBook}" var="item">
    <fmt:message key="label.book.title" bundle="${messages}"/> : ${item.title}<br/>
    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
    <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
    <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/>
    <fmt:message key="label.book.author" bundle="${messages}"/> :
    <c:forEach items="${item.authors}" var="authors">
        ${authors.toString()}
    </c:forEach><br/>
    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <c:forEach items="${item.genre}" var="genres">
        ${genres.getName()}
    </c:forEach><br/>
    <form method="post" action="/controller" accept-charset="UTF-8">
        <input type="hidden" name="command" value="add_online_order"/>
        <input type="hidden" name="book_id" value="${item.id}"/>
        <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}"/>
        <input type="submit" name="submit" value=<fmt:message key="label.button.book.order"
                                                              bundle="${messages}"/>>
    </form>
</c:forEach><br/>


<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
<br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
<br/>--%>


</body>
</html>
