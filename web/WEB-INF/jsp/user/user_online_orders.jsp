<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
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
    <title>User online orders</title>


    <fmt:message key="label.book.online_orders" bundle="${messages}"/> : <br/>

    <hr/>

    <table class="item-table">
        <tr>
            <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.isbn" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.author" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.order_date" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.expiration_online_order_date" bundle="${messages}"/></th>

        </tr>

        <c:forEach items="${userOrders}" var="item">
            <tr>

                <td>${item.book.title}</td>
                <td>${item.book.isbn}</td>
                <td><c:forEach items="${item.book.authors}" var="author">
                        ${author}
                    </c:forEach>
                </td>
                <td>${item.orderDate}</td>
                <td>${item.expirationDate}</td>

            </tr>
        </c:forEach>

    </table>

    <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

    <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

</head>
<body>

</body>
</html>

