<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN && user.role!=Role.CLIENT}">
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
    <title><fmt:message key="label.book.online_orders" bundle="${messages}"/> </title></head>
<body background="image/books-484766_1920.jpg">
<jsp:include page="../header.jsp"/>

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

            <table class="table ebalyavrot">
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

                    <c:if test="${item.location == Location.BOOKED}">
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
                                <c:if test="${user.role == Role.LIBRARIAN && item.location == Location.BOOKED}">
                                    <form method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="get_online_order_information_page"/>
                                        <input type="hidden" name="online_order_id" value="${item.id}"/>
                                        <input type="hidden" name="book_id" value="${item.book.id}"/>
                                        <input type="hidden" name="library_card" value="${item.user.libraryCardNumber}"/>
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
                                ${item.location.name}

                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>

            <div class="row">

            </div><!--/row-->
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

