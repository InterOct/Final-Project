<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/mystyle.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.product.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.product.shortdesc" var="shortDescription"/>
    <fmt:message bundle="${loc}" key="local.product.image" var="image_path"/>
    <fmt:message bundle="${loc}" key="local.description" var="description"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.cart" var="cart"/>
    <fmt:message bundle="${loc}" key="local.cart.empty" var="empty_cart"/>
    <fmt:message bundle="${loc}" key="local.cart.shopping" var="go"/>
    <fmt:message bundle="${loc}" key="local.amount" var="amount"/>
    <fmt:message bundle="${loc}" key="local.coupons" var="coupons"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.buy" var="buy"/>
    <fmt:message bundle="${loc}" key="local.use" var="use"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <c:set scope="session" var="url" value="/cart"/>
    <title>${cart}</title>
    <jsp:include page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="get-coupons"/>
        <jsp:param name="id" value="${sessionScope.user.id}"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<c:if test="${not empty requestScope.message}">
    <div class="alert alert-danger">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong><fmt:message bundle="${loc}" key="${requestScope.message}"/></strong>
    </div>
</c:if>
<div class="row">
    <div class="col-md-offset-3 col-md-6">
        <div class="window">
            <c:choose>
                <c:when test="${not empty sessionScope.cart}">
                    <table>
                        <thead>
                        <tr>
                            <th></th>
                            <th>${name}</th>
                            <th>${price}</th>
                            <th>${amount}</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="productPrice" value="${0}" scope="page"/>
                        <c:forEach var="productMap" items="${sessionScope.cart}">
                            <c:set var="productPrice" value="${productPrice + productMap.key.price*productMap.value}"
                                   scope="page"/>
                            <tr>
                                <td><img src="${pageContext.request.contextPath}${productMap.key.imgPath}" height="50px"
                                         alt="Image"/></td>
                                <td><span>${productMap.key.name}</span></td>
                                <c:choose>
                                    <c:when test="${productMap.key.discountPrice eq 0}">
                                        <td><span>${productMap.key.price}</span></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><span>${productMap.key.discountPrice}</span></td>
                                    </c:otherwise>
                                </c:choose>
                                <td><span>${productMap.value}</span></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/controller" method="post">
                                        <input type="hidden" name="command" value="remove-from-cart">
                                        <input type="hidden" name="id" value="${productMap.key.id}"/>
                                        <button type="submit" class="btn btn-xs btn-danger">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${not empty requestScope.coupons}">
                        <table>
                            <thead>
                            <tr>
                                <th>${coupons}</th>
                                <th colspan="3"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="coupon" items="${requestScope.coupons}">
                                <tr>
                                    <td>${discount}</td>
                                    <td>-${coupon.discount}%</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/controller" method="post">
                                            <input type="hidden" name="command" value="buy">
                                            <input type="hidden" name="id" value="${coupon.id}">
                                            <input type="hidden" name="discount" value="${coupon.discount}"/>
                                            <input type="submit" class="btn btn-primary" value="${use}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="buy">
                        <input type="submit" class="btn btn-primary pull-right" value="${buy}">
                    </form>

                </c:when>
                <c:otherwise>
                    <h2 class="text-center strong text-info">${empty_cart}</h2>
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <button type="button" class="btn btn-primary full">${go}</button>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
