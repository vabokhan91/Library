<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
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
    <title><fmt:message key="label.publisher.add_publisher" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

            <div class="row">

                <div class="col-lg-6">
                    <div class="input-group">
                        <form class="form-inline" method="post" action="/controller">
                            <input type="hidden" name="command" value="add_publisher"/>
                            <div class="col-12 ">
                                <fmt:message key="label.book.enter_publisher_name" bundle="${messages}"/> </div><br/>

                            <input type="text" class="form-control" name="publisher_name" value="" placeholder=<fmt:message key="label.placeholder.publisher_name" bundle="${messages}"/> pattern="[\w]{1,50}" required>
                            <input class="btn btn-secondary" type="submit" value="<fmt:message key="label.publisher.add_publisher" bundle="${messages}"/> ">
                        </form>
                    </div>
                    <c:choose>
                        <c:when test="${not empty sessionScope.isPublisherAdded && sessionScope.isPublisherAdded eq true}">
                            <fmt:message key="label.book.publisher_was_added" bundle="${messages}"/>
                        </c:when>
                        <c:when test="${not empty sessionScope.isPublisherAdded && sessionScope.isPublisherAdded eq false}">
                            <fmt:message key="label.book.publisher_was_not_added" bundle="${messages}"/>
                        </c:when>
                    </c:choose><br/>

                    <c:if test="${not empty sessionScope.isPublisherAdded}">
                        <c:remove var="isPublisherAdded" scope="session" />
                    </c:if>
                </div>
            </div>
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

</body>
</html>
