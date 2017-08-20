<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.librarian_page" bundle="${messages}"/> </title>
</head>
<body>

<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>

<form action="/controller" accept-charset="UTF-8">
    <input type="hidden" name="command" value="to_add_user_page"/>
    <input type="submit" name="submit" value=<fmt:message key="button.add_user" bundle="${messages}"/>/>
</form>

<a href="/controller?command=get_all_users"><fmt:message key="label.show_all_users" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_Find_User_Page" ><fmt:message key="label.button.find_user" bundle="${messages}"/> </a><br/>

<a href="/controller?command=get_all_books" ><fmt:message key="label.book.show_all_books" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.book.find_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_author_page" ><fmt:message key="label.book.add_author" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_delete_author_page" ><fmt:message key="label.book.delete_author" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_genre_page" ><fmt:message key="label.button.add_genre" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_delete_genre_page" ><fmt:message key="label.button.delete_genre" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_publisher_page" ><fmt:message key="label.book.add_publisher" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_delete_publisher_page" ><fmt:message key="label.book.delete_publisher" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_book_page" ><fmt:message key="label.book.add_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.button.book.delete_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.button.book.add_order" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_user_orders_page" ><fmt:message key="label.button.book.return_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_user_online_orders" ><fmt:message key="label.order.execute_online_order" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href = "/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>

</body>
</html>
