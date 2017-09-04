<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.login.change_login" bundle="${messages}"/></title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="change_login" />
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>

                    <div class="form-group row">
                        <label for="login" class="col-5 col-form-label required"><fmt:message key="label.login.enter_new_login" bundle="${messages}"/></label>
                        <div class="col-3">
                            <input type="text" class="form-control" id="login" name="new_login" value=""
                                   pattern="[\w!()*&^%$@]{1,12}" required/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.login.login_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.login.change_login" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq true}">
                        <fmt:message key="label.user.login_changed" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq false}">
                        <fmt:message key="label.user.login_not_chaged" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isLoginChanged}">
                    <c:remove var="isLoginChanged" scope="session" />
                </c:if>
            </div>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>--%>

    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>





<%--


<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="change_login" />

    <fmt:message key="label.login.enter_new_login" bundle="${messages}"/> :
    <input type = "text" name = "new_login" value="" pattern="[^\W]{1,12}" required/><br/>

    <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
    <input type="submit" name="submit" value=<fmt:message key="label.login.change_login" bundle="${messages}"/> />
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq true}">
        <fmt:message key="label.user.login_changed" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isLoginChanged && sessionScope.isLoginChanged eq false}">
        <fmt:message key="label.user.login_not_chaged" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isLoginChanged}">
    <c:remove var="isLoginChanged" scope="session" />
</c:if>

<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
--%>


</body>
</html>
