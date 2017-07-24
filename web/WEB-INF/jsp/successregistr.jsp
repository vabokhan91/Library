<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resource.language" var="messages"/>
<fmt:setBundle basename="resource.config" var="config"/>
<html>
<head>
    <title>Congratulation</title>
</head>
<body>
<fmt:message key="label.registration.success" bundle="${messages}"/>
<a href="<fmt:message key="path.page.startpage" bundle="${config}"/>"><fmt:message key="label.button.login" bundle="${messages}"/> </a>
</body>
</html>
