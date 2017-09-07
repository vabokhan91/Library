<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=3}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.add_user" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
    <script>
        function validatePasswordForm() {
            var password_form = document.getElementById('user_form'),
                new_password_field = document.getElementById("new_password"),
                confirm_password_field = document.getElementById("confirm_password");

            if (new_password_field && !new_password_field.checkValidity()) {
                new_password_field.setCustomValidity("<fmt:message key="label.password_not valid" bundle="${messages}"/>");
            }

            if (confirm_password_field && !confirm_password_field.checkValidity()) {
                confirm_password_field.setCustomValidity("confirm password failed");
            }

            if (new_password_field && confirm_password_field && new_password_field.value !== confirm_password_field.value) {
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
                <form method="post" action="/controller" accept-charset="UTF-8" novalidate onsubmit="event.preventDefault(); validatePasswordForm();" enctype="multipart/form-data" id="user_form" >
                    <input type="hidden" name="command" value="add_user"/>
                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label required"> <fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="name" name = "user_name" value="" pattern = "[a-zA-Z\s]{1,40}|([а-яА-ЯёЁ\s]{1,40})" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="surname" class="col-sm-2 col-form-label required"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="surname" name = "user_surname" value="" pattern = "[a-zA-Z\s]{1,40}|([а-яА-ЯёЁ\s]{1,40})" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="patronymic" class="col-sm-2 col-form-label"><fmt:message key="label.patronymic" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="patronymic" name = "user_patronymic" value="" pattern = "[a-zA-Z\s]{1,40}|([а-яА-ЯёЁ\s]{1,40})" placeholder=<fmt:message key="label.patronymic" bundle="${messages}"/>>
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
                                <label for="role" class="col-sm-2 col-form-label required"><fmt:message key="label.role" bundle="${messages}"/></label>
                                <div class="col-5">
                                    <select id="role" class="custom-select col-5"  name="user_role" required>
                                        <option value="librarian"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                                        <option value="client"><fmt:message key="label.user.client" bundle="${messages}"/> </option>
                                    </select>
                                </div>
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
                                <label for="new_password" class="col-sm-2 col-form-label "><fmt:message key="label.password"  bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type = "password" class="form-control" id="new_password" onkeyup="this.setCustomValidity('')" name = "user_password" value="" placeholder=<fmt:message key="label.password"  bundle="${messages}"/> pattern="[\w!()*&^%$@]{3,12}" />
                                    <small class="form-text text-muted">
                                        <fmt:message key="label.password.password_info" bundle="${messages}"/>
                                    </small>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="confirm_password" class="col-sm-2 col-form-label "><fmt:message key="label.confirm_password" bundle="${messages}"/></label>
                                <div class="col-5">
                                    <input type = "password" class="form-control" id="confirm_password" onkeyup="this.setCustomValidity('')" name = "confirm_password" value="" placeholder=<fmt:message key="label.placeholder.confirm_password" bundle="${messages}"/> pattern="[\w!()*&^%$@]{3,12}" />
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
                    <c:when test="${sessionScope.isLoginExist == true}">
                        <fmt:message key="label.login_exist" bundle="${messages}"/>
                    </c:when>
                </c:choose>
                <c:if test="${not empty sessionScope.isLoginExist}">
                    <c:remove var="isLoginExist" scope="session" />
                </c:if>

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

            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>
</div>



<jsp:include page="../footer.jsp"/>


<%--<script>
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>--%>

</body>
</html>
