<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
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
                        <label for="login" class="col-sm-2 col-form-label"><fmt:message key="label.login.enter_new_login" bundle="${messages}"/></label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="login" name="new_login" value=""
                                   pattern="[\w!()*&^%$@]{1,12}" required/>
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





        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div><!--/span-->


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
