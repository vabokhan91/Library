<%@ page isErrorPage = "true" contentType = "text/html, charset = UTF-8" pageEncoding="UTF-8"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<html>
<head><title>Error Page</title></head>
<body>
   Sorry, something gone wrong...

    </body></html>