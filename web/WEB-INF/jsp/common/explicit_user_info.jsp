<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.ADMINISTRATOR && user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.user.explicit_info" bundle="${messages}"/></title>
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
                    <div class="col-10">

                            <div>
                                <img class="lib-main-book-img" src="data:image/jpg;base64,${foundUser.photo}"><br/>
                            <div>
                                    <fmt:message key="label.library_card" bundle="${messages}"/> : ${foundUser.libraryCardNumber}<br/>
                                    <fmt:message key="label.name" bundle="${messages}"/> : ${foundUser.name} <br/>
                                    <fmt:message key="label.surname" bundle="${messages}"/> : ${foundUser.surname} <br/>
                                    <fmt:message key="label.patronymic" bundle="${messages}"/> : ${foundUser.patronymic} <br/>
                                    <fmt:message key="label.role" bundle="${messages}"/> : ${foundUser.role.name} <br/>
                                    <fmt:message key="label.address" bundle="${messages}"/> : ${foundUser.address} <br/>
                                    <fmt:message key="label.login.login" bundle="${messages}"/> : ${foundUser.login} <br/>
                                    <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${foundUser.mobilePhone} <br/>
                            </div>
                        </div>
                        <br/><br/><br/><br/>

                        <div>

                            <table class="table">
                                <h5><fmt:message key="label.book.user_orders" bundle="${messages}"/></h5> <br/>
                                <thead class="thead-default">
                                <tr>

                                    <th class="table-cell-user"><fmt:message key="label.order.id" bundle="${messages}"/></th>
                                    <th class="table-cell-user"><fmt:message key="label.book.id" bundle="${messages}"/></th>
                                    <th class="table-cell-user"><fmt:message key="label.book.title" bundle="${messages}"/></th>
                                    <th class="table-cell-user"><fmt:message key="label.order.order_date" bundle="${messages}"/> </th>
                                    <th class="table-cell-user"><fmt:message key="label.order.return_date" bundle="${messages}"/></th>

                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${foundUser.orders}" var="item">
                                    <tr>
                                        <td>${item.id}</td>
                                        <td>${item.getBook().id}</td>
                                        <td>${item.getBook().title}</td>
                                        <td>${item.orderDate}</td>
                                        <td>${item.returnDate}</td>

                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>


                    </div>

            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>


</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>
