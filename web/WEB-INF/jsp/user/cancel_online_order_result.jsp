<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1 && user.role.ordinal()!=2}">
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
    <title><fmt:message key="label.online_order_cancel_status" bundle="${messages}"/></title>
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
                <c:choose>
                    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
                        <fmt:message key="label.book.online_order_cancelled" bundle="${messages}"/><br/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
                        <fmt:message key="label.book.online_order_not_cancelled" bundle="${messages}"/>
                    </c:when>
                </c:choose>

            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
                    <c:choose>
                        <c:when test="${user.role.ordinal()==2}">
                            <a class="btn btn-secondary" href="/controller?command=to_find_user_online_orders"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a><br/>
                        </c:when>
                        <c:when test="${user.role.ordinal()==1}">
                            <a class="btn btn-secondary" href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a><br/>
                            <%--<form action="/controller">
                                <input type="hidden" name="command" value="to_get_online_orders_page">
                                <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
                                <input type="submit" value=" <fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/> "><br/>
                            </form>--%>
                        </c:when>
                    </c:choose>
                </c:when>

                <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
                    <c:choose>
                        <c:when test="${user.role.ordinal()==2}">
                            <a class="btn btn-secondary" href="/controller?command=to_find_user_online_orders"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
                        </c:when>
                        <c:when test="${user.role.ordinal()==1}">
                            <a class="btn btn-secondary" href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
                            <%--<form action="/controller">
                                <input type="hidden" name="command" value="to_get_online_orders_page">
                                <input type="hidden" name="user_id" value="${sessionScope.user.libraryCardNumber}">
                                <input type="submit" value="<fmt:message key="label.try.once.again" bundle="${messages}"/> ">
                            </form>--%>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isOnlineOrderCancelled}">
                <c:remove var="isOnlineOrderCancelled" scope="session" />
            </c:if>



        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div><!--/span-->
    </div><!--/row-->

    <hr>


</div>


<footer>
    <p>© Company 2017</p>
</footer>









<%--<c:choose>
    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
        <fmt:message key="label.book.online_order_cancelled" bundle="${messages}"/><br/>
        <c:choose>
            <c:when test="${user.role.ordinal()==2}">
                <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/></a><br/>
            </c:when>
            <c:when test="${user.role.ordinal()==1}">
                <form action="/controller">
                    <input type="hidden" name="command" value="to_get_online_orders_page">
                    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
                    <input type="submit" value=" <fmt:message key="label.book.cancel_one_more_order" bundle="${messages}"/> "><br/>
                </form>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
        <fmt:message key="label.book.online_order_not_cancelled" bundle="${messages}"/>
    <c:choose>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_find_user_online_orders"><fmt:message key="label.try.once.again" bundle="${messages}"/></a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <form action="/controller">
            <input type="hidden" name="command" value="to_get_online_orders_page">
            <input type="hidden" name="user_id" value="${sessionScope.user.libraryCardNumber}">
            <input type="submit" value="<fmt:message key="label.try.once.again" bundle="${messages}"/> ">
        </form>
    </c:when>
</c:choose>
    </c:when>
</c:choose>--%>
<br/>

<%--
<c:if test="${not empty sessionScope.isOnlineOrderCancelled}">
    <c:remove var="isOnlineOrderCancelled" scope="session" />
</c:if>


<c:choose>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
--%>


</body>
</html>
