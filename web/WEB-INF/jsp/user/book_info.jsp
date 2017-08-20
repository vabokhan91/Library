<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1 && empty user.role}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><fmt:message key="label.book.book_information" bundle="${messages}"/> </title></head>

<body>
<h3><fmt:message key="label.book.book_information" bundle="${messages}"/></h3>

<table class="item-table">
    <tr>
        <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.number_of_pages" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.year_of_publishing" bundle="${messages}"/></th>

        <th><fmt:message key="label.book.author" bundle="${messages}"/></th>

    </tr>

    <c:forEach items="${foundBook}" var="item">
        <tr>

            <td>${item.title}</td>
            <td>${item.pages}</td>
            <td>${item.year}</td>
            <td><c:forEach items="${item.authors}" var="authors">
                ${authors.toString()}
            </c:forEach></td>
            <td><c:forEach items="${item.genre}" var="genres">
                ${genres.getName()}
            </c:forEach></td>

            <td>
                <form method="post" action="/controller" accept-charset="UTF-8">
                    <input type="hidden" name="command" value="get_explicit_book_info"/>
                    <input type="hidden" name="book_id" value="${item.id}"/>
                    <input type="submit" name="submit" value=<fmt:message key="label.button.more_detail"
                                                                          bundle="${messages}"/>>
                </form>
            </td>

            <c:if test="${item.getLocation().getName() eq 'storage' && user.role.ordinal()==1}">
                <td>
                    <form method="post" action="/controller" accept-charset="UTF-8">
                        <input type="hidden" name="command" value="to_add_online_order_page"/>
                        <input type="hidden" name="book_id" value="${item.id}"/>
                        <input type="hidden" name="type_of_search" value="by_id">
                        <input type="submit" name="submit" value=<fmt:message key="label.button.book.make_online_order"
                                                                              bundle="${messages}"/>/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>


</table><br/>


<c:choose>
    <c:when test="${empty user.role.ordinal()}">
        <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
</c:choose>

<a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

<a href="/controller?command=logout">Log Out</a>
</body>
</html>
