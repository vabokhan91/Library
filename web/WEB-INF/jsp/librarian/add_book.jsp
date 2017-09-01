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
    <title><fmt:message key="label.book.add_book" bundle="${messages}"/></title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="add_book"/>
                    <div class="form-group row">
                        <label for="title" class="col-3 col-form-label required"> <fmt:message key="label.book.book_title" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="text"  class="form-control" id="title" name="book_title" value="" pattern="[\d\w\W[а-яА-Я}]]+" placeholder=<fmt:message key="label.placeholder.book_title" bundle="${messages}"/> required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="pages" class="col-3 col-form-label required"><fmt:message key="label.book.number_of_pages" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="pages" name="book_pages" value="" pattern="\d{1,5}" placeholder=<fmt:message key="label.placeholder.number_of_pages" bundle="${messages}"/> required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="year" class="col-3 col-form-label required"><fmt:message key="label.book.year_of_publishing" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="year" name="book_year" value="" pattern="\d{1,5}" placeholder=<fmt:message key="label.placeholder.year_of_publishing" bundle="${messages}"/>>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="isbn" class="col-3 col-form-label required"><fmt:message key="label.book.isbn" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="isbn" name="book_isbn" value="" placeholder=<fmt:message key="label.book.isbn" bundle="${messages}"/> pattern="(\d+-\d+-\d+-\d+-\d+)|(\d+-\d+-\d+-\d+)" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="publisher" class="col-3 col-form-label required" style="margin-right: 13px" ><fmt:message key="label.book.publisher" bundle="${messages}"/></label>
                        <select id="publisher" class="custom-select col-5"  name="book_publisher" required>
                            <c:forEach items="${publishers}" var="publisher">
                                <option value="${publisher.id}">${publisher.getName()} </option>
                            </c:forEach>
                        </select>
                    </div>



                    <div class="form-group">
                        <label for="genre" class="col-3 col-form-label required"><fmt:message key="label.book.genre" bundle="${messages}"/></label>
                        <select multiple id="genre" class="form-control col-5" name="book_genre" required>
                            <c:forEach items="${genres}" var="genre">
                                <option value="${genre.id}">${genre.getName()} </option>
                            </c:forEach>
                        </select>
                    </div>



                    <div class="form-group">
                        <label for="author" class="col-3 col-form-label required" style="margin-right: 13px" ><fmt:message key="label.book.author" bundle="${messages}"/></label>
                        <select multiple class="form-control col-5" id="author" name="book_author" required>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}">${author.getSurname()} ${author.getName()} ${author.getPatronymic()} </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="description"><fmt:message key="label.book.description" bundle="${messages}"/></label>
                        <textarea class="form-control col-9" id="description" rows="4" placeholder=<fmt:message key="label.placeholder.description" bundle="${messages}"/>></textarea>
                    </div>



                    <div class="form-group row">
                        <label  for="image" class="col-3 col-form-label" style="margin-right: 13px"><fmt:message key="label.placeholder.image" bundle="${messages}"/></label>
                        <input type="file" name="book_image" id="image" size="50"/><br/>
                    </div>


                    <input type="hidden" name="book_location" value="storage">


                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.book.add_book" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>

                <c:choose>
                    <c:when test="${not empty sessionScope.isBookAdded && sessionScope.isBookAdded eq true}">
                        <fmt:message key="message.book_added_successfully" bundle="${messages}"/>
                    </c:when>
                    <c:when test="${not empty sessionScope.isBookAdded && sessionScope.isBookAdded eq false}">
                        <fmt:message key="message.book_was_not_added" bundle="${messages}"/>
                    </c:when>
                </c:choose><br/>

                <c:if test="${not empty sessionScope.isBookAdded}">
                    <c:remove var="isBookAdded" scope="session" />
                </c:if>
            </div>
            <div class="row">

            </div><!--/row-->
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">

                <c:choose>
                    <c:when test="${user.role.ordinal()==3}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                           bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role.ordinal()==2}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>

                </c:choose>
            </c:if>

        </div>--%>



    </div>
</div>


<footer>
    <p>© Company 2017</p>
</footer>






















<%--
<form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add_book"/>
    <fmt:message key="label.book.book_title" bundle="${messages}"/> : <input type="text" name="book_title" value="" pattern="[\d\w\W[а-яА-Я}]]+" required/><br/>

    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : <input type="text" name="book_pages" value="" pattern="\d{1,5}" required><br/>

    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : <input type="text" name="book_year" value="" pattern="\d{1,5}" required><br/>

    <fmt:message key="label.book.isbn" bundle="${messages}"/> : <input type="text" name="book_isbn" value="" required pattern="(\d+-\d+-\d+-\d+-\d+)|(\d+-\d+-\d+-\d+)"><br/>

    <fmt:message key="label.book.publisher" bundle="${messages}"/> : <select name="book_publisher">
        <c:forEach items="${publishers}" var="publisher">
            <option value="${publisher.id}">${publisher.getName()} </option>
        </c:forEach>
    </select><br/>

    <fmt:message key="label.book.genre" bundle="${messages}"/> : <br/>
    <select multiple name="book_genre" required>
    <c:forEach items="${genres}" var="genre">
    <option value="${genre.id}">${genre.getName()} </option>
    </c:forEach>
    </select><br/>

    <fmt:message key="label.book.author" bundle="${messages}"/> : <br/>
    <select multiple name="book_author" required>
    <c:forEach items="${authors}" var="author">
    <option value="${author.id}">${author.getSurname()} ${author.getName()} ${author.getPatronymic()} </option>
    </c:forEach>
    </select><br/>

    <fmt:message key="label.book.description" bundle="${messages}"/> :<br/> <textarea name="book_description" id="book_description" cols="30"
                                                                                 rows="10"></textarea><br/>

    <fmt:message key="label.book.image" bundle="${messages}"/> :
    <input type="file" name="book_image" size="50"/>
<br/>
    <input type="hidden" name="book_location" value="storage">
    <input type="submit" name="submit" value=<fmt:message key="label.button.add_book" bundle="${messages}"/> />
</form>





<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
--%>

</body>
</html>
