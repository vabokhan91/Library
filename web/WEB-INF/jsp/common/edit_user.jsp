<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.ADMINISTRATOR && user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.user.edit_user" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="edit_user"/>
                    <c:forEach items="${foundUser}" var="found_user">
                        <input type="hidden" name="user_id" value="${found_user.id}"/>
                        <input type="hidden" name="library_card" value="${found_user.libraryCardNumber}"/><br/>

                    <fmt:message key="label.library_card" bundle="${messages}"/> : <c:out value="${found_user.libraryCardNumber}"/><br/><br/>
                    <div class="form-group row">
                        <label for="name" class="col-3 col-form-label required">  <fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="text"  class="form-control" id="name" name="user_name" value="${found_user.name}" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-3 col-form-label required"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="surname" name="user_surname" value="${found_user.surname}" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})"  required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="patronymic" class="col-3 col-form-label"><fmt:message key="label.patronymic" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="patronymic" name="user_patronymic" value="${found_user.patronymic}" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})">
                        </div>
                    </div>
                        <c:choose>
                                    <c:when test="${user.role == Role.ADMINISTRATOR}">
                                        <div class="form-group row">
                                        <label for="role" class="col-3 col-form-label required" style="margin-right: 13px" ><fmt:message key="label.role" bundle="${messages}"/> <c:out value="${found_user.role.name}"/></label>
                                            <select id="role" class="form-control col-5"  name="user_role" required>
                                                <option value="${found_user.role}" ><fmt:message key="label.choose_role" bundle="${messages}" /> </option>
                                                <option value="${Role.CLIENT}" ><fmt:message key="label.user.client" bundle="${messages}" /> </option>
                                                <option value="${Role.LIBRARIAN}"><fmt:message key="label.user.librarian" bundle="${messages}"/> </option>
                                            </select><br/>
                                        </div>
                                    </c:when>
                                    <c:when test="${user.role == Role.LIBRARIAN}">
                                        <input type = "hidden" name = "user_role" value="${found_user.role}"/>
                                    </c:when>
                        </c:choose><br/>

                    <div class="form-group row">
                        <label for="address" class="col-3 col-form-label required"><fmt:message key="label.address" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="address" name="user_address" value="${found_user.address}" required/>
                        </div>
                    </div>

                        <div class="form-group row">
                            <label for="mobile_phone" class="col-3 col-form-label required"><fmt:message key="label.mobile_phone" bundle="${messages}"/></label>
                            <div class="col-5">
                                <input type = "text" class="form-control" id="mobile_phone" name="user_mobilephone" value="${found_user.mobilePhone}" pattern="^((\+)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$" required/>
                                <small class="form-text text-muted">
                                    <fmt:message key="label.mobilephone.mobilephone_info" bundle="${messages}"/>
                                </small>
                            </div>

                        </div>

                        <c:choose>
                            <c:when test="${user.role==Role.ADMINISTRATOR}">
                                <div class="form-group row">
                                    <label for="login" class="col-3 col-form-label required"><fmt:message key="label.login.login" bundle="${messages}"/></label>
                                    <div class="col-5">
                                        <input type = "text" class="form-control" id="login" name="login" value="${found_user.login}" pattern="[\w!()*&^%$@]{1,12}" required/>
                                        <small class="form-text text-muted">
                                            <fmt:message key="label.login.login_info" bundle="${messages}"/>
                                        </small>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="password" class="col-3 col-form-label required"><fmt:message key="label.password" bundle="${messages}"/></label>
                                    <div class="col-5">
                                        <input type = "password" class="form-control" id="password" name="new_password" value="" pattern="[\w!()*&^%$@]{1,12}"/>
                                        <small class="form-text text-muted">
                                            <fmt:message key="label.password.password_info" bundle="${messages}"/>
                                        </small>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="confirm_password" class="col-3 col-form-label required"><fmt:message key="label.password.repeat_password" bundle="${messages}"/></label>
                                    <div class="col-5">
                                        <input type = "password" class="form-control" id="confirm_password" name="confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}"/>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label  for="photo" class="col-3 col-form-label" style="margin-right: 13px"><fmt:message key="label.upload_new_photo" bundle="${messages}"/></label>
                                    <input type="file" name="user_photo" id="photo" size="50"/><br/>
                                </div>

                            </c:when>
                            <c:when test="${user.role==Role.LIBRARIAN}">
                                <input type = "hidden" name = "login" value="${found_user.login}"/>
                            </c:when>
                        </c:choose>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.user.edit_user" bundle="${messages}"/></button>
                        </div>
                    </div>
                </c:forEach>
                </form>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">

                <c:choose>
                    <c:when test="${user.role==Role.ADMINISTRATOR}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                           bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role==Role.LIBRARIAN}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>
            </c:if>

        </div>--%>

    </div>
</div>

<script >
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
</script>

</body>
</html>
