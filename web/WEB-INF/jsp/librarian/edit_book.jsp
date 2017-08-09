<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Edit book</title>
</head>
<body>



<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="edit_book"/>
<c:forEach items="${foundBook}" var="item">
    <fmt:message key="label.book.id" bundle="${messages}"/> : ${item.id}<br/>
    <fmt:message key="label.book.title" bundle="${messages}"/> : <input type="text" name="book_title" value="${item.title}"/> <br/>
    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : <input type="text" name="book_pages" value="${item.pages}"/> <br/>
    <fmt:message key="label.book.isbn" bundle="${messages}"/> : <input type="text" name="book_isbn" value="${item.isbn}"/> <br/>
    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : <input type="text" name="book_year" value="${item.year}"/> <br/>
    <input type="hidden" name="book_id" value="${item.id}">

    <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}
    <select name="book_publisher">
        <option value="${item.publisher.id}">${item.publisher.getName()} </option>
        <c:forEach items="${publishers}" var="publisher">
            <option value="${publisher.id}">${publisher.getName()} </option>
        </c:forEach>
    </select>
    <br/>
    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <br/>
    <select name="book_genre" multiple>
        <c:forEach items="${genres}" var="genre">
            <option value="${genre.id}"
            <c:if test="${item.getGenre().contains(genre)}">selected</c:if>>  ${genre.getName()}</option>
        </c:forEach>
    </select>
    <br/>

    <fmt:message key="label.book.author" bundle="${messages}"/> :
    <br/>
    <select name="book_author" multiple>
        <c:forEach items="${authors}" var="author">
            <option value="${author.id}"
                    <c:if test="${item.getAuthors().contains(author)}">selected</c:if>>  ${author.toString()}</option>
        </c:forEach>
    </select>
    <br/>
</c:forEach>

    <input type="submit" name="submit" value="<fmt:message key="label.user.edit_user" bundle="${messages}"/>" />
</form>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>



</body>
</html>
