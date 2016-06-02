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
    <fmt:message bundle="${loc}" key="local.edit" var="title"/>
    <fmt:message bundle="${loc}" key="local.label.login" var="login_label"/>
    <fmt:message bundle="${loc}" key="local.label.password" var="password_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.first" var="name_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.last" var="surname_label"/>
    <fmt:message bundle="${loc}" key="local.registration.email" var="email_label"/>
    <fmt:message bundle="${loc}" key="local.registration.tel" var="tel_label"/>
    <fmt:message bundle="${loc}" key="local.registration.address" var="address_label"/>
    <c:set scope="session" var="url" value="/user_page"/>

</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-2 col-xs-4">
            <form action="${pageContext.request.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="edit-user"/>
                <input type="hidden" name="login" value="${sessionScope.user.login}">
                <input type="hidden" name="password" value="${sessionScope.user.password}">
                <input type="hidden" name="discount" value="${sessionScope.user.discount}">
                <input type="hidden" name="banned" value="${sessionScope.user.banned}">

                <div class="form-group">
                    <label for="firstName">${name_label}</label>
                    <input type="text" name="firstName" value="${sessionScope.user.firstName}"
                           placeholder="${sessionScope.user.firstName}"
                           pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                           title="Input correct first name." class="form-control" id="firstName">
                </div>
                <div class="form-group">
                    <label for="lastName">${surname_label}</label>
                    <input type="text" name="lastName" value="${sessionScope.user.lastName}"
                           placeholder="${sessionScope.user.lastName}"
                           pattern="[А-ЯA-Zа-яa-z\s-]{2,45}"
                           title="Input correct last name." class="form-control" id="lastName">
                </div>
                <div class="form-group">
                    <label for="email">${email_label}</label>
                    <input type="text" name="email" value="${sessionScope.user.email}"
                           placeholder="${sessionScope.user.email}"
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" class="form-control" id="email">
                </div>
                <div class="form-group">
                    <label for="address">${address_label}</label>
                    <input type="text" name="address" placeholder="${sessionScope.user.address}"
                           value="${sessionScope.user.address}"
                           title="Input correct address" pattern="^.{2,}$" class="form-control"
                           id="address">
                </div>
                <div class="form-group">
                    <label for="tel">${tel_label}</label>
                    <input type="text" name="tel" placeholder="+375(29)1213457" value="${sessionScope.user.tel}"
                           title="Input correct phone number" pattern="^[0-9]{12,15}$" class="form-control"
                           id="tel">
                </div>
                <input type="submit" class="btn btn-default" value="${title}">
            </form>

        </div>
        <div class="col-xs-4">
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
        </div>
    </div>


</div>


</body>
</html>