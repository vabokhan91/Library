<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3}">
        <jsp:forward page="/index.jsp"/>
</c:if>

<html>
<head>
        <title><fmt:message key="label.administrator_page" bundle="${messages}"/> </title></head>

<body>

<form method="post">
        <select id="language" name="language" onchange="submit()">
                <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
                <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
        </select>
</form>
<br/>

        ${user.login}, hello!!!
        ${user.name}
        <br/>



        <a href="/controller?command=to_add_user_page" ><fmt:message key="label.add_user" bundle="${messages}"/> </a><br/>

        <a href="/controller?command=to_find_user_page" ><fmt:message key="label.remove_user" bundle="${messages}"/> </a><br/>

        <a href="/controller?command=to_find_user_page" ><fmt:message key="label.button.find_user" bundle="${messages}"/> </a><br/>

        <a href="/controller?command=to_find_user_page" ><fmt:message key="label.user.edit_user" bundle="${messages}"/> </a><br/>
        <div>
        <form action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_not_blocked_users"/>
                <input type="submit" name="submit" value="<fmt:message key="label.user.block_user" bundle="${messages}"/> "/>
        </form>
        </div>

<div>
        <form action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_blocked_users"/>

                <input type="submit" name="submit" value="<fmt:message key="label.user.unblock_user" bundle="${messages}"/> "/>
        </form>
</div>
        <a href="/controller?command=get_all_users"><fmt:message key="label.show_all_users" bundle="${messages}"/> </a><br/>

        <br/>

        <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
        <a href = "/controller?command=logout"><fmt:message key="label.logout" bundle="${messages}"/> </a>


        </body>
        </html>