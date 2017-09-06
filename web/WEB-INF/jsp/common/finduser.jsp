<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.config" var="config"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3 && user.role.ordinal()!=2}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.button.find_user" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

<%@include file="../header.jsp"%>

<br/>

<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">

            <div class="row">

                <div class="col-lg-6">
                    <div class="input-group">

                        <form class="lib-search-form form-inline" action="/controller">
                            <input type="hidden" name="command" value="find_user">
                            <div><fmt:message key="label.enter_library_card_or_surname" bundle="${messages}"/> :</div><br/>
                            <input type="text" class="form-control" name="find_query_value" value="" placeholder=<fmt:message key="label.library_card_or_surname" bundle="${messages}"/> required>
                            <input type="submit" class="btn btn-secondary" value="<fmt:message key="label.button.find" bundle="${messages}"/> ">
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
            <c:if test="${not empty user}">

                <ctg:is-admin>
                    <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                       bundle="${messages}"/> </a>
                </ctg:is-admin>
                <ctg:is-librarian>
                    <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                            key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                </ctg:is-librarian>

            </c:if>

        </div>--%>

    </div>
</div>


<jsp:include page="../footer.jsp"/>

</body>
</html>
