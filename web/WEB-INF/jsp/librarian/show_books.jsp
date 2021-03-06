<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>

<html lang="${language}">
<head>
    <title><fmt:message key="label.book.book_information" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="row">
                <c:forEach items="${books}" var="item">
                    <div class="col-10">
                        <div class="lib-parent-book-info"><h2>${item.title}</h2>
                            <div>
                                <img class="lib-main-book-img" src="data:image/jpg;base64,${item.image}"/></div>
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
                            </div>
                        </div>
                        <div>
                            <form class="form-action" action="/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="get_explicit_book_info"/>
                                <input type="hidden" name="book_id" value="${item.id}"/>
                                <button class="btn btn-secondary" type="submit" ><fmt:message
                                        key="label.button.more_detail"
                                        bundle="${messages}"/></button>
                            </form>

                            <c:if test="${item.getLocation()==Location.STORAGE && user.role==Role.CLIENT}">
                                <td>
                                    <form action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="to_add_online_order_page"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <input type="hidden" name="type_of_search" value="by_id">
                                        <button class="btn btn-secondary" type="submit" ><fmt:message key="label.book.make_online_order"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>

                            <c:if test="${user.role == Role.LIBRARIAN}">
                            <td>
                                <form class="form-action" action="/controller" accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="get_book_for_editing"/>
                                    <input type="hidden" name="book_id" value="${item.id}"/>
                                    <button class="btn btn-secondary" type="submit" ><fmt:message key="label.book.edit_book"
                                                                                                               bundle="${messages}"/></button>
                                </form>
                            </td>
                            <c:if test="${item.getLocation()==Location.STORAGE}">
                                <td>
                                    <form class="form-action" method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="delete_book"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <button class="btn btn-secondary" type="submit" ><fmt:message key="label.button.book.delete_book"
                                                                                                                    bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${item.getLocation()==Location.STORAGE}">
                                <td>
                                    <form class="form-action" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="to_add_order_page"/>
                                        <input type="hidden" name="book_id" value="${item.id}"/>
                                        <button class="btn btn-secondary" type="submit" ><fmt:message key="label.button.book.add_order"
                                                                                                                    bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>
                        </c:if>

                        </div>

                    </div>

                </c:forEach>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>

</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
