<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html lang="${language}">
<head>
    <title><fmt:message key="label.main_page" bundle="${messages}"/></title>
    <%@include file="../common_imports.jsp"%>
</head>
<body background="image/books-484766_1920.jpg">

    <%@include file="../header.jsp"%>

    <div class="container">
        <div class="row row-offcanvas row-offcanvas-right">
            <div class="col-12 col-md-9">
                <p class="float-right d-md-none">
                    <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
                </p>
                <div class="jumbotron">
                    <ctg:welcome-tag />
                    <h6 class="form-signin-heading"><fmt:message key="label.login_to_account" bundle="${messages}"/></h6>
                    <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap123. Try some
                        responsive-range viewport sizes to see it in action.</p>
                </div>

                <div class="row">
                    <c:forEach items="${foundBooks}" var="item">
                        <div class="col-12">
                            <div class="lib-parent-book-info">
                                <div>
                                    <img class="lib-main-book-img" src="data:image/jpg;base64,${item.image}"/>
                                </div>
                                <div>
                                    <h2>${item.title}</h2>
                                    <fmt:message key="label.book.author" bundle="${messages}"/> : <c:forEach items="${item.authors}"
                                                                                                             var="author">
                                    ${author.surname.concat(' ').concat(author.name.charAt(0)).concat('. ').concat(author.patronymic.charAt(0)).concat(';')}</c:forEach><br/>
                                    <fmt:message key="label.book.genre" bundle="${messages}"/> :
                                    <c:forEach items="${item.genre}" var="genres">
                                        ${genres.getName()}
                                    </c:forEach><br/>
                                    <fmt:message key="label.book.isbn" bundle="${messages}"/> : ${item.isbn}<br/>
                                    <fmt:message key="label.book.year_of_publishing" bundle="${messages}"/> : ${item.year}<br/>
                                    <fmt:message key="label.book.number_of_pages" bundle="${messages}"/> : ${item.pages}<br/>
                                    <fmt:message key="label.book.publisher" bundle="${messages}"/> : ${item.publisher.name}<br/>
                                </div>
                            </div>
                            <div>
                                <form action="/controller" accept-charset="UTF-8">
                                    <input type="hidden" name="command" value="get_explicit_book_info"/>
                                    <input type="hidden" name="book_id" value="${item.id}"/>
                                    <button class="btn btn-secondary" type="submit" name="submit"><fmt:message
                                            key="label.button.more_detail"
                                            bundle="${messages}"/></button>
                                </form>
                            </div>
                        </div>

                    </c:forEach>
                </div>
            </div>
            <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
                <div class="list-group">
                    <a href="/controller?command=find_book_by_genre&genre_name=Биография" class="list-group-item"><fmt:message key="label.genre.biography" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Детектив" class="list-group-item"><fmt:message key="label.genre.detective" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Детская литература" class="list-group-item"><fmt:message key="label.genre.children" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Автобиография" class="list-group-item"><fmt:message key="label.genre.autobiography" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Бизнес" class="list-group-item"><fmt:message key="label.genre.business" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Драма" class="list-group-item"><fmt:message key="label.genre.drama" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Информационные технологии" class="list-group-item"><fmt:message key="label.genre.it" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=История" class="list-group-item"><fmt:message key="label.genre.history" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Комедия" class="list-group-item"><fmt:message key="label.genre.comedy" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Короткая история" class="list-group-item"><fmt:message key="label.genre.short_story" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Миф" class="list-group-item"><fmt:message key="label.genre.myth" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Поэма" class="list-group-item"><fmt:message key="label.genre.poem" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Приключения" class="list-group-item"><fmt:message key="label.genre.adventure" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Пьеса" class="list-group-item"><fmt:message key="label.genre.play" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Религия" class="list-group-item"><fmt:message key="label.genre.religion" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Роман" class="list-group-item "><fmt:message key="label.genre.novel" bundle="${messages}"/> </a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Сказка" class="list-group-item"><fmt:message key="label.genre.tale" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Трагедия" class="list-group-item"><fmt:message key="label.genre.tradegy" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Фантастика" class="list-group-item"><fmt:message key="label.genre.fantasy" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Эпос" class="list-group-item"><fmt:message key="label.genre.epos" bundle="${messages}"/></a>
                    <a href="/controller?command=find_book_by_genre&genre_name=Эссе" class="list-group-item"><fmt:message key="label.genre.essay" bundle="${messages}"/></a>

                </div>
            </div>
        </div>

    </div>
    <jsp:include page="../footer.jsp"/>
</body>
</html>

