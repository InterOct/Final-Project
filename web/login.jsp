<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.label.login" var="login_label"/>
    <fmt:message bundle="${loc}" key="local.label.password" var="password_label"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.login.failed" var="failed"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/mystyle.css" type="text/css">
    <c:set scope="session" var="url" value="/login.jsp"/>
    <title>${login}</title>
</head>
<body>
<%@include file="WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="row">
            <div class="col-xs-12 alert-danger">
                <strong>${failed}</strong>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-12">
            <h1 class="text-center strong text-info">${login}</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-offset-4 col-sm-4">
            <div class="window">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="login"/>
                    <div class="form-group">
                        <label for="login">${login_label}</label>
                        <input type="text" name="login" class="form-control" id="login" value=""
                               placeholder="${login_label}"
                               pattern="^[a-zA-Z\d]{3,16}$" title="Input correct login.">
                    </div>
                    <div class="form-group">
                        <label for="password">${password_label}</label>
                        <input type="password" name="password" class="form-control" id="password" value=""
                               placeholder="${password_label}"
                               title="Input correct password.">
                    </div>
                    <br>
                    <input type="submit" class="btn btn-primary full" value="${login}">
                </form>
                <br>
                <a href="${pageContext.request.contextPath}/registration.jsp">
                    <button type="button" class="btn btn-primary full">${singup}</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
