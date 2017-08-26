<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=1}">
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
    <title><fmt:message key="label.book.user_orders" bundle="${messages}"/> </title></head>
<body background="image/books-484766_1920.jpg">


<nav class="lib-navbar navbar fixed-top navbar-dark bg-dark">
    <a class="navbar-brand" href="/controller?command=to_main_page"><fmt:message key="label.library"
                                                                                 bundle="${messages}"/> </a>

    <form class="form-inline" action="/controller">
        <input type="hidden" name="command" value="find_book">
        <input class="form-control mr-sm-2" type="text" name="find_query_value" value="" placeholder=
        <fmt:message key="label.book.enter_book_title" bundle="${messages}"/> pattern="[\w\WА-Яа-яЁё]{3,}" required>
        <input class="btn btn-outline-success my-2 my-sm-0" type="submit"
               value="<fmt:message key="label.book.find_book" bundle="${messages}"/>">
    </form>

    <div class="row">
        <form method="post" class="col">
            <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-secondary btn-sm ${language == "en_US" ? "active" : ""}">
                    <input type="radio" name="language" value="en_US" autocomplete="off" onchange="submit()"> English
                </label>
                <label class="btn btn-secondary btn-sm ${language == "ru_RU" ? "active" : ""}">
                    <input type="radio" name="language" value="ru_RU" autocomplete="off" onchange="submit()"> Русский
                </label>
            </div>
        </form>
        <div class="col">
            <a class="btn btn-info btn-sm logout" href="/controller?command=logout" role="button"><fmt:message
                    key="label.logout" bundle="${messages}"/></a>
        </div>
    </div>
</nav>



<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

        <c:if test="${user.role.ordinal() == 2}">

            <fmt:message key="label.library_card" bundle="${messages}"/> : ${userOrders.get(0).user.libraryCardNumber}<br/>

            <fmt:message key="label.name" bundle="${messages}"/> : ${userOrders.get(0).user.name}<br/>

            <fmt:message key="label.surname" bundle="${messages}"/> : ${userOrders.get(0).user.surname}<br/>

            <fmt:message key="label.patronymic" bundle="${messages}"/> : ${userOrders.get(0).user.patronymic}<br/>

            <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${userOrders.get(0).user.mobilePhone}<br/><br/>

            <h4><fmt:message key="label.book.user_orders" bundle="${messages}"/> </h4> <br/>
        </c:if>
            <c:if test="${user.role.ordinal() == 1}">
                <h4><fmt:message key="label.user.your_orders" bundle="${messages}"/></h4>
            </c:if>

            <table class="table">
                <thead class="thead-default">
                <tr>
                    <c:if test="${user.role.ordinal() ==2}">
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

                <c:forEach items="${userOrders}" var="item">
                    <tr>
                        <c:if test="${user.role.ordinal()==2}">
                            <td>${item.id}</td>
                            <td>${item.book.id}</td>
                        </c:if>
                        <td>${item.book.title}</td>
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
                                            <button type="submit" class="btn btn-primary"><fmt:message key="label.button.book.return_book" bundle="${messages}"/></button>
                                            <%--<input type="submit" name="submit" value="<fmt:message key="label.button.book.return_book" bundle="${messages}"/>"/>--%>
                                        </form>
                                    </td>
                                </c:when>
                            </c:choose>
                        </c:if>

                    </tr>
                </c:forEach>





                <c:forEach items="${users}" var="item">
                    <tr>
                        <th scope="row">${item.libraryCardNumber}</th>
                        <td>${item.name}</td>
                        <td>${item.surname}</td>
                        <td>${item.patronymic}</td>
                        <td>${item.role}</td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>


            <div class="row">

            </div><!--/row-->
        </div>


        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
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

        </div><!--/span-->



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
