<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html>
<head>
    <title><fmt:message key="label.registration_result" bundle="${messages}"/> </title>
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
                    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq true}">
                        <fmt:message key="label.registration.success" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq false}">
                        <fmt:message key="label.registration_failed" bundle="${messages}"/>
                    </c:when>
                </c:choose>
            </div>

            <c:choose>
                <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq true}">
                    <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                            key="label.button.to_main_menu"
                            bundle="${messages}"/> </a><br/>
                </c:when>
                <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq false}">
                    <a class="btn btn-secondary" href="/controller?command=to_registration_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
                </c:when>
            </c:choose>

            <c:if test="${not empty sessionScope.isUserRegistered}">
                <c:remove var="isUserEdited" scope="session" />
            </c:if>

        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

        </div><!--/span-->
    </div><!--/row-->
</div>

<footer>
    <p>© Company 2017</p>
</footer>



<%--
<c:choose>
    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq true}">
        <fmt:message key="label.registration.success" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isUserRegistered && sessionScope.isUserRegistered eq false}">
        <fmt:message key="label.registration_failed" bundle="${messages}"/>
        <a href="/controller?command=to_registration_page"><fmt:message key="label.try.once.again" bundle="${messages}"/> </a><br/>
    </c:when>
</c:choose>

<c:if test="${not empty sessionScope.isUserRegistered}">
    <c:remove var="isUserRegistered" scope="session" />
</c:if>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>--%>

</body>
</html>