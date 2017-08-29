<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT}">
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
    <title><fmt:message key="label.password.change_password" bundle="${messages}"/></title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="change_password"/>
                    <input type="hidden" name="library_card" value="${sessionScope.user.id}">

                    <div class="form-group row">
                        <label for="old_password" class="col-3 col-form-label required"><fmt:message
                                key="label.password.enter_old_password" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="password" class="form-control" id="old_password" name="old_password" value=""
                                   pattern="[\w!()*&^%$@]{1,12}" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-3 col-form-label required"><fmt:message
                                key="label.password.enter_new_password" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="password" class="form-control" id="password" name="new_password" value=""
                                   pattern="[\w!()*&^%$@]{1,12}"/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.password.password_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="confirm_password" class="col-3 col-form-label required"> <fmt:message
                                key="label.password.repeat_password" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="password" class="form-control" id="confirm_password" name="confirm_password"
                                   value="" pattern="[\w!()*&^%$@]{1,12}"/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.password.password_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.password.change_password" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
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

<script>
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword() {
        debugger
        if (password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>

</body>
</html>
