<%@ page language="java" contentType="text/html; charset = UTF-8" pageEncoding="UTF-8" session="true" %>
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
<html>
<head>
    <title><fmt:message key="label.user_information" bundle="${messages}"/></title></head>

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
                        <div class="parent-book-info">
                            <h2>${item.surname.concat(' ').concat(item.name).concat(' ').concat(item.patronymic)}</h2>
                            <div>
                                <img class="main-book-img" src="data:image/jpg;base64,${item.photo}"/></div>
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
                    <!--/span-->
                </c:forEach>
            </div><!--/row-->
        </div><!--/span-->

        <jsp:include page="../navigation_sidebar.jsp"/>

        <%--<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
            <c:if test="${not empty user}">
                <c:choose>
                    <c:when test="${user.role==Role.ADMINISTRATOR}">
                        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                                           bundle="${messages}"/> </a><br/>
                    </c:when>
                    <c:when test="${user.role==Role.LIBRARIAN}">
                        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/>
                    </c:when>

                </c:choose>
            </c:if>

        </div>--%>
    </div><!--/row-->

    <hr>

    <footer>
        <p>© Company 2017</p>
    </footer>

</div>

</body>
</html>
