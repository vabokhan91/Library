<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.ADMINISTRATOR}">
    <jsp:forward page="/index.jsp"/>
</c:if>

<html>
<head>
    <title><fmt:message key="label.administrator_page" bundle="${messages}"/></title></head>

<body background="image/books-484766_1920.jpg">

<jsp:include page="../header.jsp"/>

<div class="container">
    C:\Users\vbokh\.IntelliJIdea2017.1\config\javascript\extLibs\http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.11.0_umd_popper.js

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some
                    responsive-range viewport sizes to see it in action.</p>
            </div>
            <div class="row">

            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <div><ctg:welcome-tag/></div><br/>
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message
                    key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <br/>

            <div class="list-group">
                <a href="/controller?command=to_add_user_page" class="list-group-item"><fmt:message key="label.add_user" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_page" class="list-group-item"><fmt:message key="label.remove_user" bundle="${messages}"/></a>
                <a href="/controller?command=get_all_users" class="list-group-item"><fmt:message key="label.show_all_users" bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_page" class="list-group-item"><fmt:message key="label.button.find_user"
                                                                                                     bundle="${messages}"/></a>
                <a href="/controller?command=to_find_user_page" class="list-group-item"><fmt:message key="label.user.edit_user" bundle="${messages}"/></a>
                <a href="/controller?command=get_not_blocked_users" class="list-group-item"><fmt:message key="label.user.block_user" bundle="${messages}"/></a>
                <a href="/controller?command=get_blocked_users" class="list-group-item"><fmt:message key="label.user.unblock_user" bundle="${messages}"/></a>
            </div>
        </div><!--/span-->
    </div><!--/row-->
    <br/>

</div>

<hr>

<footer>
    <p>© Company 2017</p>
</footer>

</body>
</html>