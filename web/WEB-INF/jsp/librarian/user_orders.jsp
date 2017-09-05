<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN && user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.main_page" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

        <c:if test="${user.role == Role.LIBRARIAN}">

            <fmt:message key="label.library_card" bundle="${messages}"/> : ${userOrders.libraryCardNumber}<br/>

            <fmt:message key="label.name" bundle="${messages}"/> : ${userOrders.name}<br/>

            <fmt:message key="label.surname" bundle="${messages}"/> : ${userOrders.surname}<br/>

            <fmt:message key="label.patronymic" bundle="${messages}"/> : ${userOrders.patronymic}<br/>

            <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${userOrders.mobilePhone}<br/><br/>

            <h4><fmt:message key="label.book.user_orders" bundle="${messages}"/> </h4> <br/>
        </c:if>
            <c:if test="${user.role == Role.CLIENT}">
                <h4><fmt:message key="label.user.your_orders" bundle="${messages}"/></h4>
            </c:if>


            <h5><fmt:message key="label.active_orders" bundle="${messages}"/> </h5>
            <table class="table">
                <thead class="thead-default">
                <tr>
                    <c:if test="${user.role == Role.LIBRARIAN}">
                    <th><fmt:message key="label.order.id" bundle="${messages}"/></th>
                    <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
                    </c:if>
                    <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
                    <th><fmt:message key="label.order.order_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.order.expiration_date" bundle="${messages}"/></th>


                </tr>
                </thead>
                <tbody>

                <c:forEach items="${userOrders.orders}" var="item">
                    <tr>
                        <c:if test="${empty item.returnDate}">
                        <c:if test="${user.role==Role.LIBRARIAN}">
                            <td>${item.id}</td>
                            <td>${item.book.id}</td>
                        </c:if>
                        <td>${item.book.title}</td>
                        <td>${item.orderDate}</td>
                        <td>${item.expirationDate}</td>
                        <c:if test="${user.role == Role.LIBRARIAN}">
                            <c:choose>
                                <c:when test="${empty item.returnDate}">
                                    <td>
                                        <form method="post" action="/controller" accept-charset="UTF-8">
                                            <input type="hidden" name="command" value="return_book"/>
                                            <input type="hidden" name="book_id" value="${item.book.id}"/>
                                            <input type="hidden" name="order_id" value="${item.id}"/>
                                            <button type="submit" class="btn btn-primary"><fmt:message key="label.button.book.return_book" bundle="${messages}"/></button>
                                        </form>
                                    </td>
                                </c:when>
                            </c:choose>
                        </c:if>
                        </c:if>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <h5><fmt:message key="label.former_orders" bundle="${messages}"/></h5>
            <table class="table">
                <thead class="thead-default">
                <tr>
                    <c:if test="${user.role ==Role.LIBRARIAN}">
                        <th><fmt:message key="label.order.id" bundle="${messages}"/></th>
                        <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
                    </c:if>
                    <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
                    <th><fmt:message key="label.order.order_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.order.expiration_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.order.return_date" bundle="${messages}"/></th>

                </tr>
                </thead>
                <tbody>

                <c:forEach items="${userOrders.orders}" var="item">
                    <tr>
                        <c:if test="${not empty item.returnDate}">
                        <c:if test="${user.role==Role.LIBRARIAN}">
                            <td>${item.id}</td>
                            <td>${item.book.id}</td>
                        </c:if>
                        <td>${item.book.title}</td>
                        <td>${item.orderDate}</td>
                        <td>${item.expirationDate}</td>
                        <td>${item.returnDate}</td>
                        </c:if>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">
                <c:choose>
                    <c:when test="${user.role.ordinal()==2}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==1}">
                        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>
            </c:if>

        </div>--%>



    </div>
</div>





       <%-- <fmt:message key="label.library_card" bundle="${messages}"/> : ${userOrders.get(0).user.libraryCardNumber}<br/>

        <fmt:message key="label.name" bundle="${messages}"/> : ${userOrders.get(0).user.name}<br/>

        <fmt:message key="label.surname" bundle="${messages}"/> : ${userOrders.get(0).user.surname}<br/>

        <fmt:message key="label.patronymic" bundle="${messages}"/> : ${userOrders.get(0).user.patronymic}<br/>

        <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${userOrders.get(0).user.mobilePhone}<br/>

        <fmt:message key="label.book.user_orders" bundle="${messages}"/> : <br/>--%>

    <%--<hr/>--%>

    <%--<table class="item-table">
        <tr>
<c:if test="${user.role.ordinal()!=2}">
            <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
</c:if>
            <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.isbn" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.order_date" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.expiration_date" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.return_date" bundle="${messages}"/></th>
        </tr>

        <c:forEach items="${userOrders}" var="item">
            <tr>
                <c:if test="${user.role.ordinal()!=2}">
                <td>${item.book.id}</td>
                </c:if>
                <td>${item.book.title}</td>
                <td>${item.book.isbn}</td>
                <td>${item.orderDate}</td>
                <td>${item.expirationDate}</td>
                <td>${item.returnDate}</td>
                <c:if test="${user.role.ordinal() == 2}">
                <c:choose>
                    <c:when test="${empty item.returnDate}">
                        <td>
                            <form method="post" action="/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="return_book"/>
                                <input type="hidden" name="book_id" value="${item.book.id}"/>
                                <input type="hidden" name="order_id" value="${item.id}"/>
                                <input type="submit" name="submit" value="<fmt:message key="label.button.book.return_book" bundle="${messages}"/>"/>
                            </form>
                        </td>
                    </c:when>
                </c:choose>
                </c:if>

            </tr>
        </c:forEach>

    </table>--%>


    <%--<c:choose>
        <c:when test="${user.role.ordinal()==2}">
            <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
        </c:when>
        <c:when test="${user.role.ordinal()==1}">
            <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
        </c:when>
    </c:choose>

    <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>--%>


</body>
</html>
