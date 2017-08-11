<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=2 && user.role.ordinal()!=1}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html>
<head>
    <title>User orders</title>


        <fmt:message key="label.library_card" bundle="${messages}"/> : ${userOrders.get(0).user.id}<br/>

        <fmt:message key="label.name" bundle="${messages}"/> : ${userOrders.get(0).user.name}<br/>

        <fmt:message key="label.surname" bundle="${messages}"/> : ${userOrders.get(0).user.surname}<br/>

        <fmt:message key="label.patronymic" bundle="${messages}"/> : ${userOrders.get(0).user.patronymic}<br/>

        <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${userOrders.get(0).user.mobilePhone}<br/>

        <fmt:message key="label.book.user_orders" bundle="${messages}"/> : <br/>

    <hr/>

    <table class="item-table">
        <tr>
            <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.title" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.isbn" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.order_date" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.expiration_date" bundle="${messages}"/></th>
            <th><fmt:message key="label.book.return_date" bundle="${messages}"/></th>
        </tr>

        <c:forEach items="${userOrders}" var="item">
            <tr>
                <td>${item.book.id}
                <td>${item.book.title}</td>
                <td>${item.book.isbn}</td>
                <td>${item.orderDate}</td>
                <td>${item.expirationDate}</td>
                <td>${item.returnDate}</td>
                <c:if test="${user.role.ordinal() == 2}">
                <c:choose>
                    <c:when test="${empty item.returnDate}">
                        <td>
                            <form method="post" action="/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="return_book"/>
                                <input type="hidden" name="book_id" value="${item.book.id}"/>
                                <input type="hidden" name="order_id" value="${item.id}"/>
                                <input type="submit" name="submit" value="<fmt:message key="label.button.book.return_book" bundle="${messages}"/>"/>
                            </form>
                        </td>
                    </c:when>
                </c:choose>
                </c:if>

            </tr>
        </c:forEach>

    </table>


    <c:choose>
        <c:when test="${user.role.ordinal()==2}">
            <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
        </c:when>
        <c:when test="${user.role.ordinal()==1}">
            <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
        </c:when>
    </c:choose>

    <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

</head>
<body>

</body>
</html>
