<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Online order information</title>
</head>
<body>

<form method="post" action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="execute_online_order">
    <c:forEach items="${foundBook}" var="item">

        <fmt:message key="label.book.id" bundle="${messages}"/> : ${item.getId()}<br/>
        <fmt:message key="label.book.title" bundle="${messages}"/> : ${item.getTitle()}<br/>
        <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
        <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
        <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
        <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher}<br/>
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
        <select name="type_of_order">
            <option  value="subscription"><fmt:message key="label.book.subscription" bundle="${messages}"/> </option>
            <option value="reading_room"><fmt:message key="label.book.reading_room" bundle="${messages}"/> </option>
        </select><br/>


    </c:forEach>
    <input type="hidden" name="book_id" value="${foundBook.get(0).id}"/>
    <input type="hidden" name="online_order_id" value="${online_order_id}"/>
    <input type="hidden" name="library_card" value="${library_card}"/>
    <input type="hidden" name="librarian_id" value="${sessionScope.user.id}">
    <input type="submit" name="submit" value=<fmt:message key="label.button.book.add_order" bundle="${messages}"/> />
</form>


<a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu"
                                                                  bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>


</body>
</html>
