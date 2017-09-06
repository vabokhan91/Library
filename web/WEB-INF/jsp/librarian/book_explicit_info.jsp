<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>

<html lang="${language}">
<head>
    <title><fmt:message key="label.book.explicit_info" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="row">
                    <div class="col-10">
                        <div class="parent-book-info"><h2>${foundBook.title}</h2>
                            <div>
                                <img class="main-book-img" src="data:image/jpg;base64,${foundBook.image}"/></div>
                            <div>
                                <c:if test="${user.role == Role.LIBRARIAN || user.role == Role.ADMINISTRATOR}">
                                    <fmt:message key="label.book.id" bundle="${messages}"/> : ${foundBook.id}<br/>
                                </c:if>
                                <fmt:message key="label.book.author" bundle="${messages}"/> : <c:forEach items="${foundBook.authors}"
                                                                                                         var="author">
                                ${author.surname.concat(' ').concat(author.name).concat(' ').concat(author.patronymic).concat(';')}</c:forEach><br/>
                                <fmt:message key="label.book.genre" bundle="${messages}"/> :
                                <c:forEach items="${foundBook.genre}" var="genres">
                                    ${genres.getName()}
                                </c:forEach><br/>
                                <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${foundBook.isbn}<br/>
                                <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${foundBook.year}<br/>
                                <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${foundBook.pages}<br/>
                                <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${foundBook.publisher.name}<br/><br/>
                                <fmt:message key="label.book.description" bundle="${messages}"/> : ${foundBook.description}<br/><br/>
                                <c:if test="${user.role==Role.LIBRARIAN || user.role == Role.ADMINISTRATOR}">
                                    <fmt:message key="label.book.orderStatus" bundle="${messages}"/> : ${foundBook.orderStatus.name}<br/>
                                </c:if>
                            </div>
                        </div>


                        <div>
                            <c:if test="${item.getLocation()==Location.STORAGE && user.role==Role.CLIENT}">
                                <td>
                                    <form method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="to_add_online_order_page"/>
                                        <input type="hidden" name="book_id" value="${foundBook.id}"/>
                                        <input type="hidden" name="type_of_search" value="by_id">
                                        <button class="btn btn-secondary" type="submit" name="submit"><fmt:message key="label.book.make_online_order"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>

                            <c:if test="${user.role ==Role.LIBRARIAN}">
                                <td>
                                    <form class="form-action" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="get_book_for_editing"/>
                                        <input type="hidden" name="book_id" value="${foundBook.id}"/>
                                        <button class="btn btn-secondary" type="submit"><fmt:message key="label.book.edit_book"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                                <c:if test="${foundBook.getLocation()== Location.STORAGE}">
                                    <td>
                                        <form class="form-action" method="post" action="/controller" accept-charset="UTF-8">
                                            <input type="hidden" name="command" value="delete_book"/>
                                            <input type="hidden" name="book_id" value="${foundBook.id}"/>
                                            <button class="btn btn-secondary" type="submit" name="submit" ><fmt:message key="label.button.book.delete_book"
                                                                                                                        bundle="${messages}"/></button>
                                        </form>
                                    </td>
                                </c:if>
                                <c:if test="${foundBook.getLocation()==Location.STORAGE}">
                                    <td>
                                        <form class="form-action" action="/controller" accept-charset="UTF-8">
                                            <input type="hidden" name="command" value="to_add_order_page"/>
                                            <input type="hidden" name="book_id" value="${foundBook.id}"/>
                                            <button class="btn btn-secondary" type="submit" ><fmt:message key="label.button.book.add_order"
                                                                                                                        bundle="${messages}"/></button>
                                        </form>
                                    </td>
                                </c:if>
                            </c:if>

                        </div>
                        <br/><br/>

                        <div>
                            <br/>
                            <c:if test="${not empty user.role && user.role!=Role.CLIENT}">
                                <h3><fmt:message key="label.book.latest_order_information" bundle="${messages}"/> :</h3> <br/>


                                    <fmt:message key="label.library_card" bundle="${messages}"/> : ${foundOrder.user.libraryCardNumber}<br/>

                                    <fmt:message key="label.name" bundle="${messages}"/> : ${foundOrder.user.name}<br/>

                                    <fmt:message key="label.surname" bundle="${messages}"/> : ${foundOrder.user.surname}<br/>

                                    <fmt:message key="label.patronymic" bundle="${messages}"/> : ${foundOrder.user.patronymic}<br/>

                                    <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${foundOrder.user.mobilePhone}<br/>

                                    <fmt:message key="label.book.order_date" bundle="${messages}"/> : ${foundOrder.orderDate}<br/>

                                    <fmt:message key="label.book.expiration_date" bundle="${messages}"/> : ${foundOrder.expirationDate}<br/>

                                    <fmt:message key="label.book.return_date" bundle="${messages}"/> : ${foundOrder.returnDate}<br/>
                               <br/>
                            </c:if>
                        </div>


                    </div>


            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">

                <c:choose>
                    <c:when test="${user.role==Role.ADMINISTRATOR}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                           bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role==Role.LIBRARIAN}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role==Role.CLIENT}">
                        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu"
                                                                                                               bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>
            </c:if>
        </div>--%>
    </div>

    <hr>

    <footer>
        <p>© Company 2017</p>
    </footer>

</div>

</body>
</html>
