<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=3}">
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
    <title><fmt:message key="label.add_user" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">


<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="add_user"/>
                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label required"> <fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="name" name = "user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="surname" class="col-sm-2 col-form-label required"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="surname" name = "user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="patronymic" class="col-sm-2 col-form-label"><fmt:message key="label.patronymic" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="patronymic" name = "user_patronymic" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.patronymic" bundle="${messages}"/>>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label required"><fmt:message key="label.address" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="address" name = "user_address" value="" placeholder=<fmt:message key="label.address" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${user.role.ordinal() == 3}">


                            <div class="form-group row">
                                <label for="role" class="col-sm-2 col-form-label required" style="margin-right: 13px" ><fmt:message key="label.role" bundle="${messages}"/></label>
                                <select id="role" class="custom-select col-5 "  name="user_role" required>
                                    <option value="librarian"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                                    <option value="client"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
                                </select>
                            </div>

                            <br/>

                            <div class="form-group row">
                                <label for="login" class="col-sm-2 col-form-label"><fmt:message key="label.user.login" bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type = "text" class="form-control" id="login" name = "login" value="" placeholder=<fmt:message key="label.user.login" bundle="${messages}"/> pattern="[\w!()*&^%$@]{5,12}"/>
                                    <small class="form-text text-muted">
                                        <fmt:message key="label.login.login_info" bundle="${messages}"/>
                                    </small>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="password" class="col-sm-2 col-form-label "><fmt:message key="label.password"  bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type = "password" class="form-control" id="password" name = "user_password" value="" placeholder=<fmt:message key="label.password"  bundle="${messages}"/> pattern="[\w!()*&^%$@]{3,12}" />
                                    <small class="form-text text-muted">
                                        <fmt:message key="label.password.password_info" bundle="${messages}"/>
                                    </small>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="confirm_password" class="col-sm-2 col-form-label "><fmt:message key="label.confirm_password" bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type = "password" class="form-control" id="confirm_password" name = "confirm_password" value="" placeholder=<fmt:message key="label.placeholder.confirm_password" bundle="${messages}"/> pattern="[\w!()*&^%$@]{1,12}" />
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <input type = "hidden" name = "user_role" value="client"/>
                        </c:otherwise>
                    </c:choose><br/>
                    <div class="form-group row">
                        <label for="mobile_phone" class="col-sm-2 col-form-label required"><fmt:message key="label.mobile_phone" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="mobile_phone" name = "user_mobilephone" value="" placeholder= <fmt:message key="label.placeholder.mobile_phone" bundle="${messages}"/> pattern="^((\+)[\-]?)?(\(?\d{3}\)?[\-]?)?[\d\-]{7,10}$" required/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.mobilephone.mobilephone_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <c:if test="${user.role.ordinal()==3}">
                        <div class="form-group row">
                            <label  for="photo" class="col-sm-2 col-form-label" style="margin-right: 13px"><fmt:message key="label.upload_photo" bundle="${messages}"/></label>
                            <input type ="file" id="photo" name = "user_photo" size="50"/><br/>
                        </div>
                    </c:if>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.add_user" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.userIsAdded && sessionScope.userIsAdded eq true}">
                        <fmt:message key="label.user.added_successfully" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.userIsAdded && sessionScope.userIsAdded eq false}">
                        <fmt:message key="label.user.not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.userIsAdded}">
                    <c:remove var="userIsAdded" scope="session" />
                </c:if>
            </div>
            <div class="row">

            </div><!--/row-->
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


    <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
        <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
        <c:if test="${not empty user}">

            <c:choose>
                <c:when test="${user.role.ordinal()==3}">
                    <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                       bundle="${messages}"/> </a><br/>
                </c:when>
                <c:when test="${user.role.ordinal()==2}">
                    <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                            key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                </c:when>

            </c:choose>
        </c:if>

    </div>--%>



    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>


<script>
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        debugger
        if(password.value != confirm_password.value) {
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
