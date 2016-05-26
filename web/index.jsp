<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>E-SHOP</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <c:set scope="session" var="url" value="index.jsp"/>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div style="float: left">
    <ul>
        <c:forEach var="category" items="${applicationScope.categories}">
            <li><a href="#">${category.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div style="float: left">
    <c:forEach var="product" items="${requestScope.products}">
        <div style="float: right; width: 100px; height: 150px">
            <img src="${product.imgPath}" alt="${product.name}" height="100px"><br>
            <div style="height: 60px">
                <span>${product.name}</span><br>
                <span>${product.price}</span><br>
            </div>
            <form action="${pageContext.request.contextPath}/Controller">
                <input type="hidden" name="command" value="add-to-cart">
                <input type="submit" value="Cart" style="float: left">
            </form>
        </div>
    </c:forEach>
</div>
</body>
</html>