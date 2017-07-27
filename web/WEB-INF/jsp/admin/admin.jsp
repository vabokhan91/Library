<%@ page language="java" contentType = "text/html; charset = UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setBundle basename="resource.config" var="path"/>
<fmt:setBundle basename="resource.language" var="message"/>
<c:if test="${user.roleId!=4}">
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
        <form action="<fmt:message key="path.page.adduser"/>">
                <input type="submit" value="Add User">
        </form>

        <a href="/controller?command=to_add_user_page" ><fmt:message key="label.add_user" bundle="${message}"/> </a><br/>

        <a href="/controller?command=to_Remove_User_Page" ><fmt:message key="label.remove_user" bundle="${message}"/> </a><br/>

        <a href="/controller?command=to_Find_User_Page" ><fmt:message key="label.button.find_user" bundle="${message}"/> </a><br/>

        <form method="get" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_not_blocked_users"/>

                <input type="submit" name="submit" value="<fmt:message key="label.user.block_user" bundle="${message}"/> "/>
        </form>

        <%--<a href="/controller?command=to_block_user_page" ><fmt:message key="label.user.block_user" bundle="${message}"/> </a><br/>--%>

        <form method="get" action="/controller" accept-charset="UTF-8">
                <input type="hidden" name="command" value="get_blocked_users"/>

                <input type="submit" name="submit" value="<fmt:message key="label.user.unblock_user" bundle="${message}"/> "/>
        </form>


        <br/>
        <a href = "/controller?command=logout">Log Out</a>


        </body>
        </html>