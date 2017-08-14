<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html lang="${language}">
<head><title>
    <fmt:message key="label.login" bundle="${messages}"/> </title></head>
<body>
<form method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
        <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
    </select>
</form>

<c:choose>
    <c:when test="${user==null}">
        <ctg:welcome-tag />
        <form method = "POST" action = "/controller">
            <input type = "hidden" name = "command" value = "login"/>
            <fmt:message key="label.login" bundle="${messages}"/> : <br/>
            <input type = "text" name = "login" value=""/><br/>
            <fmt:message key="label.password" bundle="${messages}"/> : <br/>
            <input type = "password" name = "password" value=""/>
            <br/>
                ${errorLoginPassMessage}
            <c:if test="${not empty sessionScope.errorLoginPassMessage}">
                <c:remove var="errorLoginPassMessage" scope="session" />
            </c:if>
            <br/>
            <input type="submit" name="submit" value=<fmt:message key="label.button.login" bundle="${messages}"/> />
        </form>
        <br />
        <form action="/controller">
            <input type = "hidden" name = "command" value = "to_registration_page"/>
            <input type="submit" name="submit" value=<fmt:message key="label.registration" bundle="${messages}"/> />
        </form>
    </c:when>
    <c:otherwise>
        <ctg:welcome-tag/>
        <br />
    </c:otherwise>
</c:choose>


<div>
    <ul><li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="РОМАН">
            <input type="submit" name="submit" value="Роман" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Автобиография">
            <input type="submit" name="submit" value="Автобиография" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Бизнес">
            <input type="submit" name="submit" value="Бизнес" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Биография">
            <input type="submit" name="submit" value="Биография" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Детектив">
            <input type="submit" name="submit" value="Детектив" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Детская_литература">
            <input type="submit" name="submit" value="Детская литература" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Драма">
            <input type="submit" name="submit" value="Драма" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Информационные_технологии">
            <input type="submit" name="submit" value="Информационные технологии" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="История">
            <input type="submit" name="submit" value="История" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Комедия">
            <input type="submit" name="submit" value="Комедия" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Короткая_история">
            <input type="submit" name="submit" value="Короткая история" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Миф">
            <input type="submit" name="submit" value="Миф" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Поэма">
            <input type="submit" name="submit" value="Поэма" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Приключения">
            <input type="submit" name="submit" value="Приключения" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Пьеса">
            <input type="submit" name="submit" value="Пьеса" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Религия">
            <input type="submit" name="submit" value="Религия" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Сказка">
            <input type="submit" name="submit" value="Сказка" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Трагедия">
            <input type="submit" name="submit" value="Трагедия" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Фантастика">
            <input type="submit" name="submit" value="Фантастика" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Эпос">
            <input type="submit" name="submit" value="Эпос" />
        </form>
    </li>
    <li>
        <form action="/controller">
            <input type="hidden" name="command" value="find_book_by_genre">
            <input type="hidden" name="genre_name" value="Эссе">
            <input type="submit" name="submit" value="Эссе" />
        </form>
    </li>
    </ul>
</div>

<c:forEach items="${foundBooks}" var="item">
    <div>
        ${item.title}
        ${item.pages}
        ${item.isbn}
        ${item.year}
        ${item.location}
        ${item.publisher.name}
            ${item.description}
        <c:forEach items="${item.genre}" var="genres">
            ${genres.getName()}
        </c:forEach>
        <c:forEach items="${item.authors}" var="authors">
            ${authors.toString()}
        </c:forEach>
    </div>
</c:forEach>



<c:choose>
    <c:when test="${user.role.ordinal()==3}">
        <a href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==2}">
        <a href="/controller?command=to_librarian_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
    <c:when test="${user.role.ordinal()==1}">
        <a href="/controller?command=to_user_main_page"><fmt:message key="label.button.to_main_menu" bundle="${messages}"/> </a>
    </c:when>
</c:choose>


</body></html>

