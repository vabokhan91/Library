<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=1 && user.role.ordinal()!=2}">
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
            <th><fmt:message key="label.book.order_status" bundle="${messages}"/></th>

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
                <td>${item.status}</td>

                <c:if test="${user.role.ordinal()==2 && item.status eq 'booked'}">
                    <td>
                        <form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="to_execute_online_order_page"/>
                            <input type="hidden" name="online_order_id" value="${item.id}"/>
                            <input type="hidden" name="type_of_search" value="by_id">
                            <input type="hidden" name="book_id" value="${item.book.id}"/>
                            <input type="hidden" name="library_card" value="${item.user.libraryCardNumber}"/>
                            <input type="submit" name="submit" value=<fmt:message key="label.button.book.execute_online_order"
                                                                                  bundle="${messages}"/>/>
                        </form>
                    </td>
                </c:if>


                <%--<c:if test="${item.getLocation().getName() eq 'storage'}">
                    <td>
                        <form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="to_add_order_page"/>
                            <input type="hidden" name="book_id" value="${item.id}"/>
                            <input type="hidden" name="type_of_search" value="by_id">
                            <input type="submit" name="submit" value=<fmt:message key="label.button.book.add_order"
                                                                                  bundle="${messages}"/>/>
                        </form>
                    </td>
                </c:if>--%>



                <c:if test="${empty item.returnDate}">
                    <td>
                        <form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="cancel_online_order"/>
                            <input type="hidden" name="order_id" value="${item.id}"/>
                            <input type="hidden" name="book_id" value="${item.book.id}"/>
                            <input type="submit" name="submit" value=<fmt:message key="label.button.book.cancel_online_order"
                                                                                  bundle="${messages}"/>/>
                        </form>
                    </td>
                </c:if>

            </tr>
        </c:forEach>

    </table>


    <c:choose>
        <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq true}">
            <fmt:message key="label.book.online_order_cancelled" bundle="${messages}"/>
        </c:when>
        <c:when test="${not empty sessionScope.isOnlineOrderCancelled && sessionScope.isOnlineOrderCancelled eq false}">
            <fmt:message key="label.book.online_order_not_cancelled" bundle="${messages}"/>
        </c:when>
    </c:choose><br/>

    <c:if test="${not empty sessionScope.isOnlineOrderCancelled}">
        <c:remove var="isOnlineOrderCancelled" scope="session" />
    </c:if>

    <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

    <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>

</head>
<body>

</body>
</html>

