<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="css/library.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</head>
<body background="image/books-484766_1920.jpg">

<nav class="lib-navbar navbar fixed-top navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Library</a>

    <form class="form-inline">
        <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>

    <form method="post">
        <div class="btn-group" data-toggle="buttons">
            <label class="btn btn-secondary btn-sm ${language == "en_US" ? "active" : ""}">
                <input type="radio" name="language" value="en_US" autocomplete="off" onchange="submit()"> English
            </label>
            <label class="btn btn-secondary btn-sm ${language == "ru_RU" ? "active" : ""}">
                <input type="radio" name="language" value="ru_RU" autocomplete="off" onchange="submit()"> Русский
            </label>
        </div>
    </form>
</nav>

<div class="container">

    <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-12 col-md-9">
            <p class="float-right d-md-none">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas">Toggle nav</button>
            </p>
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some responsive-range viewport sizes to see it in action.</p>
            </div>
            <div class="row">
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
                <div class="col-6 col-lg-4">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
                </div><!--/span-->
            </div><!--/row-->
        </div><!--/span-->

        <div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
            <div><ctg:welcome-tag/></div>
            <c:choose>
                <c:when test="${empty user}">
            <form class="form-signin" method = "POST" action = "/controller">
                <h6 class="form-signin-heading"><fmt:message key="label.login_to_account" bundle="${messages}"/></h6>
                <input type = "hidden" name = "command" value = "login"/>
                <label for="inputEmail" class="sr-only"> <fmt:message key="label.login" bundle="${messages}"/></label>
                <input type="text" name="login" id="inputEmail" class="form-control" placeholder="<fmt:message key="label.login.login" bundle="${messages}"/>" required="" autofocus="">
                <label for="inputPassword" class="sr-only"><fmt:message key="label.password" bundle="${messages}"/></label>
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="<fmt:message key="label.password" bundle="${messages}"/>" required="">
                <input type="submit" name="login" class="btn btn-lg btn-primary btn-block" value=<fmt:message key="label.login" bundle="${messages}"/>>
                ${errorLoginPassMessage}
            </form>
            <form action="/controller" id="register">
                   <input type = "hidden" name = "command" value = "to_registration_page"/>
                   <input type="submit" name="submit" class="btn btn-lg btn-primary btn-block" value=<fmt:message key="label.registration" bundle="${messages}"/> />
            </form>
            <c:if test="${not empty sessionScope.errorLoginPassMessage}">
                <c:remove var="errorLoginPassMessage" scope="session" />
            </c:if>
            </c:when>
                <c:otherwise>
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
                </c:otherwise>
            </c:choose>


            <div class="list-group">
                <a href="#" class="list-group-item active">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
                <a href="#" class="list-group-item">Link</a>
            </div>
        </div><!--/span-->
    </div><!--/row-->

    <hr>

    <footer>
        <p>© Company 2017</p>
    </footer>

</div>

<%--<ctg:welcome-tag/>
        <c:choose>
            <c:when test="${user==null}">
                <form method = "POST" action = "/controller">
                    <input type = "hidden" name = "command" value = "login"/>
                    <fmt:message key="label.login" bundle="${messages}"/> : <br/>
                    <input type="text" name="login" placeholder=<fmt:message key="label.login.login" bundle="${messages}"/>><br/>
                    <fmt:message key="label.password" bundle="${messages}"/> : <br/>
                    <input type="password" name="password" placeholder=<fmt:message key="label.password" bundle="${messages}"/>>
                    <br/>
                    <input type="submit" name="login" class="login loginmodal-submit" value=<fmt:message key="label.login" bundle="${messages}"/>>
                </form>
            </c:when>
            <c:otherwise>
                <ctg:welcome-tag/>
                <br />
            </c:otherwise>
        </c:choose>--%>

<%--<div>
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
</div>--%>

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






</body></html>

