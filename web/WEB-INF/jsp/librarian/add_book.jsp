<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.book.add_book" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
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
                        <label for="publisher" class="col-3 col-form-label required"><fmt:message key="label.book.publisher" bundle="${messages}"/></label>
                        <div class="col-5"><select id="publisher" class="custom-select col-5"  name="book_publisher" required>
                            <c:forEach items="${publishers}" var="publisher">
                                <option value="${publisher.id}">${publisher.getName()} </option>
                            </c:forEach>
                        </select>
                        </div>
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

            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>



    </div>
</div>


<jsp:include page="../footer.jsp"/>


</body>
</html>
