<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
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
    <title><fmt:message key="label.book.add_order" bundle="${messages}"/></title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="row">
                <div class="col-10">

                        <div>
                            <img class="main-book-img" src="data:image/jpg;base64,${item.image}"/></div>
                        <div>
                    <form method="post" action="/controller" accept-charset="UTF-8">
                        <input type="hidden" name="command" value="add_order">
                        <input type="hidden" name="book_id" value="${foundBook.id}"/>
                        <input type="hidden" name="librarian_id" value="${sessionScope.user.id}">
                        <div class="parent-book-info"><h2>${foundBook.title}</h2>

                            <fmt:message key="label.book.id" bundle="${messages}"/> : ${foundBook.id}<br/>

                            <fmt:message key="label.book.author" bundle="${messages}"/> : <c:forEach
                                    items="${foundBook.authors}"
                                    var="author">
                                ${author.surname.concat(' ').concat(author.name.charAt(0)).concat('. ').concat(author.patronymic.charAt(0)).concat(';')}</c:forEach><br/>
                            <fmt:message key="label.book.genre" bundle="${messages}"/> :
                            <c:forEach items="${foundBook.genre}" var="genres">
                                ${genres.getName()}
                            </c:forEach><br/>
                            <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${foundBook.isbn}<br/>
                            <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${foundBook.year}<br/>
                            <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${foundBook.pages}<br/>
                            <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${foundBook.publisher.name}<br/><br/>
                            <fmt:message key="label.book.location" bundle="${messages}"/> : ${foundBook.location.name}<br/>
                        <div>
                            <br/>
                            <br/>
                            <div class="form-group row">
                                <label for="type_of_order" class="col-sm-2 col-form-label"
                                       style="margin-right: 13px"><fmt:message key="label.book.type_of_order"
                                                                               bundle="${messages}"/></label>
                                <select id="type_of_order" class="custom-select col-sm-3" name="type_of_order"
                                        required>
                                    <option value="${Location.SUBSCRIPTION}"><fmt:message key="label.book.subscription"
                                                                              bundle="${messages}"/></option>
                                    <option value="${Location.READING_ROOM}"><fmt:message key="label.book.reading_room"
                                                                              bundle="${messages}"/></option>
                                </select>
                            </div>

                            <div class="form-group row">
                                <label for="library_card" class="col-5 col-form-label required"><fmt:message
                                        key="label.book.enter_library_card" bundle="${messages}"/></label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="library_card" name="library_card"
                                           value="" required pattern="\d{1,5}" placeholder=<fmt:message
                                            key="label.placeholder.enter_library_card" bundle="${messages}"/>>
                                </div>
                            </div>


                            <div class="form-group row">
                                <div class="col-sm-10">
                                    <button type="submit" class="btn btn-primary"><fmt:message
                                            key="label.button.book.add_order" bundle="${messages}"/></button>
                                </div>
                            </div>
                        </div>
                        </div>
                    </form>
                </div>


                <div>


                </div>


            </div>
            <!--/span-->
            </c:forEach>
        </div><!--/row-->



    </div><!--/span-->
        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">

                <c:choose>
                    <c:when test="${user.role.ordinal()==3}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message
                                key="label.button.to_main_menu"
                                bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==2}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==1}">
                        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                                key="label.button.to_main_menu"
                                bundle="${messages}"/> </a><br/>
                    </c:when>
                </c:choose>
            </c:if>

        </div><!--/span-->

</div><!--/row-->

<hr>

<footer>
    <p>© Company 2017</p>
</footer>

</div>


<%--<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="add_order">
    <c:forEach items="${foundBook}" var="item">

        <fmt:message key="label.book.id" bundle="${messages}"/> : ${item.getId()}<br/>
        <fmt:message key="label.book.title" bundle="${messages}"/> : ${item.getTitle()}<br/>
        <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
        <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
        <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
        <fmt:message key="label.book.publisher" bundle="${messages}"/> :<c:if
            test="${not empty item.publisher}">${item.publisher.name}</c:if><br/>
        <fmt:message key="label.book.author" bundle="${messages}"/> :
        <c:forEach items="${item.authors}" var="authors">
            ${authors.toString()}
        </c:forEach><br/>
        <fmt:message key="label.book.genre" bundle="${messages}"/> :
        <c:forEach items="${item.genre}" var="genres">
            ${genres.getName()}
        </c:forEach><br/>
        <br/>
        <fmt:message key="label.book.location" bundle="${messages}"/> : ${item.location}<br/>

        <fmt:message key="label.book.type_of_order" bundle="${messages}"/> :
        <select name="type_of_order" required>
            <option value="subscription"><fmt:message key="label.book.subscription" bundle="${messages}"/></option>
            <option value="reading_room"><fmt:message key="label.book.reading_room" bundle="${messages}"/></option>
        </select><br/>
        <fmt:message key="label.book.enter_library_card" bundle="${messages}"/> : <input type="text" name="library_card"
                                                                                         value="" required
                                                                                         pattern="\d{1,5}"/><br/>
        <input type="hidden" name="book_id" value="${foundBook.get(0).id}"/>
        <input type="hidden" name="librarian_id" value="${sessionScope.user.id}">
    </c:forEach>
    <input type="submit" name="submit" value=
    <fmt:message key="label.button.book.add_order" bundle="${messages}"/>/>
</form>
<br/>

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu"
                                                                  bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>--%>


</body>
</html>
