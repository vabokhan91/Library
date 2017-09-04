<%@ page language="java" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>


<div class="col-6 col-md-3 sidebar-offcanvas" id="sidebar">
<a class="btn btn-secondary" href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
<c:if test="${not empty user}">

    <ctg:is-admin>
        <a class="btn btn-secondary" href="/controller?command=to_admin_page"><fmt:message key="label.button.to_main_menu"
                                                                                           bundle="${messages}"/> </a><br/><br/>
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
    </ctg:is-admin>
    <ctg:is-librarian>
        <a class="btn btn-secondary" href="/controller?command=to_librarian_main_page"><fmt:message
                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/><br/>
        <div class="list-group">
            <a href="/controller?command=to_add_user_page" class="list-group-item"><fmt:message key="label.add_user" bundle="${messages}"/></a>
            <a href="/controller?command=get_all_users" class="list-group-item"><fmt:message key="label.show_all_users" bundle="${messages}"/></a>
            <a href="/controller?command=to_find_user_page" class="list-group-item"><fmt:message key="label.button.find_user" bundle="${messages}"/></a>
            <a href="/controller?command=get_all_books" class="list-group-item"><fmt:message key="label.book.show_all_books" bundle="${messages}"/></a>
            <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.book.find_book" bundle="${messages}"/></a>
            <a href="/controller?command=to_add_book_page" class="list-group-item"><fmt:message key="label.book.add_book" bundle="${messages}"/></a>
            <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.button.book.add_order" bundle="${messages}"/></a>
            <a href="/controller?command=to_find_user_orders_page" class="list-group-item"><fmt:message key="label.button.book.return_book" bundle="${messages}"/></a>
            <a href="/controller?command=to_find_user_online_orders" class="list-group-item "><fmt:message key="label.order.execute_online_order" bundle="${messages}"/> </a>
            <a href="/controller?command=to_find_book_page" class="list-group-item"><fmt:message key="label.button.book.delete_book" bundle="${messages}"/></a>
            <a href="/controller?command=to_add_author_page" class="list-group-item"><fmt:message key="label.book.add_author" bundle="${messages}"/></a>
            <a href="/controller?command=to_delete_author_page" class="list-group-item"><fmt:message key="label.book.delete_author" bundle="${messages}"/></a>
            <a href="/controller?command=to_add_genre_page" class="list-group-item"><fmt:message key="label.genre.add_genre" bundle="${messages}"/></a>
            <a href="/controller?command=to_delete_genre_page" class="list-group-item"><fmt:message key="label.button.delete_genre" bundle="${messages}"/></a>
            <a href="/controller?command=to_add_publisher_page" class="list-group-item"><fmt:message key="label.book.add_publisher" bundle="${messages}"/></a>
            <a href="/controller?command=to_delete_publisher_page" class="list-group-item"><fmt:message key="label.book.delete_publisher" bundle="${messages}"/></a>

        </div>
    </ctg:is-librarian>
    <ctg:is-client>
        <a class="btn btn-secondary" href="/controller?command=to_user_main_page"><fmt:message
                key="label.button.to_main_menu" bundle="${messages}"/> </a><br/><br/>
        <div class="list-group">
            <a href="/controller?command=to_change_password_page" class="list-group-item"><fmt:message key="label.user.change_password" bundle="${messages}"/></a>
            <a href="/controller?command=to_change_login_page" class="list-group-item"><fmt:message key="label.login.change_login" bundle="${messages}"/></a>
            <a href="/controller?command=get_user_orders&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.order.watch_orders" bundle="${messages}"/></a>
            <a href="/controller?command=to_user_find_book_page" class="list-group-item"><fmt:message key="label.book.find_book" bundle="${messages}"/></a>
            <a href="/controller?command=get_all_books" class="list-group-item"><fmt:message key="label.book.show_all_books" bundle="${messages}"/></a>
            <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.online_orders" bundle="${messages}"/></a>
            <a href="/controller?command=to_get_online_orders_page&library_card=${sessionScope.user.libraryCardNumber}" class="list-group-item"><fmt:message key="label.book.cancel_online_order" bundle="${messages}"/></a>
        </div>
    </ctg:is-client>

</c:if>
</div>
