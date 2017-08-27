<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
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
    <title><fmt:message key="label.personal_cabinet" bundle="${messages}"/> </title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some
                    responsive-range viewport sizes to see it in action.</p>
            </div>
            <div class="row">

            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <div><ctg:welcome-tag/>
                <div class="user-image">
                    <img src="data:image/jpg;base64,${user.photo}"width="100px" height="150px"/>
                    <form  method="post" action="/controller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="upload_user_photo"/>
                        <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
                        <input class="btn btn-secondary-menu" type="file" name="user_photo" size="50" onchange="submit()"/>
                        <input type="submit" value="<fmt:message key="label.upload_new_photo" bundle="${messages}"/> ">
                    </form>

                    <%--<form method="post" action="/controller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="upload_user_photo"/>
                        <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
                        <input type="file" value="<fmt:message key="label.upload_new_photo" bundle="${messages}"/> " name="user_photo" size="50" onchange="submit()"/>
                        &lt;%&ndash;<input type="submit" >&ndash;%&gt;
                    </form>
--%>

                    <c:choose>
                        <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq true}">
                            <fmt:message key="label.user.photo_uploaded" bundle="${messages}"/>
                        </c:when>
                        <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq false}">
                            <fmt:message key="label.user.photo_not_uploaded" bundle="${messages}"/>
                        </c:when>
                    </c:choose><br/>

                    <c:if test="${not empty sessionScope.isPhotoUploaded}">
                        <c:remove var="isPhotoUploaded" scope="session" />
                    </c:if>

                </div>
            </div><br/>
            <br/>
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/><br/>


            <div class="list-group">
                <a href="/controller?command=to_change_password_page" class="list-group-item"><fmt:message key="label.user.change_password" bundle="${messages}"/></a>
                <a href="/controller?command=to_change_login_page" class="list-group-item"><fmt:message key="label.login.change_login" bundle="${messages}"/></a>
                <a href="/controller?command=get_user_orders&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.order.watch_orders" bundle="${messages}"/></a>

                <a href="/controller?command=to_user_find_book_page" class="list-group-item"><fmt:message key="label.book.find_book" bundle="${messages}"/></a>
                <a href="/controller?command=get_all_books" class="list-group-item"><fmt:message key="label.book.show_all_books" bundle="${messages}"/></a>
                <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.online_orders" bundle="${messages}"/></a>
                <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.cancel_online_order" bundle="${messages}"/></a>



            </div>
        </div><!--/span-->
    </div><!--/row-->
    <br/>


</div>

<hr>

<footer>
    <p>© Company 2017</p>
</footer>





<%--
<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>
<br/>


<div>
    <img src="data:image/jpg;base64,${user.photo}"width="100px" height="150px"/>
</div>
<br/>

<form method="post" action="/controller" enctype="multipart/form-data">
    <input type="hidden" name="command" value="upload_user_photo"/>
    <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
    <input type="file" name="user_photo" size="50" onchange="submit()"/>
    &lt;%&ndash;<input type="submit" value="<fmt:message key="label.upload_new_photo" bundle="${messages}"/> ">&ndash;%&gt;
</form>

<c:choose>
    <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq true}">
        <fmt:message key="label.user.photo_uploaded" bundle="${messages}"/>
    </c:when>
    <c:when test="${not empty sessionScope.isPhotoUploaded && sessionScope.isPhotoUploaded eq false}">
        <fmt:message key="label.user.photo_not_uploaded" bundle="${messages}"/>
    </c:when>
</c:choose><br/>

<c:if test="${not empty sessionScope.isPhotoUploaded}">
    <c:remove var="isPhotoUploaded" scope="session" />
</c:if>


<a href="/controller?command=to_change_password_page"><fmt:message key="label.user.change_password" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_change_login_page"><fmt:message key="label.login.change_login" bundle="${messages}"/> </a><br/>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="get_user_orders">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">

    <input type="submit" value="<fmt:message key="label.order.watch_orders" bundle="${messages}"/> ">
</form>


<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_user_find_book_page">

    <input type="submit" value="<fmt:message key="label.book.find_book" bundle="${messages}"/> ">
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_get_online_orders_page">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
    <input type="submit" value="<fmt:message key="label.book.online_orders" bundle="${messages}"/> ">
</form>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="to_get_online_orders_page">
    <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}">
    <input type="submit" value="<fmt:message key="label.book.cancel_online_order" bundle="${messages}"/> ">
</form>
<br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href = "/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>
--%>


</body>
</html>
