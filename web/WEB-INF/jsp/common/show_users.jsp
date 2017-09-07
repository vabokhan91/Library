<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role != Role.ADMINISTRATOR && user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.user_information" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

            <table class="table">
                <thead class="thead-default">
                <tr>
                    <th><fmt:message key="label.library_card" bundle="${messages}"/></th>
                    <th><fmt:message key="label.name" bundle="${messages}"/></th>
                    <th><fmt:message key="label.surname" bundle="${messages}"/></th>
                    <th><fmt:message key="label.patronymic" bundle="${messages}"/></th>
                    <th><fmt:message key="label.role" bundle="${messages}"/></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="item">
                <tr>
                    <th scope="row">${item.libraryCardNumber}</th>
                    <td>${item.name}</td>
                    <td>${item.surname}</td>
                    <td>${item.patronymic}</td>
                    <td>${item.role.name}</td>

                </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="row">

            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>