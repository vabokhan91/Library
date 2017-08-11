<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Online order</title>
</head>
<body>


<c:forEach items="${foundBook}" var="item">
    <fmt:message key="label.book.title" bundle="${messages}"/> : ${item.title}<br/>
    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
    <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
    <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/>
    <fmt:message key="label.book.author" bundle="${messages}"/> :
    <c:forEach items="${item.authors}" var="authors">
        ${authors.toString()}
    </c:forEach><br/>
    <fmt:message key="label.book.genre" bundle="${messages}"/> :
    <c:forEach items="${item.genre}" var="genres">
        ${genres.getName()}
    </c:forEach><br/>
    <form method="post" action="/controller" accept-charset="UTF-8">
        <input type="hidden" name="command" value="add_online_order"/>
        <input type="hidden" name="book_id" value="${item.id}"/>
        <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
        <input type="submit" name="submit" value=<fmt:message key="label.button.book.order"
                                                              bundle="${messages}"/>>
    </form>
</c:forEach><br/>


<a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
<br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>
<br/>


</body>
</html>
