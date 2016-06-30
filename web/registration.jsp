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
    <fmt:message bundle="${loc}" key="local.registration.name.first" var="name_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.last" var="surname_label"/>
    <fmt:message bundle="${loc}" key="local.registration.email" var="email_label"/>
    <fmt:message bundle="${loc}" key="local.registration.tel" var="tel_label"/>
    <fmt:message bundle="${loc}" key="local.registration.address" var="address_label"/>
    <fmt:message bundle="${loc}" key="local.registration.failed" var="failed"/>
    <title>${title}</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css" type="text/css">
    <c:set scope="session" var="url" value="/registration.jsp"/>
</head>
<body>
<%@include file="WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-danger">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong><fmt:message bundle="${loc}" key="${requestScope.message}"/></strong>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.isRegistered}">
        <div class="row">
            <div class="col-sm-12 alert-danger">
                <strong>${failed}</strong>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-12">
            <h1 class="text-center strong text-info">${title}</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-offset-3 col-sm-6">
            <div class="window">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="register-user"/>
                    <div class="form-group">
                        <label for="login">${login_label}</label>
                        <input type="text" name="login" class="form-control" id="login" value=""
                               placeholder="${login_label}"
                               pattern="^[a-zA-Z\d]{3,16}$" title="Input correct login.">
                    </div>
                    <div class="form-group">
                        <label for="password">${password_label}</label>
                        <input type="password" name="password" class="form-control" id="password" value=""
                               pattern="^[a-zA-Z\d]{3,16}$"
                               placeholder="${password_label}"
                               title="Input correct password.">
                    </div>
                    <div class="form-group">
                        <label for="firstName">${name_label}</label>
                        <input type="text" name="firstName" value="" placeholder="${name_label}"
                               pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                               title="Input correct first name." class="form-control" id="firstName">
                    </div>
                    <div class="form-group">
                        <label for="lastName">${surname_label}</label>
                        <input type="text" name="lastName" value="" placeholder="${surname_label}"
                               pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                               title="Input correct last name." class="form-control" id="lastName">
                    </div>
                    <div class="form-group">
                        <label for="email">${email_label}</label>
                        <input type="text" name="email" value="" placeholder="${email_label}"
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" class="form-control" id="email">
                    </div>
                    <div class="form-group">
                        <label for="address">${address_label}</label>
                        <input type="text" name="address" placeholder="${address_label}"
                               title="Input correct address" pattern="^.{2,}$" class="form-control"
                               id="address">
                    </div>
                    <div class="form-group">
                        <label for="tel">${tel_label}</label>
                        <input type="text" name="tel" placeholder="+375(29)1213457"
                               title="Input correct phone number" pattern="^[0-9]{12,15}$" class="form-control"
                               id="tel">
                    </div>
                    <input type="submit" class="btn btn-primary full" value="${singup}">
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>
