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
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/mystyle.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <c:set scope="session" var="url" value="/product_page.jsp"/>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<jsp:useBean id="product" class="by.epam.eshop.entity.Product" scope="request"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-offset-3 col-xs-2">
            <div class="window container-fluid">
                ${product.name}
                <img class="img-responsive" src="${product.imgPath}" alt="${product.name}" height="400px"><br>
                ${product.price}<br>
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="add-to-cart">
                    <input type="hidden" name="id" value="${product.id}"/>
                    <input type="hidden" name="name" value="${product.name}"/>
                    <input type="hidden" name="catName" value="${product.catName}"/>
                    <input type="hidden" name="price" value="${product.price}"/>
                    <input type="hidden" name="producer" value="${product.shortDescription}"/>
                    <input type="hidden" name="imgPath" value="${product.imgPath}"/>
                    <input type="hidden" name="description" value="${product.description}"/>
                    <input type="submit" class="btn btn-default" value="${cart}" style="float: left; width: 100px;">
                </form>
            </div>

        </div>

        <div class="col-xs-offset-1 col-xs-4">
            <div class="window">
                ${product.shortDescription}
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-offset-3 col-xs-7">
            <div class="window">
                ${product.description}
            </div>
        </div>
    </div>

</div>

</body>
</html>