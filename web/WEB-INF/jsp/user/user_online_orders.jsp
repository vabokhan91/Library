<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<%@ page import="by.epam.bokhan.entity.OrderStatus" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN && user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.book.user_online_orders" bundle="${messages}"/></title>
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

                <h4><fmt:message key="label.book.user_online_orders" bundle="${messages}"/> </h4> <br/>
            </c:if>
            <c:if test="${user.role == Role.CLIENT}">
                <h4><fmt:message key="label.user.your_online_orders" bundle="${messages}"/></h4>
            </c:if>


            <h5><fmt:message key="label.order.active_online_orders" bundle="${messages}"/></h5><br>

            <table class="table">
                <thead class="thead-default">
                <tr>
                    <c:if test="${user.role == Role.LIBRARIAN}">
                        <th><fmt:message key="label.online_order.id" bundle="${messages}"/></th>
                        <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
                    </c:if>
                    <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
                    <th><fmt:message key="label.online_order.order_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.online_order.expiration_date" bundle="${messages}"/></th>

                </tr>
                </thead>
                <tbody>

                <c:forEach items="${userOrders.orders}" var="item">

                    <c:if test="${item.orderStatus == OrderStatus.BOOKED}">
                    <tr>
                        <c:if test="${user.role == Role.LIBRARIAN}">
                            <td>${item.id}</td>
                            <td>${item.book.id}</td>
                        </c:if>
                        <td>${item.book.title}</td>
                        <td>${item.orderDate}</td>
                        <td>${item.expirationDate}</td>
                        <td>
                            <span class="expander">
                                <c:if test="${user.role == Role.LIBRARIAN && item.orderStatus == OrderStatus.BOOKED}">
                                    <form action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="get_online_order_information_page"/>
                                        <input type="hidden" name="online_order_id" value="${item.id}"/>
                                        <input type="hidden" name="book_id" value="${item.book.id}"/>
                                        <input type="hidden" name="library_card" value="${userOrders.libraryCardNumber}"/>
                                        <input type="hidden" name="librarian_id" value="${user.id}"/>
                                        <button type="submit" class="btn btn-primary">
                                            <fmt:message key="label.button.book.execute_online_order" bundle="${messages}"/>
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${empty item.returnDate}">
                                <form method="post" action="/controller" accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="cancel_online_order"/>
                                    <input type="hidden" name="order_id" value="${item.id}"/>
                                    <input type="hidden" name="book_id" value="${item.book.id}"/>
                                    <button type="submit" class="btn btn-primary">
                                        <fmt:message key="label.button.book.cancel_online_order" bundle="${messages}"/>
                                    </button>
                                </form>
                                </c:if>
                            </span>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>

                </tbody>
            </table>
            <br/>
            <br/>

            <h5><fmt:message key="label.order.previous_online_orders" bundle="${messages}"/></h5>
            <br/>
            <br/>

            <table class="table">
                <thead class="thead-default">
                <tr>
                    <c:if test="${user.role == Role.LIBRARIAN}">
                        <th><fmt:message key="label.online_order.id" bundle="${messages}"/></th>
                        <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
                    </c:if>
                    <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
                    <th><fmt:message key="label.online_order.order_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.online_order.expiration_date" bundle="${messages}"/></th>
                    <th><fmt:message key="label.book.order_status" bundle="${messages}"/></th>

                </tr>
                </thead>
                <tbody>

                <c:forEach items="${userOrders.orders}" var="item">
                    <c:if test="${not empty item.returnDate}">
                    <tr>
                        <c:if test="${user.role == Role.LIBRARIAN}">
                            <td>${item.id}</td>
                            <td>${item.book.id}</td>
                        </c:if>
                        <td>${item.book.title}</td>
                        <td>${item.orderDate}</td>
                        <td>${item.expirationDate}</td>
                        <td>
                                ${item.orderStatus.name}

                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>

            <div class="row">

            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
                <c:choose>
                    <c:when test="${user.role == Role.LIBRARIAN}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role == Role.CLIENT}">
                        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>

        </div>--%>

    </div>

</div>
</body>
</html>

