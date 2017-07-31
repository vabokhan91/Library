<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="messages"/>
<c:if test="${user.role.ordinal()!=3}">
        <jsp:forward page="/index.jsp"/>
</c:if>

<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Administrator page</title></head>

<body>
        ${user.login}, hello!!!
        ${user.name}
        <br/>

        <a href="/controller?command=to_add_user_page" ><fmt:message key="label.add_user" bundle="${messages}"/> </a><br/>

        <a href="/controller?command=get_users_for_removal" ><fmt:message key="label.remove_user" bundle="${messages}"/> </a><br/>

        <a href="/controller?command=to_Find_User_Page" ><fmt:message key="label.button.find_user" bundle="${messages}"/> </a><br/>

        <form method="get" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_not_blocked_users"/>

                <input type="submit" name="submit" value="<fmt:message key="label.user.block_user" bundle="${messages}"/> "/>
        </form>


        <form method="get" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_blocked_users"/>

                <input type="submit" name="submit" value="<fmt:message key="label.user.unblock_user" bundle="${messages}"/> "/>
        </form>

        <a href="/controller?command=get_all_users"><fmt:message key="label.show_all_users" bundle="${messages}"/> </a><br/>

        <br/>

        <a href="/controller?command=to_main_page"><fmt:message key="label.button.to_main_page" bundle="${messages}"/> </a><br/>
        <a href = "/controller?command=logout">Log Out</a>


        </body>
        </html>