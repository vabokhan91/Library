<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.book.edit_book" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div >
                <fmt:message key="label.book.id" bundle="${messages}"/> : ${foundBook.id}<br/><br/>
                <form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="edit_book"/>
                    <input type="hidden" name="book_id" value="${foundBook.id}"/>
                    <div class="form-group row">
                        <label for="title" class="col-3 col-form-label required"> <fmt:message key="label.book.book_title" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type="text"  class="form-control" id="title" name="book_title" value="${foundBook.title}" pattern="[\w\WА-Яа-яЁё]+" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="pages" class="col-3 col-form-label required"><fmt:message key="label.book.number_of_pages" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="pages" name="book_pages" value="${foundBook.pages}" pattern="\d{1,5}"  required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="year" class="col-3 col-form-label required"><fmt:message key="label.book.year_of_publishing" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="year" name="book_year" value="${foundBook.year}" pattern="\d{1,5}" required>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="isbn" class="col-3 col-form-label required"><fmt:message key="label.book.isbn" bundle="${messages}"/></label>
                        <div class="col-5">
                            <input type = "text" class="form-control" id="isbn" name="book_isbn" value="${foundBook.isbn}" pattern="(\d+-\d+-\d+-\d+-\d+)|(\d+-\d+-\d+-\d+)" required/>
                            <small class="form-text text-muted">
                                <fmt:message key="label.isbn_example" bundle="${messages}"/>
                            </small>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="publisher" class="col-3 col-form-label required" ><fmt:message key="label.book.publisher" bundle="${messages}" /></label>
                        <div class="col-5"><select id="publisher" class="custom-select col-5"  name="book_publisher" required>
                            <option value="${foundBook.publisher.id}">${foundBook.publisher.getName()} </option>
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
                                <option value="${genre.id}"
                                <c:if test="${foundBook.getGenre().contains(genre)}">selected</c:if>>  ${genre.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="author" style="margin-right: 13px" class="col-3 col-form-label required"><fmt:message key="label.book.author" bundle="${messages}"/></label>
                        <select multiple class="form-control col-5" id="author" name="book_author" required>
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}"
                                    <c:if test="${foundBook.authors.contains(author)}">selected</c:if>> ${author.getSurname()} ${author.getName()} ${author.getPatronymic()} </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="description"><fmt:message key="label.book.description" bundle="${messages}"/></label>
                        <textarea class="form-control col-9" name="book_description" id="description" rows="4" >${foundBook.description}</textarea>
                    </div>
                    <br/>

                    <div class="form-group row">
                        <label  for="image" class="col-sm-2 col-form-label" style="margin-right: 13px"><fmt:message key="label.book.upload_new_image" bundle="${messages}"/></label>
                        <input type="file" name="book_image" id="image" size="50"/><br/>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.book.edit_book" bundle="${messages}"/></button>
                        </div>
                    </div>

                </form>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


    </div>
</div>


<jsp:include page="../footer.jsp"/>
</body>
</html>
