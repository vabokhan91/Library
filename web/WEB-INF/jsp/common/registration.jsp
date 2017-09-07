<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>

<html lang="${language}">
<head>
    <title><fmt:message key="label.registration" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
    <script>
        function validatePasswordForm() {
            var password_form = document.getElementById('user_form'),
                new_password_field = document.getElementById("new_password"),
                confirm_password_field = document.getElementById("confirm_password");

            if (!new_password_field.checkValidity()) {
                new_password_field.setCustomValidity("<fmt:message key="label.password_not valid" bundle="${messages}"/>");
            }

            if (!confirm_password_field.checkValidity()) {
                confirm_password_field.setCustomValidity("confirm password failed");
            }

            if (new_password_field.value !== confirm_password_field.value) {
                confirm_password_field.setCustomValidity("<fmt:message key="label.passwords_dont_match" bundle="${messages}"/>");
            }

            if (password_form.reportValidity()){
                password_form.submit();
            }
        }
    </script>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" id="user_form" novalidate onsubmit="event.preventDefault(); validatePasswordForm();" accept-charset="UTF-8" >
                    <input type="hidden" name="command" value="register" />

                    <div class="form-group row">
                        <label for="library_card" class="col-6 col-form-label required"> <fmt:message key="label.book.enter_library_card" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="library_card" name="library_card" value="" pattern = "[\d]{1,5}" required placeholder=<fmt:message key="label.placeholder.enter_library_card" bundle="${messages}"/>/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="name" class="col-6 col-form-label required"><fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="name" name="user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-6 col-form-label required"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="surname" name="user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="login" class="col-6 col-form-label required"><fmt:message key="label.login.login" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="login" name = "login" value="" pattern = "[\w!()*&^%$@]{1,12}" placeholder=<fmt:message key="label.login.login" bundle="${messages}"/> required>
                            <small class="form-text text-muted">
                                <fmt:message key="label.login.login_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="new_password" class="col-6 col-form-label required"><fmt:message key="label.password" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "password" class="form-control" id="new_password" onkeyup="this.setCustomValidity('')" name = "user_password" value="" pattern="[\w!()*&^%$@]{3,12}" placeholder=<fmt:message key="label.password" bundle="${messages}"/> required/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.password.password_info" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="confirm_password" class="col-6 col-form-label required"><fmt:message key="label.confirm_password" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "password" class="form-control" id="confirm_password" onkeyup="this.setCustomValidity('')" name = "confirm_password" value="" placeholder= <fmt:message key="label.placeholder.confirm_password" bundle="${messages}"/> pattern="[\w!()*&^%$@]{3,12}" required/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.password.password_info" bundle="${messages}"/>
                            </small>
                         </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.registration" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
                <c:choose>
                    <c:when test="${sessionScope.isLoginExist == true}">
                        <fmt:message key="label.login_exist" bundle="${messages}"/>
                    </c:when>
                </c:choose>
                <c:if test="${not empty sessionScope.isLoginExist}">
                    <c:remove var="isLoginExist" scope="session" />
                </c:if>
            </div>
        </div>



        <jsp:include page="../navigation_sidebar.jsp"/>


    </div>
</div>


<jsp:include page="../footer.jsp"/>

</body>
</html>
