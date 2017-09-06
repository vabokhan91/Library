<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.LIBRARIAN}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.find_user_online_orders" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-12 col-md-9">
            <div class="row">
                <div class="col-lg-6">
                    <div class="input-group">

                        <form class="lib-search-form form-inline" action="/controller">
                            <input type="hidden" name="command" value="to_get_online_orders_page">

                            <div><fmt:message key="label.book.enter_users_library_card" bundle="${messages}"/> </div><br/>
                            <input class="form-control" type="text" name="library_card" value="" placeholder=<fmt:message key="label.placeholder.enter_library_card" bundle="${messages}"/> required pattern="\d{1,5}">

                            <input class="btn btn-secondary" type="submit" value="<fmt:message key="label.find" bundle="${messages}"/> ">
                        </form>

                    </div>
                </div>
            </div>
            <br>

        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                    key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>

        </div>--%>

    </div>
</div>



<jsp:include page="../footer.jsp"/>
</body></html>