<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
                                                   integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="css/library.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
            integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
            integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
            crossorigin="anonymous"></script>
    <title><fmt:message key="label.user.block_user" bundle="${messages}"/></title>
</head>
<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>


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

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/></a><br/>
        </div><!--/span-->

    </div>
</div>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>
