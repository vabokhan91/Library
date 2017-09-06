<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
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
<html lang="${language}">
<head>
    <title><fmt:message key="label.password.change_password" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>

    <script>
        function validatePasswordForm() {
            var password_form = document.getElementById('password_form'),
                old_password_field = document.getElementById('old_password'),
                new_password_field = document.getElementById("new_password"),
                confirm_password_field = document.getElementById("confirm_password");

            if (!old_password_field.checkValidity()) {
                old_password_field.setCustomValidity('old password failed');
            }

            if (!new_password_field.checkValidity()) {
                new_password_field.setCustomValidity("new password failed");
            }

            if (!confirm_password_field.checkValidity()) {
                confirm_password_field.setCustomValidity("confirm password failed");
            }

            if (new_password_field.value !== confirm_password_field.value) {
                confirm_password_field.setCustomValidity("Passwords Don't Match");
            }

            if (password_form.reportValidity()){
                password_form.submit();
            }
        }
    </script>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div>
                <form method="post" action="/controller" novalidate id="password_form" onsubmit="event.preventDefault(); validatePasswordForm();" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="change_password"/>
                    <input type="hidden" name="user_id" value="${sessionScope.user.id}">

                    <div class="form-group row">
                        <label for="old_password" class="col-3 col-form-label required"><fmt:message
                                key="label.password.enter_old_password" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="password" class="form-control" id="old_password" name="old_password" value=""
                                   pattern="[\w!()*&^%$@]{1,12}" onkeyup="this.setCustomValidity('')" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="new_password" class="col-3 col-form-label required"><fmt:message
                                key="label.password.enter_new_password" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="password" class="form-control" id="new_password" name="new_password" value=""
                                   pattern="[\w!()*&^%$@]{1,12}" onkeyup="this.setCustomValidity('')" required/>
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
                                   value="" pattern="[\w!()*&^%$@]{1,12}" onkeyup="this.setCustomValidity('')" required/>
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

</body>
</html>
