<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>

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

    <title><fmt:message key="label.registration" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" >
                    <input type="hidden" name="command" value="register" />

                    <div class="form-group row">
                        <label for="library_card" class="col-6 col-form-label"> <fmt:message key="label.book.enter_library_card" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="library_card" name="library_card" value="" pattern = "[\d]{1,5}" required placeholder=<fmt:message key="label.placeholder.enter_library_card" bundle="${messages}"/>/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="name" class="col-6 col-form-label"><fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="name" name="user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-6 col-form-label"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="surname" name="user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="login" class="col-6 col-form-label"><fmt:message key="label.login.login" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "text" class="form-control" id="login" name = "login" value="" pattern = "[\w!()*&^%$@]{1,12}" placeholder=<fmt:message key="label.login.login" bundle="${messages}"/> required>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-6 col-form-label"><fmt:message key="label.password" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "password" class="form-control" id="password" name = "user_password" value="" pattern="[\w!()*&^%$@]{1,12}" placeholder=<fmt:message key="label.password" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="confirm_password" class="col-6 col-form-label"><fmt:message key="label.confirm_password" bundle="${messages}"/></label>
                        <div class="col-6">
                            <input type = "password" class="form-control" id="confirm_password" name = "confirm_password" value="" placeholder= <fmt:message key="label.placeholder.confirm_password" bundle="${messages}"/> pattern="[\w!()*&^%$@]{1,12}" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.registration" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
        </div><!--/span-->
    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>






<%--
<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="register" />

    <fmt:message key="label.book.enter_library_card" bundle="${messages}"/> :
    <input type = "text" name = "library_card" value="" pattern = "[\d]{1,5}" required/><br/>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "user_name" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "user_surname" value="" pattern = "[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.login" bundle="${messages}"/> :
    <input type = "text" name = "login" value="" pattern="[^\W]{1,12}" required/><br/>

    <fmt:message key="label.password" bundle="${messages}"/> :
    <input type = "password" name="user_password" placeholder="Password" id="password"  value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <fmt:message key="label.confirm_password" bundle="${messages}"/> :
    <input type = "password" name = "confirm_password" placeholder="Confirm Password" id="confirm_password" value="" pattern="[\w!()*&^%$@]{1,12}" required/><br/>

    <input type="submit" name="submit" value=<fmt:message key="label.registration" bundle="${messages}"/> />
</form>
--%>


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
