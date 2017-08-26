<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<c:if test="${user.role.ordinal()!=2}">
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
    <title><fmt:message key="label.book.add_author" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="add_author"/>
                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label"> <fmt:message key="label.name" bundle="${messages}"/></label>
                        <div class="col-sm-3">
                            <input type="text"  class="form-control" id="name" name="author_name" value="" pattern="[\DА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.name" bundle="${messages}"/> required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="surname" class="col-sm-2 col-form-label"><fmt:message key="label.surname" bundle="${messages}"/></label>
                        <div class="col-sm-3">
                            <input type = "text" class="form-control" id="surname" name="author_surname" value="" pattern="[\DА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.surname" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="patronymic" class="col-sm-2 col-form-label"><fmt:message key="label.patronymic" bundle="${messages}"/></label>
                        <div class="col-sm-3">
                            <input type = "text" class="form-control" id="patronymic" name="author_patronymic" value="" pattern="[\DА-Яа-яЁё]{1,40}" placeholder=<fmt:message key="label.patronymic" bundle="${messages}"/>>
                        </div>

                    </div>

                    <div class="form-group row">
                        <label for="date_of_birth" class="col-sm-2 col-form-label"><fmt:message key="label.date_of_birth" bundle="${messages}"/></label>
                        <div class="col-sm-3">
                            <input type = "date" class="form-control" id="date_of_birth" name="date_of_birth" value="" required>
                        </div>

                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.book.add_author" bundle="${messages}"/></button>
                        </div>
                    </div>


                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isAuthorAdded && sessionScope.isAuthorAdded eq true}">
                        <fmt:message key="label.book.author_is_added" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isAuthorAdded && sessionScope.isAuthorAdded eq false}">
                        <fmt:message key="label.book.author_not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isAuthorAdded}">
                    <c:remove var="isAuthorAdded" scope="session" />
                </c:if>
            </div>
            <div class="row">

            </div><!--/row-->
        </div>


        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div><!--/span-->



    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>


















<%--<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_author"/>

    <fmt:message key="label.name" bundle="${messages}"/> :
    <input type = "text" name = "author_name" value="" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.surname" bundle="${messages}"/> :
    <input type = "text" name = "author_surname" value="" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})" required/><br/>

    <fmt:message key="label.patronymic" bundle="${messages}"/> :
    <input type = "text" name = "author_patronymic" value="" pattern="[^\d\W]{1,40}|([а-яА-Я]{1,40})"/><br/>

    <fmt:message key="label.date_of_birth" bundle="${messages}"/> :
    <input type = "date" name = "date_of_birth" value="" required/><br/>


    <input type="submit" name="submit" value=<fmt:message key="label.book.add_author" bundle="${messages}"/> />
</form>



<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>--%>

</body>
</html>
