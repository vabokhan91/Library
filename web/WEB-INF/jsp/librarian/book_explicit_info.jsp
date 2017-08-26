<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<%--<c:if test="${user.role.ordinal()==0}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>--%>
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
    <title><fmt:message key="label.book.explicit_info" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">


<nav class="lib-navbar navbar fixed-top navbar-dark bg-dark">
    <a class="navbar-brand" href="#"><fmt:message key="label.library" bundle="${messages}"/> </a>

    <form class="form-inline" action="/controller">
        <input type="hidden" name="command" value="find_book">
        <input class="form-control mr-sm-2" type="text" placeholder=<fmt:message key="label.book.enter_book_title" bundle="${messages}"/> aria-label="Search" name="find_query_value" value="">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="label.book.find_book" bundle="${messages}"/></button>
    </form>

    <form method="post">
        <div class="btn-group" data-toggle="buttons">
            <label class="btn btn-secondary btn-sm ${language == "en_US" ? "active" : ""}">
                <input type="radio" name="language" value="en_US" autocomplete="off" onchange="submit()"> English
            </label>
            <label class="btn btn-secondary btn-sm ${language == "ru_RU" ? "active" : ""}">
                <input type="radio" name="language" value="ru_RU" autocomplete="off" onchange="submit()"> Русский
            </label>
        </div>
    </form>
</nav>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="row">
                <c:forEach items="${foundBook}" var="item">
                    <div class="col-10">
                        <div class="parent-book-info"><h2>${item.title}</h2>
                            <div>
                                <img class="main-book-img" src="data:image/jpg;base64,${item.image}"/></div>
                            <div>
                                <c:if test="${user.role.ordinal() == 2 || user.role.ordinal() == 3}">
                                    <fmt:message key="label.book.id" bundle="${messages}"/> : ${item.id}<br/>
                                </c:if>
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
                                <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/><br/>
                                <fmt:message key="label.book.description" bundle="${messages}"/> : ${item.description}<br/><br/>
                                <c:if test="${user.role.ordinal()==2 || user.role.ordinal() == 3}">
                                    <fmt:message key="label.book.location" bundle="${messages}"/> : ${item.location}<br/>
                                </c:if>
                            </div>
                        </div>


                        <div>
                            <c:if test="${item.getLocation().getName() eq 'storage' && user.role.ordinal()==1}">
                                <td>
                                    <form method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="to_add_online_order_page"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <input type="hidden" name="type_of_search" value="by_id">
                                        <input type="submit" name="submit" value=<fmt:message key="label.button.book.make_online_order"
                                                                                              bundle="${messages}"/>/>
                                    </form>
                                </td>
                            </c:if>

                            <c:if test="${user.role.ordinal() == 2}">
                                <td>
                                    <form class="form-action" method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="get_book_for_editing"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <button class="btn btn-secondary" type="submit" name="submit"><fmt:message key="label.book.edit_book"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                                <c:if test="${item.getLocation().getName() eq 'storage'}">
                                    <td>
                                        <form class="form-action" method="post" action="/controller" accept-charset="UTF-8">
                                            <input type="hidden" name="command" value="delete_book"/>
                                            <input type="hidden" name="book_id" value="${item.id}"/>
                                            <button class="btn btn-secondary" type="submit" name="submit" ><fmt:message key="label.button.book.delete_book"
                                                                                                                        bundle="${messages}"/></button>
                                        </form>
                                    </td>
                                </c:if>
                                <c:if test="${item.getLocation().getName() eq 'storage'}">
                                    <td>
                                        <form class="form-action" method="post" action="/controller" accept-charset="UTF-8">
                                            <input type="hidden" name="command" value="to_add_order_page"/>
                                            <input type="hidden" name="book_id" value="${item.id}"/>
                                            <button class="btn btn-secondary" type="submit" name="submit" ><fmt:message key="label.button.book.add_order"
                                                                                                                        bundle="${messages}"/></button>
                                        </form>
                                    </td>
                                </c:if>
                            </c:if>

                        </div>
                        <br/><br/>


                        <div>
                            <br/>
                            <c:if test="${not empty user.role && user.role.ordinal()!=1}">
                                <h3><fmt:message key="label.book.latest_order_information" bundle="${messages}"/> :</h3> <br/>

                                <c:forEach items="${foundOrder}" var="item">
                                    <fmt:message key="label.library_card" bundle="${messages}"/> : ${item.user.libraryCardNumber}<br/>

                                    <fmt:message key="label.name" bundle="${messages}"/> : ${item.user.name}<br/>

                                    <fmt:message key="label.surname" bundle="${messages}"/> : ${item.user.surname}<br/>

                                    <fmt:message key="label.patronymic" bundle="${messages}"/> : ${item.user.patronymic}<br/>

                                    <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${item.user.mobilePhone}<br/>

                                    <fmt:message key="label.book.order_date" bundle="${messages}"/> : ${item.orderDate}<br/>

                                    <fmt:message key="label.book.expiration_date" bundle="${messages}"/> : ${item.expirationDate}<br/>

                                    <fmt:message key="label.book.return_date" bundle="${messages}"/> : ${item.returnDate}<br/>
                                </c:forEach><br/>
                            </c:if>
                        </div>


                    </div>
                    <!--/span-->
                </c:forEach>
            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">

                <c:choose>
                    <c:when test="${user.role.ordinal()==3}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                           bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==2}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==1}">
                        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu"
                                                                                                               bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>
                <a class="btn btn-secondary" href="/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>
            </c:if>

        </div><!--/span-->
    </div><!--/row-->

    <hr>

    <footer>
        <p>© Company 2017</p>
    </footer>

</div>

</body>
</html>
