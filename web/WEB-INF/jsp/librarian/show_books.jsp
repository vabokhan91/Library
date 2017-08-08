<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>Books</title>
</head>
<body>

<table class="item-table">
    <tr>
        <th><fmt:message key="label.book.id" bundle="${messages}"/> </th>
        <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.number_of_pages" bundle="${messages}"/> </th>
        <th><fmt:message key="label.book.isbn" bundle="${messages}"/> </th>
        <th><fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> </th>
        <th><fmt:message key="label.book.location" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.publisher" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.genre" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.author" bundle="${messages}"/></th>

    </tr>

    <c:forEach items="${books}" var="item">
        <tr>


            <td>${item.id}</td>
            <td>${item.title}</td>
            <td>${item.pages}</td>
            <td>${item.isbn}</td>
            <td>${item.year}</td>
            <td>${item.location}</td>
            <td>${item.publisher}</td>
            <td><c:forEach items="${item.genre}" var="genres">
                ${genres.getName()}
            </c:forEach> </td>
            <td><c:forEach items="${item.authors}" var="authors">
                ${authors.toString()}
            </c:forEach> </td>

            <%--<td><form method="post" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_explicit_user_info"/>
                <input type = "hidden" name = "library_card" value="${item.id}"/><br/>
                <input type="submit" name="submit" value=<fmt:message key="label.button.show_explicit_info" bundle="${messages}"/>/>
            </form></td>

            <td><form method="post" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_user_for_editing"/>
                <input type = "hidden" name = "library_card" value="${item.id}"/><br/>
                <input type="submit" name="submit" value=<fmt:message key="label.user.edit_user" bundle="${messages}"/>/>
            </form> </td>
--%>
        </tr>
    </c:forEach>

</table>


<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a>


</body>
</html>
