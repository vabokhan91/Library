<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Add Book</title>
</head>
<body>
<form method="post" action="/controller" accept-charset="UTF-8" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add_book"/>
    <fmt:message key="label.book.book_title" bundle="${messages}"/> : <input type="text" name="book_title" value="" required/><br/>

    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : <input type="text" name="book_pages" value="" required><br/>

    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : <input type="text" name="book_year" value="" required><br/>

    <fmt:message key="label.book.isbn" bundle="${messages}"/> : <input type="text" name="book_isbn" value="" required pattern="(\d+-\d+-\d+-\d+-\d+)|(\d+-\d+-\d+-\d+)"><br/>

    <fmt:message key="label.book.publisher" bundle="${messages}"/> : <select name="book_publisher">
        <c:forEach items="${publishers}" var="publisher">
            <option value="${publisher.id}">${publisher.getName()} </option>
        </c:forEach>
    </select><br/>

    <fmt:message key="label.book.genre" bundle="${messages}"/> : <br/>
    <select multiple name="book_genre">
    <c:forEach items="${genres}" var="genre">
    <option value="${genre.id}">${genre.getName()} </option>
    </c:forEach>
    </select><br/>

    <fmt:message key="label.book.author" bundle="${messages}"/> : <br/>
    <select multiple name="book_author">
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

<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>

</body>
</html>
