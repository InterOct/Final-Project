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
    <fmt:message bundle="${loc}" key="local.welcome.message" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
    <c:set scope="session" var="url" value="index.jsp"/>
    <jsp:include page="${pageContext.request.contextPath}/Controller">
        <jsp:param name="command" value="get-products"/>
    </jsp:include>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsp" %>
<div class="container-fluid">
    <c:if test="${not empty requestScope.message}">
        <div class="col-md-12">
            <span>${requestScope.message}</span>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-12">
            <h1 class="text-center strong text-info">${welcome}</h1>
            <br>
            <br>
        </div>
    </div>
    <div class="row content">
        <div class="col-sm-2 sidenav">
            <ul class="list-group">
                <li class="list-group-item-heading h2 text-center">${categories}</li>
                <c:forEach var="category" items="${requestScope.categories}">
                    <li class="list-group-item"><a
                            href="${pageContext.request.contextPath}/index.jsp?cat=${category.name}">${category.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-10">
            <c:set var="counter" value="${0}" scope="page"/>
            <div class="container">
                <c:forEach var="product" items="${requestScope.products}">
                    <c:if test="${(counter%3)==0}"><div class="row"></c:if>
                    <div class="col-xs-4">
                        <div class="panel panel-primary">
                            <div class="panel-heading">${product.name}</div>
                            <div class="panel-body"><img src="${product.imgPath}"
                                                         class="img-responsive" style="width: 100%; height: 300px"
                                                         alt="Image"></div>
                            <div class="panel-footer">
                                <div class="container">
                                    <span class="h3">${price}:${product.price}$</span>
                                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                                        <input type="hidden" name="command" value="add-to-cart">
                                        <input type="hidden" name="id" value="${product.id}"/>
                                        <input type="hidden" name="name" value="${product.name}"/>
                                        <input type="hidden" name="catName" value="${product.catName}"/>
                                        <input type="hidden" name="price" value="${product.price}"/>
                                        <input type="hidden" name="producer" value="${product.shortDescription}"/>
                                        <input type="hidden" name="imgPath" value="${product.imgPath}"/>
                                        <input type="hidden" name="description" value="${product.description}"/>
                                        <input type="submit" class="btn btn-default" value="${cart}"
                                               style="float: left; width: 100px;">
                                    </form>
                                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                                        <input type="hidden" name="command" value="view-product">
                                        <input type="hidden" name="id" value="${product.id}"/>
                                        <input type="hidden" name="name" value="${product.name}"/>
                                        <input type="hidden" name="catName" value="${product.catName}"/>
                                        <input type="hidden" name="price" value="${product.price}"/>
                                        <input type="hidden" name="producer" value="${product.shortDescription}"/>
                                        <input type="hidden" name="imgPath" value="${product.imgPath}"/>
                                        <input type="hidden" name="description" value="${product.description}"/>
                                        <input type="submit" class="btn btn-default" value="View"
                                               style="float: left; width: 100px; margin-left: 20px">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:set var="counter" value="${counter+1}" scope="page"/>
                    <c:if test="${(counter%3)==0}"></div><br></c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>