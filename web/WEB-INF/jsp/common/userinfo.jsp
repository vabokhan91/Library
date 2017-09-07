<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
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
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="row">
                <c:forEach items="${foundUser}" var="item">
                    <div class="col-10">
                        <div class="lib-parent-book-info">
                            <h2>${item.surname.concat(' ').concat(item.name).concat(' ').concat(item.patronymic)}</h2>
                            <div>
                                <img class="lib-main-book-img" src="data:image/jpg;base64,${item.photo}"/></div>
                            <div>
                                <fmt:message key="label.library_card" bundle="${messages}"/> : ${item.libraryCardNumber}
                                <br/>
                                <fmt:message key="label.address" bundle="${messages}"/> : ${item.address}
                                <br/>
                                <fmt:message key="label.role" bundle="${messages}"/> : ${item.role.name}<br/>
                                <fmt:message key="label.user.login" bundle="${messages}"/> : ${item.login}<br/>
                                <fmt:message key="label.mobile_phone" bundle="${messages}"/> : ${item.mobilePhone}<br/>
                            </div>
                        </div>
                        <div>
                            <form class="form-action" action="/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="get_explicit_user_info"/>
                                <input type="hidden" name="library_card" value="${item.libraryCardNumber}"/><br/>
                                <button class="btn btn-secondary" type="submit" ><fmt:message
                                        key="label.show_explicit_info"
                                        bundle="${messages}"/></button>
                            </form>

                            <form class="form-action" action="/controller" accept-charset="UTF-8">
                                <input type="hidden" name="command" value="get_user_for_editing"/>
                                <input type="hidden" name="find_query_value" value="${item.libraryCardNumber}"/><br/>
                                <button class="btn btn-secondary" type="submit" name="submit"><fmt:message
                                        key="label.user.edit_user"
                                        bundle="${messages}"/></button>
                            </form>

                            <c:if test="${user.role ==Role.ADMINISTRATOR}">
                                <td>
                                    <form method="post" class="form-action" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="remove_user"/>
                                        <input type="hidden" name="user_id" value="${item.id}"/><br/>
                                        <button class="btn btn-secondary" type="submit" name="submit"><fmt:message key="label.remove_user"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>

                        </div>

                    </div>

                </c:forEach>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

    </div>

</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
