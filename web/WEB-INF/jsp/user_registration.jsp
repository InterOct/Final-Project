<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.singup.text" var="title"/>
    <fmt:message bundle="${loc}" key="local.label.login" var="login_label"/>
    <fmt:message bundle="${loc}" key="local.label.password" var="password_label"/>
    <fmt:message bundle="${loc}" key="local.userregistration.name.first" var="name_label"/>
    <fmt:message bundle="${loc}" key="local.userregistration.name.last" var="surname_label"/>
    <fmt:message bundle="${loc}" key="local.userregistration.email" var="email_label"/>
    <title>${title}</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</head>
<body>
<form action="Controller" method="post">
    <%@include file="nav.jsp" %>
    <input type="hidden" name="command" value="register-user"/>

    ${login_label}: <br/>
    <input type="text" name="login" value=""/> <br/>
    ${password_label}: <br/>
    <input type="password" name="password" value=""/><br/>
    ${name_label}: <br/>
    <input type="text" name="firstName" value=""/><br/>
    ${surname_label}: <br/>
    <input type="text" name="lastName" value=""/><br/>
    ${email_label}: <br/>
    <input type="text" name="email" value=""/><br/>
    <input type="submit" value="${title}"/>
</form>

</body>
</html>
