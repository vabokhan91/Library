<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.ADMINISTRATOR}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.user.block_user" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

            <table class="table">
                <thead class="thead-default">
                <tr>
                    <th><fmt:message key="label.library_card" bundle="${messages}"/> </th>
                    <th><fmt:message key="label.name" bundle="${messages}"/></th>
                    <th><fmt:message key="label.surname" bundle="${messages}"/> </th>
                    <th><fmt:message key="label.patronymic" bundle="${messages}"/> </th>
                    <th><fmt:message key="label.role" bundle="${messages}"/> </th>

                </tr>
                </thead>
                <tbody>

                <c:forEach items="${not_blocked_users}" var="item">
                    <c:if test="${user.id!= item.id}">
                    <tr>

                        <td>${item.libraryCardNumber}</td>
                        <td>${item.name}</td>
                        <td>${item.surname}</td>
                        <td>${item.patronymic}</td>
                        <td>${item.role.name}</td>
                        <td><form method="post" action="/controller" accept-charset="UTF-8">
                            <input type="hidden" name="command" value="block_user"/>
                            <input type = "hidden" name = "user_id" value="${item.id}"/><br/>
                            <button class="btn btn-primary" type="submit" name="submit"><fmt:message key="label.user.block_user" bundle="${messages}"/></button>
                        </form> </td>

                    </tr>
                    </c:if>
                </c:forEach>

                </tbody>
            </table>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/></a><br/>
        </div>--%>

    </div>
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
