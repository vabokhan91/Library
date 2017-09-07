<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bokhan.entity.Role" %>
<%@ page import="by.epam.bokhan.entity.Location" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role!=Role.CLIENT}">
    <jsp:forward page="/index.jsp"/>
</c:if>
<html lang="${language}">
<head>
    <title><fmt:message key="label.online_order" bundle="${messages}"/></title>
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

                    <div class="col-10">
                        <div class="lib-parent-book-info"><h2>${foundBook.title}</h2>
                            <div>
                                <img class="lib-main-book-img" src="data:image/jpg;base64,${foundBook.image}"/></div>
                            <div>
                                <fmt:message key="label.book.author" bundle="${messages}"/> : <c:forEach items="${foundBook.authors}"
                                                                                                         var="author">
                                ${author.surname.concat(' ').concat(author.name.charAt(0)).concat('. ').concat(author.patronymic.charAt(0)).concat(';')}</c:forEach><br/>
                                <fmt:message key="label.book.genre" bundle="${messages}"/> :
                                <c:forEach items="${foundBook.genre}" var="genres">
                                    ${genres.getName()}
                                </c:forEach><br/>
                                <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${foundBook.isbn}<br/>
                                <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${foundBook.year}<br/>
                                <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${foundBook.pages}<br/>
                                <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${foundBook.publisher.name}<br/><br/>
                            </div>
                        </div>
                        <fmt:message key="label.book.description" bundle="${messages}"/> : ${foundBook.description}<br/><br/>
                        <div>
                            <c:if test="${foundBook.location == Location.STORAGE && user.role==Role.CLIENT}">
                                <td>
                                    <form method="post" action="/controller" accept-charset="UTF-8">
                                        <input type="hidden" name="command" value="add_online_order"/>
                                        <input type="hidden" name="book_id" value="${foundBook.id}"/>
                                        <input type="hidden" name="library_card" value="${sessionScope.user.libraryCardNumber}"/>
                                        <button class="btn btn-secondary" type="submit" name="submit"><fmt:message key="label.book.order_command"
                                                                                                                   bundle="${messages}"/></button>
                                    </form>
                                </td>
                            </c:if>
                        </div>
                    </div>
            </div>
        </div>

        <jsp:include page="../navigation_sidebar.jsp"/>


    </div>

</div>




<jsp:include page="../footer.jsp"/>






</body>
</html>
