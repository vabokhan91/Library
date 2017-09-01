<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
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
    <title><fmt:message key="label.librarian_page" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<nav class="lib-navbar navbar fixed-top navbar-dark bg-dark">
    <a class="navbar-brand" href="/controller?command=to_main_page"><fmt:message key="label.library" bundle="${messages}"/> </a>
    <form class="form-inline" action="/controller">
        <input type="hidden" name="command" value="user_find_book">
        <input class="form-control mr-sm-2" type="text" name="find_query_value" value="" placeholder=<fmt:message key="label.placeholder.enter_book_title" bundle="${messages}"/> pattern="[\w\WА-Яа-яЁё]+" required>
        <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="<fmt:message key="label.book.find_book" bundle="${messages}"/>">
    </form>


    <div class="row">
        <form method="post" class="col">
            <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-secondary btn-sm ${language == "en_US" ? "active" : ""}">
                    <input type="radio" name="language" value="en_US" autocomplete="off" onchange="submit()"> English
                </label>
                <label class="btn btn-secondary btn-sm ${language == "ru_RU" ? "active" : ""}">
                    <input type="radio" name="language" value="ru_RU" autocomplete="off" onchange="submit()"> Русский
                </label>
            </div>
        </form>
        <c:if test="${not empty user}">
        <div class="col">
            <a class="btn btn-info btn-sm logout" href="/controller?command=logout" role="button"><fmt:message key="label.logout" bundle="${messages}"/></a>
        </div>
        </c:if>
    </div>
</nav>

</body>
</html>
