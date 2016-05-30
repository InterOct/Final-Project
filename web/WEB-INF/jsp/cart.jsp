<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.product.name" var="name"/>
<fmt:message bundle="${loc}" key="local.category.name" var="categoty_name"/>
<fmt:message bundle="${loc}" key="local.product.price" var="price"/>
<fmt:message bundle="${loc}" key="local.product.producer" var="producer"/>
<fmt:message bundle="${loc}" key="local.product.image" var="image_path"/>
<fmt:message bundle="${loc}" key="local.description" var="description"/>
<fmt:message bundle="${loc}" key="local.edit" var="edit"/>
<fmt:message bundle="${loc}" key="local.add" var="add"/>
<c:set scope="session" var="url" value="/cart"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Cart</title>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>

<c:if test="${not empty sessionScope.cart}">
    <table class="table table-condensed">
        <thead>
        <tr>
            <th></th>
            <th>${name}</th>
            <th>${categoty_name}</th>
            <th>${price}</th>
            <th>${producer}</th>
            <th>${description}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${sessionScope.cart}">
            <tr>
                <td><img src="${pageContext.request.contextPath}${product.imgPath}" height="50px"
                         alt="Image"/></td>
                <td><span>${product.name}</span></td>
                <td><span>${product.catName}</span></td>
                <td><span>${product.price}</span></td>
                <td><span>${product.producer}</span></td>
                <td><span>${product.description}</span></td>
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="remove-from-cart">
                    <input type="hidden" name="id" value="${product.id}"/>
                    <td>
                        <button type="submit" class="btn btn-xs btn-danger">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                    </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="${pageContext.request.contextPath}/Controller" method="post">
        <input type="hidden" name="command" value="buy">
        <input type="submit" value="Buy">
    </form>
</c:if>

</body>
</html>
