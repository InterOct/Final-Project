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
    <c:set scope="session" var="url" value="registration.jsp"/>

</head>
<body>
<%@include file="WEB-INF/jsp/nav.jsp" %>
<form action="${pageContext.request.contextPath}/Controller" method="post">

    <input type="hidden" name="command" value="register-user"/>

    <p><input type="text" name="login" value="" placeholder="${login_label}" pattern="^[a-z0-9_-]{3,16}$"
              title="Input correct login."></p>
    <p><input type="password" name="password" value="" placeholder="${password_label}" pattern="^[a-zA-Z\d]{8,}$"
              title="Input correct password."></p>
    <p><input type="text" name="firstName" value="" placeholder="${name_label}" pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
              title="Input correct first name."></p>
    <p><input type="text" name="lastName" value="" placeholder="${surname_label}" pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
              title="Input correct last name."></p>
    <p><input type="text" name="email" value="" placeholder="${email_label}"
              pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"></p>
    <input type="tel" pattern="\([0-9]{3}\)\s[0-9]{3}-[0-9]{2}-[0-9]{2}" name="phone" placeholder="+375(29)1213457"
           title="Телефон" required/><br>

    <input type="submit" value="${singup}">
</form>

</body>
</html>
