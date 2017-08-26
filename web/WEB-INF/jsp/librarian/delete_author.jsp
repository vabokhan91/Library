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
    <title><fmt:message key="label.book.delete_author" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>



<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-9 col-md-9">
            <div >


                <form class="form-group" method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="delete_author"/>

                   <div class="form-group row"> <fmt:message key="label.book.choose_author_to remove" bundle="${messages}"/>
                    <br/>
                       <br/>
                    <select class="form-control" name="book_author" multiple required>
                        <c:forEach items="${authors}" var="author">
                            <option value="${author.id}">  <c:out value="${author.surname.concat(' ').concat(author.name).concat(' ').concat(author.patronymic)}"/></option>
                        </c:forEach>
                    </select>

                   </div>
                    <br/>

                   <div class="form-group row">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary"><fmt:message key="label.delete_author" bundle="${messages}"/></button>
                    </div>
            </div>


                </form>


                <c:choose>
                    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq true}">
                        <fmt:message key="label.book.author_deleted" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isAuthorDeleted && sessionScope.isAuthorDeleted eq false}">
                        <fmt:message key="label.book.author_not_deleted" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isAuthorDeleted}">
                    <c:remove var="isAuthorDeleted" scope="session" />
                </c:if>
        </div>

    </div>
        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>
    </div>
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
