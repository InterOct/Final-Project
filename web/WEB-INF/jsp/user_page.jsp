<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>User page</title>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message" var="message"/>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <c:set scope="session" var="url" value="/user_page"/>

</head>
<body>
<%@include file="nav.jsp" %>
<h1><c:out value="${message}"/>, ${sessionScope.sessionScope.user.firstName}!</h1>
<form action="${pageContext.request.contextPath}/Controller" method="post">
    <input type="text" name="login" value="${sessionScope.user.login}"/><br>
    <input type="text" name="password" value="${sessionScope.user.password}"/><br>
    <input type="text" name="firstName" value="${sessionScope.user.firstName}"/><br>
    <input type="text" name="lastName" value="${sessionScope.user.lastName}"/><br>
    <input type="text" name="email" value="${sessionScope.user.email}"/><br>
</form>
<table class="table table-condensed">
    <c:forEach var="order" items="${requestScope.orders}">
        <tr>
            <td><span>${order.date}</span></td>
            <td><span>${order.status}</span></td>
            <td>
                <table class="table table-condensed">
                    <c:forEach var="productMap" items="${order.products}">
                        <tr>
                            <td><span>${productMap.key.name}</span></td>
                            <td><span>${productMap.key.price}</span></td>
                            <td><span>${productMap.value}</span></td>
                        </tr>
                    </c:forEach>
                </table>

            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>