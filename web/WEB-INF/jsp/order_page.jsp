<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tld/custom.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message" var="message"/>
    <fmt:message bundle="${loc}" key="local.product.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.amount" var="amount"/>
    <fmt:message bundle="${loc}" key="local.order" var="order"/>
    <fmt:message bundle="${loc}" key="local.order.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.order.status" var="status"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css" type="text/css">
    <c:set scope="session" var="url" value="/order_page"/>
    <title>${order}</title>
    <jsp:include page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="get-user-orders"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-2 col-xs-8">
            <h2 class="strong text-info">${order}</h2>
            <div class="window">
                <h2 class="strong text-info">${date} : ${requestScope.order.date}</h2>
                <h2 class="strong text-info">${status} : <cm:status value="${requestScope.order.status}"/></h2>
                <table>
                    <tr>
                        <th></th>
                        <th>${name}</th>
                        <th>${price}</th>
                        <th>${amount}</th>
                    </tr>
                    <c:forEach var="productMap" items="${requestScope.order.products}">
                        <tr>
                            <td><img src="${pageContext.request.contextPath}${productMap.key.imgPath}" height="50px"
                                     alt="${productMap.key.imgPath}"/></td>
                            <td>${productMap.key.name}</td>
                            <td>${productMap.key.price}</td>
                            <td>${productMap.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>