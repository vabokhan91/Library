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
    <title>Explicit info</title>
</head>
<body>


<fmt:message key="label.library_card" bundle="${messages}"/> : ${foundUser.id}<br/>
<fmt:message key="label.name" bundle="${messages}"/> : ${foundUser.name} <br/>
<fmt:message key="label.surname" bundle="${messages}"/> : ${foundUser.surname} <br/>
<fmt:message key="label.patronymic" bundle="${messages}"/> : ${foundUser.patronymic} <br/>
<fmt:message key="label.role" bundle="${messages}"/> : ${foundUser.role} <br/>
<fmt:message key="label.address" bundle="${messages}"/> : ${foundUser.address} <br/>
<fmt:message key="label.login" bundle="${messages}"/> : ${foundUser.login} <br/>
<fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${foundUser.mobilePhone} <br/>

<hr/>

<table class="item-table">
    <tr>
        <th><fmt:message key="label.order.id" bundle="${messages}"/> </th>
        <th><fmt:message key="label.book.id" bundle="${messages}"/></th>
        <th><fmt:message key="label.book.title" bundle="${messages}"/> </th>
        <th><fmt:message key="label.order.order_date" bundle="${messages}"/> </th>
        <th><fmt:message key="label.order.expiration_date" bundle="${messages}"/> </th>
        <th><fmt:message key="label.order.return_date" bundle="${messages}"/></th>

    </tr>

    <c:forEach items="${foundUser.orders}" var="item">
        <tr>
            <td>${item.id}</td>
            <td>${item.getBook().id}</td>
            <td>${item.getBook().title}</td>
            <td>${item.orderDate}</td>
            <td>${item.expirationDate}</td>
            <td>${item.returnDate}</td>

        </tr>
    </c:forEach>

</table>





<a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/></a>

</body>
</html>
