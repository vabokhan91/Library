<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="label.book.show_all_books" bundle="${messages}"/></title>
</head>
<body>

<table class="item-table">
    <tr>
        <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.number_of_pages" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.isbn" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.year_of_publishing" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.location" bundle="${messages}"/></th>
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
            <td><c:forEach items="${item.authors}" var="authors">
                ${authors.toString()}
            </c:forEach></td>
            <c:if test="${user.role.ordinal()==2}">
            <td>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="get_explicit_book_info"/>
                    <input type="hidden" name="book_id" value="${item.id}"/>
                    <input type="submit" name="submit" value=<fmt:message key="label.button.more_detail"
                                                                          bundle="${messages}"/>>
                </form>
            </td>

            <td>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="get_book_for_editing"/>
                    <input type="hidden" name="book_id" value="${item.id}"/>
                    <input type="submit" name="submit" value=<fmt:message key="label.book.edit_book"
                                                                          bundle="${messages}"/>/>
                </form>
            </td>
                <c:if test="${item.getLocation().getName() eq 'storage'}">
                    <td>
                        <form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="delete_book"/>
                            <input type="hidden" name="book_id" value="${item.id}"/>
                            <input type="submit" name="submit" value=<fmt:message key="label.button.book.delete_book"
                                                                                  bundle="${messages}"/>/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${item.getLocation().getName() eq 'storage'}">
                    <td>
                        <form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="to_add_order_page"/>
                            <input type="hidden" name="book_id" value="${item.id}"/>
                            <input type="submit" name="submit" value=<fmt:message key="label.button.book.add_order"
                                                                                  bundle="${messages}"/>/>
                        </form>
                    </td>
                </c:if>
            </c:if>
        </tr>
    </c:forEach>

</table>


<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                 bundle="${messages}"/> </a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu"
                                                                          bundle="${messages}"/> </a>
    </c:otherwise>
</c:choose><br/>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href="/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a><br/>

</body>
</html>
