<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message" var="message"/>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css" type="text/css">
    <fmt:message bundle="${loc}" key="local.edit" var="title"/>
    <fmt:message bundle="${loc}" key="local.label.login" var="login_label"/>
    <fmt:message bundle="${loc}" key="local.label.password" var="password_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.first" var="name_label"/>
    <fmt:message bundle="${loc}" key="local.registration.name.last" var="surname_label"/>
    <fmt:message bundle="${loc}" key="local.registration.email" var="email_label"/>
    <fmt:message bundle="${loc}" key="local.registration.tel" var="tel_label"/>
    <fmt:message bundle="${loc}" key="local.registration.address" var="address_label"/>
    <fmt:message bundle="${loc}" key="local.info" var="info"/>
    <fmt:message bundle="${loc}" key="local.orders" var="orders"/>
    <fmt:message bundle="${loc}" key="local.coupons" var="coupons"/>
    <fmt:message bundle="${loc}" key="local.order.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.order.status" var="status"/>
    <fmt:message bundle="${loc}" key="local.products" var="products"/>
    <fmt:message bundle="${loc}" key="local.total" var="total"/>
    <fmt:message bundle="${loc}" key="local.details" var="details"/>
    <fmt:message bundle="${loc}" key="local.empty" var="empty_c"/>
    <fmt:message bundle="${loc}" key="local.account" var="account"/>
    <title>${account}</title>
    <c:set scope="session" var="url" value="/user_page"/>
    <jsp:include page="${pageContext.request.contextPath}/Controller">
        <jsp:param name="command" value="get-user-orders"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-2 col-xs-8">
            <h2 class="strong text-info">${info}</h2>
            <div class="window">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="edit-user"/>
                    <input type="hidden" name="id" value="${sessionScope.user.id}"/>
                    <input type="hidden" name="login" value="${sessionScope.user.login}">
                    <input type="hidden" name="password" value="${sessionScope.user.password}">
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
                    <input type="submit" class="btn btn-primary" value="${title}">
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-offset-2 col-xs-8">
            <h2 class="strong text-info">${orders}</h2>
            <div class="window" style="overflow-x: auto;">
                <c:choose>
                    <c:when test="${not empty requestScope.orders}">
                        <table>
                            <tr>
                                <th>${date}</th>
                                <th>${status}</th>
                                <th>${total}</th>
                                <th></th>
                            </tr>
                            <c:forEach var="order" items="${requestScope.orders}">
                                <tr>
                                    <td>${order.date}</td>
                                    <td>${order.status}</td>
                                    <td>
                                        <c:set var="totalPrice" value="${0}" scope="page"/>
                                        <c:forEach var="productMap" items="${order.products}">
                                            <c:set var="totalPrice"
                                                   value="${totalPrice + productMap.key.price * productMap.value}"/>
                                        </c:forEach>
                                            ${totalPrice}
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                                            <input type="hidden" name="command" value="view-order">
                                            <input type="hidden" name="id" value="${order.id}">
                                            <input type="submit" value="${details}" class="btn btn-primary full"
                                                   style="margin: 0;">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-center strong text-info">${empty_c}</h2>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-offset-2 col-xs-8">
            <h2 class="strong text-info">${coupons}</h2>
            <div class="window" style="overflow-x: auto;">
                <c:choose>
                    <c:when test="${not empty requestScope.coupons}">
                        <table>
                            <tr>
                            </tr>
                            <c:forEach var="order" items="${requestScope.orders}">
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-center strong text-info">${empty_c}</h2>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>