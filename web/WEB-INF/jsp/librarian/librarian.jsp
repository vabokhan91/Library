<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
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
    <title>Librarian</title>
</head>
<body>

<a href="/controller?command=to_add_user_page"><fmt:message key="label.add_user" bundle="${messages}"/> </a><br/>

<a href="/controller?command=get_all_users"><fmt:message key="label.show_all_users" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_Find_User_Page" ><fmt:message key="label.button.find_user" bundle="${messages}"/> </a><br/>

<a href="/controller?command=get_all_books" ><fmt:message key="label.book.show_all_books" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.book.find_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_author_page" ><fmt:message key="label.book.add_author" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_add_book_page" ><fmt:message key="label.book.add_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.button.book.delete_book" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_book_page" ><fmt:message key="label.button.book.add_order" bundle="${messages}"/> </a><br/>

<a href="/controller?command=to_find_user_orders_page" ><fmt:message key="label.button.book.return_book" bundle="${messages}"/> </a><br/>

</body>
</html>
